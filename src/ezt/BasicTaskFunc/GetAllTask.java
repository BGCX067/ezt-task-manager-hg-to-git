package ezt.BasicTaskFunc;

import ezt.FileIO.*;

public class GetAllTask {
	
	public Object[][] allTaskDay(String date){
	
		Task task = new Task();
		
		return task.getAllTaskDay(date);
		
	}

	public Object[][] allTaskWeek(String date){
		
		Task task = new Task();
		
		return task.getAllTaskWeek(date);
		
	}
	
	public Object[][] allTaskMonth(String date){
		
		Task task = new Task();
		
		return task.getAllTaskMonth(date);
		
	}
	
	public Object[][] allEventToday(String date){
		
		Task task = new Task();
		
		return task.getAllEventDay(date);
		
	}	

	public boolean isEvent(String id){
				
		ReadFromText readTask = new ReadFromText();			
		
		return readTask.read(id).contains("nil");
		
	}
}
