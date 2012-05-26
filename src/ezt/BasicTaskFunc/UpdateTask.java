package ezt.BasicTaskFunc;

public class UpdateTask {
	
	public boolean update(String id, String desc, String date, String time, String priority, boolean onAlert, String status){
		
		Task task = new Task();
		
		task.getTask(id);
		
		task.setDesc(desc);
		task.setDate(date);
		task.setTime(time);
		task.setPriority(priority);
		task.setOnAlert(onAlert);
		task.setStatus(status);
		
		return task.updateTask();
		
	}
	
}
