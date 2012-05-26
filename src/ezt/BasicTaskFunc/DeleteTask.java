package ezt.BasicTaskFunc;

public class DeleteTask {

	public boolean delete(int id){
	
		Task t = new Task();
		
		return t.deleteTask(id);
		
	}
	
}
