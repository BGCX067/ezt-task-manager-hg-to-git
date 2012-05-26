package ezt.FileIO;

import java.io.*;
import java.util.*;

public class SortFileRecord {

	private static final String fileName = "Task.txt";	

	public void sort(){

		try{
			
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
	        Map<String, String> map=new TreeMap<String, String>();
	        String line="";
	        while((line=reader.readLine())!=null){
	                map.put(getField(line),line);
	        }
	        reader.close();
	        FileWriter writer = new FileWriter(fileName);
	        for(String val : map.values()){
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
