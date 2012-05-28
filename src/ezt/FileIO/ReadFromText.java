package ezt.FileIO;

import java.io.*;
import java.util.StringTokenizer;

//this class is to read the task from task.txt by task ID
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

	public Object[][] readByDesc(String desc){
		
		String concateTask = "", taskDetails, strLine;
		String id = "", date="", time="", priority="", alert="", status="";
		Object [][] searchResult= new Object [20][7];
		int i=0;
		
		try{
			
			FileInputStream fstream = new FileInputStream(fileName);

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));   		

  			//Read File Line By Line
  			while ((strLine = br.readLine()) != null)   {
  				
  				StringTokenizer st = new StringTokenizer(strLine, "/");
  				
  				while(st.hasMoreTokens()) {
  					
  					id = st.nextToken();  					
  					taskDetails = st.nextToken();
  					date = st.nextToken();
  					time = st.nextToken();
  					priority = st.nextToken();
  					alert = st.nextToken();
  					status = st.nextToken();
  					
  					if(taskDetails.contains(desc)){
  					
					
  						searchResult[i][0] = date;
  					    searchResult[i][1] = taskDetails;
  					    searchResult[i][2] = priority;
  					    searchResult[i][3] = alert;
  					    searchResult[i][4] = id;
  					    searchResult[i][5] = time;
  					    searchResult[i][6] = status;
  					      				
  					    i++;
  					    
  					}
  				}  				
  				  				
  			}
  			
		}catch (Exception e) {
			
            System.out.println("Error: " + e.getMessage());
        }   	
    
		return searchResult;
	
	}

public Object[][] readByStatus(String statuss){
		
		String concateTask = "", taskDetails, strLine;
		String id = "", date="", time="", priority="", alert="", status="";
		Object [][] searchResult= new Object [20][7];
		int i=0;
		
		try{
			
			FileInputStream fstream = new FileInputStream(fileName);

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));   		

  			//Read File Line By Line
  			while ((strLine = br.readLine()) != null)   {
  				
  				StringTokenizer st = new StringTokenizer(strLine, "/");
  				
  				while(st.hasMoreTokens()) {
  					
  					id = st.nextToken();  					
  					taskDetails = st.nextToken();
  					date = st.nextToken();
  					time = st.nextToken();
  					priority = st.nextToken();
  					alert = st.nextToken();
  					status = st.nextToken();
  					
  				 if(status.equalsIgnoreCase(statuss)){
  					
  						searchResult[i][0] = date;
  					    searchResult[i][1] = taskDetails;
  					    searchResult[i][2] = priority;
  					    searchResult[i][3] = alert;
  					    searchResult[i][4] = id;
  					    searchResult[i][5] = time;
  					    searchResult[i][6] = status;
  					    i++;
  					}
  					    
  				}  				
  				  				
  			}
  			
		}catch (Exception e) {
			
            System.out.println("Error: " + e.getMessage());
        }   	
    
		return searchResult;
	
	}

public Object[][] readByPriority(String Prioritys){
		
		String concateTask = "", taskDetails, strLine;
		String id = "", date="", time="", priority="", alert="", status="";
		Object [][] searchResult= new Object [20][7];
		int i=0;
		
		try{
			
			FileInputStream fstream = new FileInputStream(fileName);

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));   		

  			//Read File Line By Line
  			while ((strLine = br.readLine()) != null)   {
  				
  				StringTokenizer st = new StringTokenizer(strLine, "/");
  				
  				while(st.hasMoreTokens()) {
  					
  					id = st.nextToken();  					
  					taskDetails = st.nextToken();
  					date = st.nextToken();
  					time = st.nextToken();
  					priority = st.nextToken();
  					alert = st.nextToken();
  					status = st.nextToken();
  					
  				 if(priority.equalsIgnoreCase(Prioritys)){
  					
  						searchResult[i][0] = date;
  					    searchResult[i][1] = taskDetails;
  					    searchResult[i][2] = priority;
  					    searchResult[i][3] = alert;
  					    searchResult[i][4] = id;
  					    searchResult[i][5] = time;
  					    searchResult[i][6] = status;
  					    i++;
  					}
  					    
  				}  				
  				  				
  			}
  			
		}catch (Exception e) {
			
            System.out.println("Error: " + e.getMessage());
        }   	
    
		return searchResult;
	
	}
}
