package ezt.BasicTaskFunc;

public class UpdateTask {
	
	public boolean update(String id, String desc, String date, String time, String priority, boolean onAlert, String status){
		
		Task task = new Task();
		
		task.getTask(id);
		
		task.setDesc(desc);//update with new task description
		task.setDate(date);//update with new task start date and end date
		task.setTime(time);//update with new task start time and end time
		task.setPriority(priority);//update with new task priority
		task.setOnAlert(onAlert);//update with new task to remind or not
		task.setStatus(status);//update with new task status
		
		return task.updateTask();
		
	}
	
}
