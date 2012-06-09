package ezt.FileIO;

import java.io.*;

//read the receiver email address of reminder
public class ReadEmailAddr {
	
	private static final String fileName = "receiverEmail.txt";	
		
	public static String read(){
		
		String receiverEmail="", strLine;
		
		try{
			
			FileInputStream fstream = new FileInputStream(fileName);

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));   		

  			//Read File Line By Line
  			while ((strLine = br.readLine()) != null)   {
  				  					
  				receiverEmail = strLine;
  								
  			}
  			
		}catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }   	
    	
		return receiverEmail;
	}	

}
