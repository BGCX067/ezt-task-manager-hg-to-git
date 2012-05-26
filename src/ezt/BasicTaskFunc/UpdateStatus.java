package ezt.BasicTaskFunc;

public class UpdateStatus {

	public boolean statusUpdate(String id, String status){
		
		Task task = new Task();
		
		task.getTask(id);
		
		task.setStatus(status);
		
		return task.updateTask();
		
	}
	
}
