package ezt.BasicTaskFunc;

import ezt.FileIO.*;

public class GetAllTask {
	
	//retrieve all the today task
	public Object[][] allTaskDay(String date){
	
		Task task = new Task();
		
		return task.getAllTaskDay(date);
		
	}

	//retrieve all the this week task
	public Object[][] allTaskWeek(String date){
		
		Task task = new Task();
		
		return task.getAllTaskWeek(date);
		
	}
	
	//retrieve all the this month task
	public Object[][] allTaskMonth(String date){
		
		Task task = new Task();
		
		return task.getAllTaskMonth(date);
		
	}
	
	//retrieve all the this month task
	public Object[][] allTasks(){
		
		Task task = new Task();
		
		return task.getAllTasks();
		
	}
	
	//retrieve all the today event
	public Object[][] allEventToday(String date){
		
		Task task = new Task();
		
		return task.getAllEventDay(date);
		
	}	

	//to check whether the record is an event if its' time is nil
	public boolean isEvent(String id){
				
		ReadFromText readTask = new ReadFromText();			
		
		return readTask.read(id).contains("nil");
		
	}
}
