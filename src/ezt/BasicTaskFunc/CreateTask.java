package ezt.BasicTaskFunc;

import ezt.FileIO.GetLastID;

public class CreateTask {
	
	public boolean create(String desc, String date, String time, String priority, boolean onAlert, String status){		
			
		GetLastID lastID = new GetLastID();
			
		Task t = new Task();
				
		return t.addTask(lastID.getLastID()+1, desc, date, time, priority, onAlert, status);
				
	}	

}
