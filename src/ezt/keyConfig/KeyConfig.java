package ezt.keyConfig;

import ezt.DetectInput.*;
import java.io.*;
import java.util.StringTokenizer;

//Shortcut key config manager frame
public class KeyConfig {
	
	public static void getKey(){
		
		String strLine;
				
		try{
			
			FileInputStream fstream = new FileInputStream("KeyConfig.txt");//read all the key settings from KeyConfig textfile

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));   		

  			//Read File Line By Line
  			while ((strLine = br.readLine()) != null)   {
  				
  				StringTokenizer st = new StringTokenizer(strLine, "~");
  				
  				//store all the key settings into golbal variables
  				while(st.hasMoreTokens()) {
  					
  					Global.activateKey = st.nextToken().charAt(0);
  					Global.weekPanelKey = st.nextToken().charAt(0);
  					Global.dayPanelKey = st.nextToken().charAt(0);
  					Global.monthPanelKey = st.nextToken().charAt(0);
  					Global.calendarDateKey = st.nextToken().charAt(0);
  					Global.eventPanelKey = st.nextToken().charAt(0);
  					Global.commandKey = st.nextToken().charAt(0);
  					Global.searchPanelKey = st.nextToken().charAt(0);
  					Global.calendarPrevMKey = Integer.parseInt(st.nextToken());
  					Global.calendarNextMKey = Integer.parseInt(st.nextToken());
  					Global.calendarYrKey = Integer.parseInt(st.nextToken());  			
  					
  					
  				}  				
  				  				
  			}
  			
		}catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }   
		
		
	}
	
	//save the new key settings
	public static void writeKey(String keySet){
					
		try{
			
			FileWriter fstream = new FileWriter("KeyConfig.txt");
            BufferedWriter outobj = new BufferedWriter(fstream);
            outobj.write(keySet);
            outobj.close();            
    		
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
		
		
	}

}
