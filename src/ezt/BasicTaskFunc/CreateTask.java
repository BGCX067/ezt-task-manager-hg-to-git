/*Author: Yueng Shu Sheng
 * Purpose: get the last task id in text file
*/

package ezt.BasicTaskFunc;

import ezt.FileIO.GetLastID;

public class CreateTask {
	
	public boolean create(String desc, String date, String time, String priority, boolean onAlert, String status){		

		//get the last task id in text file
		GetLastID lastID = new GetLastID();
			
		Task t = new Task();
				
		//increaase the task id with 1
		return t.addTask(lastID.getLastID()+1, desc, date, time, priority, onAlert, status);
				
	}	

}
