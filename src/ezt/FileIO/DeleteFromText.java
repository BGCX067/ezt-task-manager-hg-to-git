package ezt.FileIO;

import java.io.*;
import java.util.StringTokenizer;

public class DeleteFromText {

	private static final String fileName = "Task.txt";	
	private static final String fileNameTemp = "Temp.txt";

	public void delete(int searhTaskID){

		try{
			
			String strLine = "", taskDetails="", concateTask="", taskID = "";
			FileInputStream fstream = new FileInputStream(fileName);
			File srcFile = new File(fileNameTemp);
			File destFile = new File(fileName);
			taskID = Integer.toString(searhTaskID);
			
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));   		 		
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileNameTemp));
			
  			//Read File Line By Line
  			while ((strLine = br.readLine()) != null)   {
  				
  				StringTokenizer st = new StringTokenizer(strLine, "/");
  				
  				while(st.hasMoreTokens()) {
  					
  					taskDetails = st.nextToken();
  					
  					if(!taskDetails.equalsIgnoreCase(taskID)){
  						
  						concateTask += taskDetails + "/";
  						
  						while(st.hasMoreTokens()) {

  							concateTask += st.nextToken() + "/";
  							
  						}
  						
			  			bw.write(concateTask);
  						bw.newLine();
  						concateTask = "";
  						    
  					}else{break;}
  			      
  				} 				
  				  				
  			} 		  			    	 	
 
		    bw.flush();
		    bw.close();
		    
		    CopyFile c = new CopyFile();
		    c.copy(srcFile, destFile);
		    
		    SortFileRecord s = new SortFileRecord();
			s.sort();
		    
		}catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } 
	
	}
	
}
