package ezt.DetectInput;

import ezt.BasicTaskFunc.*;
import java.util.StringTokenizer;

public class DetectInput {
	
	public boolean detect(String input){

		String id = "", desc = "", date = "", time = "", priority = "", status = "Active";
		Boolean onAlert = false;
		
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
			
		}else if(input.substring(0,6).equalsIgnoreCase("delete")){
																	
			StringTokenizer st = new StringTokenizer(input, ",");
				
				while(st.hasMoreTokens()) {
					
					st.nextToken();
					id = st.nextToken();	
					
					break;
					
				}		  				
				DeleteTask deleteTask = new DeleteTask();
				
				return deleteTask.delete(Integer.parseInt(id));
			
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
	
	public Object[][] allTaskDay(){
		
		GetAllTask getAllTask = new GetAllTask();
		return getAllTask.allTaskDay();
		
	}

	public Object[][] allTaskWeek(){
		
		GetAllTask getAllTask = new GetAllTask();
		return getAllTask.allTaskWeek();
		
	}
	public Object[][] allTaskMonth(){
		
		GetAllTask getAllTask = new GetAllTask();
		return getAllTask.allTaskMonth();
		
	}
	
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
}
