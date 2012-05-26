package ezt.BasicTaskFunc;

import ezt.FileIO.*;

public class GetAllTask {
	
	public Object[][] allTaskDay(){
		
		Task task = new Task();
		
		return task.getAllTaskDay();
		
	}

	public Object[][] allTaskWeek(){
		
		Task task = new Task();
		
		return task.getAllTaskWeek();
		
	}
	
	public Object[][] allTaskMonth(){
		
		Task task = new Task();
		
		return task.getAllTaskMonth();
		
	}
	
	public Object[][] allEventToday(){
		
		Task task = new Task();
		
		return task.getAllEventDay();
		
	}	

	public boolean isEvent(String id){
				
		ReadFromText readTask = new ReadFromText();			
		
		return readTask.read(id).contains("nil");
		
	}
}
