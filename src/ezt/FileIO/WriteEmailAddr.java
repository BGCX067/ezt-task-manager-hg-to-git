package ezt.FileIO;

import java.io.*;

//write the receiver email address of reminder
public class WriteEmailAddr {
	
	private static final String destFile = "receiverEmail.txt";
	
	public static boolean write(String email){
		
		boolean success = false;		

		try{
			
			FileWriter fstream = new FileWriter(destFile);
            BufferedWriter outobj = new BufferedWriter(fstream);
            outobj.write(email);
            outobj.close();
            success = true;
    		
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
		
		return success;
		
	}	

}
