package ezt.FileIO;

import java.io.*;
import java.util.StringTokenizer;

public class GetLastID {

	private static final String fileName = "Task.txt";
	
	public int getLastID(){
		
		String strLine;
		int currentID = 0;
		
		try{
			
			FileInputStream fstream = new FileInputStream(fileName);

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));   		

  			//Read File Line By Line
  			while ((strLine = br.readLine()) != null)   {
  				
  				StringTokenizer st = new StringTokenizer(strLine, "/");
  				
  				while(st.hasMoreTokens()) {
  					
  					currentID = Integer.parseInt(st.nextToken());
  					 					
  					break;
  					
 				}  				
  				  				
  			}
  			
		}catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }   	
    			
		return currentID;
		
	}
	
}
