package ezt.DetectInput;

import ezt.BasicTaskFunc.*;
import java.util.StringTokenizer;

//this is the Facade class which act as a wall btn the UI and internal components
public class DetectInput {
	
	//detect the input whether is create, update or delete task
	public boolean detect(String input){

		String id = "", desc = "", date = "", time = "", priority = "", status = "Active";
		Boolean onAlert = false;
		
		//create task command
		if(input.substring(0,3).equalsIgnoreCase("add")){
																	
			StringTokenizer st = new StringTokenizer(input, ",");
				
				while(st.hasMoreTokens()) {
					
					st.nextToken();
					desc = st.nextToken();
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
					date = st.nextToken();
					time = st.nextToken();
					priority = st.nextToken();
					if(st.nextToken().equalsIgnoreCase("on alert")){
						onAlert = true;
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
	public Object[][] allTaskDay(){
		
		GetAllTask getAllTask = new GetAllTask();
		return getAllTask.allTaskDay();
		
	}

	//get all this wekk tasks object and send to UI
	public Object[][] allTaskWeek(){
		
		GetAllTask getAllTask = new GetAllTask();
		return getAllTask.allTaskWeek();
		
	}
	
	//get all this month tasks object and send to UI
	public Object[][] allTaskMonth(){
		
		GetAllTask getAllTask = new GetAllTask();
		return getAllTask.allTaskMonth();
		
	}
	
	//get all today events object and send to UI
	public Object[][] allEventToday(){
		
		GetAllTask getAllTask = new GetAllTask();
		return getAllTask.allEventToday();
		
	}
	
	public String concateUpdateString(String id) {
		
		String concateUpdateString = "", alert = "";
		
		Task task = new Task(id);
		
		if(task.isOnAlert()) alert = "on alert";
		else alert = "no alert";
		
		concateUpdateString += "update,"+id+","+task.getDesc()+","+task.getDate()+
		","+task.getTime()+","+task.getPriority()+
		","+alert+","+task.getStatus();
		
		return concateUpdateString;
		
	}
	
	public Object[][] searchTask(String desc){
					
		SearchTask read = new SearchTask();

		return read.searchByDesc(desc);
		
	}
}
