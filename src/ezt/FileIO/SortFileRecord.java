package ezt.FileIO;

import java.io.*;
import java.util.*;

//this class is to the temp task file and write back to the task.txt
public class SortFileRecord {

	private static final String fileName = "Task.txt";	

	public void sort(){

		try{
			
			//sorting the tasks by task ID
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
	        Map<String, String> map=new TreeMap<String, String>();
	        String line="";
	        while((line=reader.readLine())!=null){
	                map.put(getField(line),line);
	        }
	        reader.close();
	        
	        FileWriter writer = new FileWriter(fileName);
	        for(String val : map.values()){
	        	
	        		//write back to the task.txt
	                writer.write(val);      
	                writer.write('\n');
	        }
	        writer.close();
	        
		}catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String getField(String line) {
        return line.split(" ")[0];//extract value you want to sort on
    }
}
