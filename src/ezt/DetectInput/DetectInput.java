package ezt.DetectInput;

import ezt.BasicTaskFunc.*;
import ezt.FileIO.*;
import ezt.Reminder.*;

import java.util.List;
import java.util.StringTokenizer;

//this is the Facade class which act as a wall btn the UI and internal components
public class DetectInput {
	
	//detect the input whether is create, update or delete task
	public boolean detect(String input){

		String id = "", desc = "", date = "", time = "", priority = "", status = "Active", tempAlert="";
		Boolean onAlert = false;
		
		
		//create task command
		if(input.substring(0,3).equalsIgnoreCase("add")){
																	
			StringTokenizer st = new StringTokenizer(input, ",");
				
				while(st.hasMoreTokens()) {
					
					st.nextToken();
					desc = st.nextToken();
					writeWordList("Description:"+desc);//save user typed word for future autocomplete
					date = st.nextToken();
					time = st.nextToken();					
					priority = st.nextToken();
					
					if(st.nextToken().equalsIgnoreCase("yes")){
						onAlert = true;
					}else {
						onAlert = false;
					}
					
					break;
					
				}		  				
				CreateTask createTask = new CreateTask();
				return createTask.create(desc, date, time, priority, onAlert, status);
				 
				//update task command	
		}else if(input.substring(0,6).equalsIgnoreCase("update")){
															
			StringTokenizer st = new StringTokenizer(input, ",");
				
				while(st.hasMoreTokens()) {
					
					st.nextToken();
					id = st.nextToken();
					desc = st.nextToken();
					if(st.hasMoreTokens()==false){
						date = Global.dateTemp;
					}else{
						date = st.nextToken();
					}
					if(st.hasMoreTokens()==false){
						time= Global.timeTemp;
					}else{
						time = st.nextToken();
					}
					if(st.hasMoreTokens()==false){
						priority= Global.priorityTemp;
					}else{
						priority = st.nextToken();
					}
					if(st.hasMoreTokens()==false){
						tempAlert = Global.alertTemp;
					}else{
						tempAlert = st.nextToken();
					}
					
					if(tempAlert.equalsIgnoreCase("alert true")){						
						onAlert = true;
					}else if(tempAlert.equalsIgnoreCase("alert false")){						
						onAlert = false;
					}else{
						onAlert = false;
					}
					
					break;
					
				}		  				
				UpdateTask updateTask = new UpdateTask();
				
				return updateTask.update(id,desc, date, time, priority, onAlert, status);
			
				//delete task command
		}else if(input.substring(0,6).equalsIgnoreCase("delete")){
																	
			StringTokenizer st = new StringTokenizer(input, ",");
				
				while(st.hasMoreTokens()) {
					
					st.nextToken();
					id = st.nextToken();	
					
					break;
					
				}		  				
				DeleteTask deleteTask = new DeleteTask();
				
				return deleteTask.delete(Integer.parseInt(id));
			
				//change status of task command
		}else if(input.substring(0,6).equalsIgnoreCase("status")){
																		
			StringTokenizer st = new StringTokenizer(input, ",");
				
				while(st.hasMoreTokens()) {
					
					st.nextToken();
					id = st.nextToken();		  					
					status = st.nextToken();	
					break;
					
				}		  				
				UpdateStatus updateStatus = new UpdateStatus();
				
				return updateStatus.statusUpdate(id, status);
			
		}else{return false;}
		
	}
	
	public boolean isEvent(String id){
		
		GetAllTask getAllTask = new GetAllTask();
		return getAllTask.isEvent(id);
		
	}
	
	//get all today tasks object and send to UI
	public Object[][] allTaskDay(String todayDate){
		
		GetAllTask getAllTask = new GetAllTask();
		return getAllTask.allTaskDay(todayDate);
		
	}

	//get all this week tasks object and send to UI
	public Object[][] allTaskWeek(String todayDate){
		
		GetAllTask getAllTask = new GetAllTask();
		return getAllTask.allTaskWeek(todayDate);
		
	}
	
	//get all this month tasks object and send to UI
	public Object[][] allTaskMonth(String todayDate){
		
		GetAllTask getAllTask = new GetAllTask();
		return getAllTask.allTaskMonth(todayDate);
		
	}
	
	//get all today events object and send to UI
	public Object[][] allEventToday(String todayDate){
		
		GetAllTask getAllTask = new GetAllTask();
		return getAllTask.allEventToday(todayDate);
		
	}
	
	public String concateUpdateString(String id) {
		
		String concateUpdateString = "", alert = "";
		
		Task task = new Task(id);
		
		if(task.isOnAlert()) alert = "alert true";
		else alert = "alert false";
			
		Global.dateTemp=task.getDate();
		Global.timeTemp=task.getTime();
		Global.priorityTemp=task.getPriority();
		Global.alertTemp=alert;				
				
		concateUpdateString += "update,"+id+","+task.getDesc()+","+task.getDate()+
		","+task.getTime()+","+task.getPriority()+
		","+alert+","+task.getStatus();
		
		return concateUpdateString;
		
	}
	
	public Object[][] searchTask(String desc){
		
		SearchTask read = new SearchTask();

		if(desc.substring(0,2).equalsIgnoreCase("-p")) return read.searchByPriority(desc.substring(2));
		else if (desc.substring(0,2).equalsIgnoreCase("-s")) return read.searchByStatus(desc.substring(2));
		else return read.searchByDesc(desc);
		
	}
	
	public List<String> readWordList(){
		
		ReadFromText read = new ReadFromText();
		
		return read.readWordList(); 
		
	}

	public void writeWordList(String word){
		
		WriteToText write = new WriteToText();
		
		write.writeWordList(word); 
		
	}
	
	public static boolean runReminder(){
		
		Task checkTodayTask = new Task();
		return checkTodayTask.todayHaveReminder();
	}
	
	public boolean setEmail(String email){
		
		WriteEmailAddr setEmail = new WriteEmailAddr();
		
		return setEmail.write(email);
	}
	
	public boolean setHpNo(String hpno){
		
		WriteHpNo setHpNo = new WriteHpNo();
		
		return setHpNo.write(hpno);
	}

}
