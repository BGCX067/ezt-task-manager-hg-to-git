package ezt.FileIO;

import java.io.*;

//this class is to write/append the task into task.txt
public class WriteToText {

	private static final String fileName = "Task.txt";	
	
	public void write(Object task){

		try{
	        
            OutputStreamWriter writer = new OutputStreamWriter(
                  new FileOutputStream(fileName, true), "UTF-8");
            BufferedWriter fbw = new BufferedWriter(writer);
            fbw.write(task.toString());
            fbw.newLine();
            fbw.close();
            
            SortFileRecord s = new SortFileRecord();
    		s.sort();
    		
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
		
	}	
	
}
