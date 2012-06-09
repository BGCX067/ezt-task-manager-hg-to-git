package ezt.BasicTaskFunc;

import ezt.FileIO.*;

public class SearchTask {
	
	//search the task by description
	public Object[][] searchByDesc(String desc){
		
		ReadFromText read = new ReadFromText();
		return read.readByDesc(desc);
		
	}
	
	//search the task by priority
	public Object[][] searchByPriority(String priority){
		
		ReadFromText read = new ReadFromText();
		return read.readByPriority(priority);
		
	}
	
	//search the task by status
	public Object[][] searchByStatus(String status){
		
		ReadFromText read = new ReadFromText();
		return read.readByStatus(status);
		
	}

}
