package ezt.FileIO;

import java.io.*;
import java.util.StringTokenizer;

public class ReadFromText {

	private static final String fileName = "Task.txt";	
	
	public String read(String searhTaskID){
		
		String concateTask = "", taskDetails, strLine;
		
		try{
			
			FileInputStream fstream = new FileInputStream(fileName);

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));   		

  			//Read File Line By Line
  			while ((strLine = br.readLine()) != null)   {
  				
  				StringTokenizer st = new StringTokenizer(strLine, "/");
  				
  				while(st.hasMoreTokens()) {
  					
  					taskDetails = st.nextToken();
  					
  					if(taskDetails.equalsIgnoreCase(searhTaskID)){
  						
  						concateTask += taskDetails + ".";
  						
  						while(st.hasMoreTokens()) {
  							concateTask += st.nextToken() + ".";
  						}
  					}
  				}  				
  				  				
  			}
  			
		}catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }   	
    	
		if(concateTask == ""){
			return "No records found.";
		}else{
			return concateTask;
		}
	}	

	
}
