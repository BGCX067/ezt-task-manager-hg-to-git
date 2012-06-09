package ezt.FileIO;

import java.io.*;

//write the receiver hp no of reminder
public class WriteHpNo {
	
	private static final String destFile = "receiverHpNo.txt";
	
	public static boolean write(String hpno){
		
		boolean success = false;
		
		try{
			
			FileWriter fstream = new FileWriter(destFile);
            BufferedWriter outobj = new BufferedWriter(fstream);
            outobj.write(hpno);
            outobj.close();
            success = true;
    		
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
		
		return success;
		
	}	

}
