/*Author: Yueng Shu Sheng
 * Purpose: delete task by id
*/
package ezt.BasicTaskFunc;

public class DeleteTask {

	//delete task by id
	public boolean delete(int id){
	
		Task t = new Task();
		
		return t.deleteTask(id);
		
	}
	
}
