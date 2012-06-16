/*Author: Yueng Shu Sheng
 * Purpose: update the status of the task/event
*/


package ezt.BasicTaskFunc;

//to update the status of the task/event
public class UpdateStatus {

	public boolean statusUpdate(String id, String status){
		
		Task task = new Task();
		
		task.getTask(id);//get the task / event with id
		
		task.setStatus(status);//and then update its status to active/non active
		
		return task.updateTask();
		
	}
	
}
