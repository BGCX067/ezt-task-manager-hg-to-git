package ezt.BasicTaskFunc;

import ezt.FileIO.*;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.text.*;
import java.util.*;

public class Task {

	private int id;
	private String desc;
	private String priority;
	private String time;
	private String date;
	private String status;
	private boolean onAlert;
	
	public Task(){
		super();
		this.id = 0;
		this.desc = "";
		this.priority = "";
		this.onAlert = false;
		this.date = "";
		this.time = "";	
		this.status = "";
	}
	
	public Task(String idSelf){
		
		ReadFromText readTask = new ReadFromText();		
		
		StringTokenizer st = new StringTokenizer(readTask.read(idSelf), ".");
		
		while(st.hasMoreTokens()) {
			this.id = Integer.parseInt(st.nextToken());
			this.desc = st.nextToken();
			this.date = st.nextToken();
			this.time = st.nextToken();
			this.priority = st.nextToken();
			this.onAlert = Boolean.parseBoolean(st.nextToken());
			this.status = st.nextToken();
		}
		
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public boolean isOnAlert() {
		return onAlert;
	}

	public void setOnAlert(boolean onAlert) {
		this.onAlert = onAlert;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getTask(String idSearch){
		
		ReadFromText readTask = new ReadFromText();		
		
		StringTokenizer st = new StringTokenizer(readTask.read(idSearch), ".");
		
		while(st.hasMoreTokens()) {
			this.id = Integer.parseInt(st.nextToken());
			this.desc = st.nextToken();
			this.date = st.nextToken();
			this.time = st.nextToken();
			this.priority = st.nextToken();
			this.onAlert = Boolean.parseBoolean(st.nextToken());
			this.status = st.nextToken();
		}
		
		return 	"ID: " + this.id +"\nDescription: " + this.desc +"\nDate: " + this.date +"\nTime: " 
				+ this.time + "\nPriority: " + this.priority +
				"\nAlert: " + this.onAlert +"\nStatus: " + this.status ;
		
	}
	
	public String toString(){

		return 	this.id +"/" + this.desc +"/" + this.date +"/" 
				+ this.time +"/" + this.priority +"/" + this.onAlert +"/" + this.status +"/\n";
		
	}

	public boolean addTask(int id, String desc, String date, String time, String priority, boolean onAlert, String status){
		
		boolean success = false;
		
		this.id = id;
		this.desc = desc;
		this.priority = priority;
		this.onAlert = onAlert;
		this.date = date;
		this.time = time;
		this.status = status;
		
		WriteToText writeTask = new WriteToText();
		writeTask.write(this);
		
		success = true;
		
		return success;
		
	}
	
	public boolean deleteTask(int taskID){
		
		boolean success = false;
		
		DeleteFromText deleteTask = new DeleteFromText();		
		deleteTask.delete(taskID);
		
		success = true;
		
		return success;
	}	

	public boolean updateTask(){
		
		boolean success = false;
		
		WriteToText writeTask = new WriteToText();
		DeleteFromText deleteTask = new DeleteFromText();
		deleteTask.delete(this.id);
		writeTask.write(this);
		
		success = true;
		
		return success;
		
	}

	public Object[][] getAllTaskDay(){
		
		int lastID = 0, g = 0;
		int startTime = 0;
		DateFormat formatter ; 
		Date td=null, startDate=null, endDate=null;
		Calendar todayDate;
					
		GetLastID fileIO = new GetLastID();
		lastID = fileIO.getLastID();
		
		ReadFromText readTask = new ReadFromText();
		
		StringTokenizer st;
		
		String timesInDay [] = {"01:00 - 02:00", "02:00 - 03:00","03:00 - 04:00","04:00 - 05:00","05:00 - 06:00","06:00 - 07:00",
				"07:00 - 08:00","08:00 - 09:00","09:00 - 10:00","10:00 - 11:00","11:00 - 12:00", "12:00 - 13:00", "13:00 - 14:00",
				"14:00 - 15:00", "15:00 - 16:00", "16:00 - 17:00", "17:00 - 18:00", "18:00 - 19:00", "19:00 - 20:00", 
				"20:00 - 21:00", "21:00 - 22:00", "22:00 - 23:00", "23:00 - 24:00"};
		
		Object[][] alltask = new Object[timesInDay.length][8];
		
		for(int c=0;c<timesInDay.length;c++){
			
			alltask [c][0]= timesInDay[c];
		}
		
		for(int i=1;i<=lastID;i++){			
			
			st = new StringTokenizer(readTask.read(Integer.toString(i)), ".");
			
			while(st.hasMoreTokens()) {
				this.id = Integer.parseInt(st.nextToken());
				this.desc = st.nextToken();
				this.date = st.nextToken();
				this.time = st.nextToken();
				this.priority = st.nextToken();
				this.onAlert = Boolean.parseBoolean(st.nextToken());
				this.status = st.nextToken();
			}
					
			try{
				
				formatter = new SimpleDateFormat("dd-MMM-yy");
				startDate = (Date)formatter.parse(this.date.substring(5,14));  
				endDate = (Date)formatter.parse(this.date.substring(18,27));
				todayDate = Calendar.getInstance();
				todayDate.set(Calendar.HOUR_OF_DAY, 0);
				todayDate.set(Calendar.MINUTE, 0);
				todayDate.set(Calendar.SECOND, 0);
				todayDate.set(Calendar.MILLISECOND, 0);
				td = todayDate.getTime();			
						
			}catch(Exception ex){System.out.println(ex);}
			
			if((!this.time.equalsIgnoreCase("nil")) && ((td.after(startDate) && td.before(endDate)) || td.equals(startDate) || td.equals(endDate))){				
			
				if(this.time.substring(0,1).equalsIgnoreCase("0")){
				
					startTime = Integer.parseInt(this.time.substring(0,2));
					
				}else{
					
					startTime = Integer.parseInt(this.time.substring(0,1)+"0");
					startTime += Integer.parseInt(this.time.substring(1,2));
					
				} 
				
				for(int c=0;c<timesInDay.length;c++){
					
					if(startTime==(Integer.parseInt(alltask [c][0].toString().substring(0,1))) || startTime==(Integer.parseInt(alltask [c][0].toString().substring(0,2)))){
						
						alltask [c][1]= this.desc;			
						alltask [c][2]= this.priority;			
						alltask [c][3]= this.onAlert;
						alltask [c][4]= this.id;
						alltask [c][5]= this.date;
						alltask [c][6]= this.status;
						alltask [c][7]= this.time;
					}else{
						
						if(alltask [c][1] == null){
							
							alltask [c][1]= "";
							alltask [c][2]= "";
							alltask [c][3]= "";			
							alltask [c][4]= "";			
							alltask [c][5]= "";
							alltask [c][6]= "";
							alltask [c][7]= "";
							
						}
						
					}
				}

			}
			
		}
		
		return alltask;
		
	}
	
	public Object[][] getAllTaskWeek(){
		
		int lastID = 0, g = 0;
		
		DateFormat formatter ; 
		Date td=null, startDate=null, endDate=null;
		Calendar todayDate;
		
		int weekCurrent=0, weekStart=0, weekEnd=0;
		Calendar weekStartCal=null, weekEndCal=null;
		
		GetLastID fileIO = new GetLastID();
		lastID = fileIO.getLastID();
		
		ReadFromText readTask = new ReadFromText();
		
		StringTokenizer st;
		
		Object[][] alltask = new Object[lastID][7]; 
		
		for(int i=1;i<=lastID;i++){			
			
			st = new StringTokenizer(readTask.read(Integer.toString(i)), ".");
			
			while(st.hasMoreTokens()) {
				this.id = Integer.parseInt(st.nextToken());
				this.desc = st.nextToken();
				this.date = st.nextToken();
				this.time = st.nextToken();
				this.priority = st.nextToken();
				this.onAlert = Boolean.parseBoolean(st.nextToken());
				this.status = st.nextToken();
			}
			
			try{				
				
				formatter = new SimpleDateFormat("dd-MMM-yy");
				
				startDate = (Date)formatter.parse(this.date.substring(5,14));
				weekStartCal = Calendar.getInstance();
				weekStartCal.setTime(startDate);
				weekStart = weekStartCal.get(Calendar.WEEK_OF_MONTH);
								
				endDate = (Date)formatter.parse(this.date.substring(18,27));
				weekEndCal = Calendar.getInstance();
				weekEndCal.setTime(endDate);
				weekEnd = weekEndCal.get(Calendar.WEEK_OF_MONTH);
				
				todayDate = Calendar.getInstance();	
				todayDate.set(Calendar.HOUR_OF_DAY, 0);
				todayDate.set(Calendar.MINUTE, 0);
				todayDate.set(Calendar.SECOND, 0);
				todayDate.set(Calendar.MILLISECOND, 0);
				td = todayDate.getTime();	
				weekCurrent = todayDate.get(Calendar.WEEK_OF_MONTH);
						
			}catch(Exception ex){System.out.println(ex);}
			
			if((!this.time.equalsIgnoreCase("nil")) && ((td.getMonth()>=startDate.getMonth() && td.getMonth()<=endDate.getMonth()) && (weekCurrent == weekStart))){	
				alltask [g][0]= this.id;
				alltask [g][1]= this.time;
				alltask [g][2]= this.desc;			
				alltask [g][3]= this.priority;			
				alltask [g][4]= this.onAlert;
				alltask [g][5]= this.date;
				alltask [g][6]= this.status;
				
				g++;
			}
			
		}
		
		return alltask;
		
	}	
	
	public Object[][] getAllTaskMonth(){
		
		int lastID = 0, g=0;

		DateFormat formatter ; 
		Date td=null, startDate=null, endDate=null;
		Calendar todayDate;
		
		GetLastID fileIO = new GetLastID();
		lastID = fileIO.getLastID();
		
		ReadFromText readTask = new ReadFromText();
		
		StringTokenizer st;
		
		Object[][] alltask = new Object[lastID][7]; 
		
		for(int i=1;i<=lastID;i++){			
			
			st = new StringTokenizer(readTask.read(Integer.toString(i)), ".");
			
			while(st.hasMoreTokens()) {
				this.id = Integer.parseInt(st.nextToken());
				this.desc = st.nextToken();
				this.date = st.nextToken();
				this.time = st.nextToken();
				this.priority = st.nextToken();
				this.onAlert = Boolean.parseBoolean(st.nextToken());
				this.status = st.nextToken();
			}
			
			try{
				
				formatter = new SimpleDateFormat("dd-MMM-yy");
				startDate = (Date)formatter.parse(this.date.substring(5,14));  
				endDate = (Date)formatter.parse(this.date.substring(18,27));
				todayDate = Calendar.getInstance();
				todayDate.set(Calendar.HOUR_OF_DAY, 0);
				todayDate.set(Calendar.MINUTE, 0);
				todayDate.set(Calendar.SECOND, 0);
				todayDate.set(Calendar.MILLISECOND, 0);
				td = todayDate.getTime();			
				
			}catch(Exception ex){System.out.println(ex);}
			
			if((!this.time.equalsIgnoreCase("nil")) && ((td.getMonth()>=startDate.getMonth() && td.getMonth()<=endDate.getMonth()))){	
				alltask [g][0]= this.id;
				alltask [g][1]= this.time;
				alltask [g][2]= this.desc;			
				alltask [g][3]= this.priority;			
				alltask [g][4]= this.onAlert;
				alltask [g][5]= this.date;
				alltask [g][6]= this.status;
				
				g++;
			}
			
		}
		
		return alltask;
		
	}	
	
	public Object[][] getAllEventDay(){
		
		int lastID = 0, g = 0;
		DateFormat formatter ; 
		Date td=null, startDate=null, endDate=null;
		Calendar todayDate;
					
		GetLastID fileIO = new GetLastID();
		lastID = fileIO.getLastID();
		
		ReadFromText readTask = new ReadFromText();
		
		StringTokenizer st;
		
		Object[][] alltask = new Object[lastID][7]; 
		
		for(int i=1;i<=lastID;i++){			
			
			st = new StringTokenizer(readTask.read(Integer.toString(i)), ".");
			
			while(st.hasMoreTokens()) {
				this.id = Integer.parseInt(st.nextToken());
				this.desc = st.nextToken();
				this.date = st.nextToken();
				this.time = st.nextToken();
				this.priority = st.nextToken();
				this.onAlert = Boolean.parseBoolean(st.nextToken());
				this.status = st.nextToken();
			}

			try{
				
				formatter = new SimpleDateFormat("dd-MMM-yy");
				startDate = (Date)formatter.parse(this.date.substring(5,14));  
				endDate = (Date)formatter.parse(this.date.substring(18,27));
				todayDate = Calendar.getInstance();
				todayDate.set(Calendar.HOUR_OF_DAY, 0);
				todayDate.set(Calendar.MINUTE, 0);
				todayDate.set(Calendar.SECOND, 0);
				todayDate.set(Calendar.MILLISECOND, 0);
				td = todayDate.getTime();			
						
			}catch(Exception ex){System.out.println(ex);}
			
			if((this.time.equalsIgnoreCase("nil")) && ((td.after(startDate) && td.before(endDate)) || td.equals(startDate) || td.equals(endDate))){				
				
				alltask [g][0]= this.id;		
				alltask [g][1]= this.desc;
				
				g++;
			}
			
		}
		
		return alltask;
		
	}	
}
