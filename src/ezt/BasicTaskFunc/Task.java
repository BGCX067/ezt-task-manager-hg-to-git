/*Author: Yueng Shu Sheng
 * Purpose: task object class
*/
package ezt.BasicTaskFunc;

import ezt.DetectInput.Global;
import ezt.FileIO.*;
import ezt.GoogleSync.EventFeed;
import ezt.Reminder.SendReminderEmail;
import ezt.Reminder.SendSMSmessage;
import ezt.Reminder.runReminder;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.io.IOException;
import java.text.*;
import com.google.gdata.util.ServiceException;

public class Task {

	private int id; //task id
	private String desc; //task description
	private String priority; //task priority, high/medium/low
	private String time; //task start time & end time
	private String date; //task start date & end date
	private String status; //task status, active or non active	
	private boolean onAlert; //make task to trigger reminder or not
	
	//empty task constructor
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
	
	//get task constructor with id
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
	
	//search task by id
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
	
	//concate all the task details
	public String toString(){

		return 	this.id +"/" + this.desc +"/" + this.date +"/" 
				+ this.time +"/" + this.priority +"/" + this.onAlert +"/" + this.status +"/\n";
		
	}

	//create task with details
	public boolean addTask(int id, String desc, String date, String time, String priority, boolean onAlert, String status){
		
		boolean success = false;
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yy"); 
		
		this.id = id;
		this.desc = desc;
		this.priority = priority;
		this.onAlert = onAlert;
		this.date = date;
		this.time = time;
		this.status = status;	
		
		//call the method to check whether the time of task is previously occupied
		if(previousAdded(this.date,this.time) == false){
			
			//call the write to text IO to write to textfile
			WriteToText writeTask = new WriteToText();
			writeTask.write(this);
			
			
			//add the task/event to Google Calendar, One-Way-Sync
			EventFeed googleSync = new EventFeed();
			googleSync.GoogleCalSyn(this.desc,this.date,this.time);
			
			success = true;
		
		}
		
		return success;
		
	}
	
	//to check whether the time of task is previously occupied
	public boolean previousAdded(String d, String t){
		
		int startTime = 0;
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yy"); 
		Date td=null, startDate=null, endDate=null, reminderTd=null;
		Calendar todayDate,reminderTodayDate;
		boolean previousAdded = false;
		int lastID=0;
		String dates="", times="";
		
		ReadFromText readTask = new ReadFromText();
		
		StringTokenizer st;
		
		//get the last task id
		GetLastID fileIO = new GetLastID();
		lastID = fileIO.getLastID();
				
		//loop thru all the task
		for(int i=1;i<=lastID;i++){			
			
			st = new StringTokenizer(readTask.read(Integer.toString(i)), ".");
			
			while(st.hasMoreTokens()) {

				st.nextToken();
				st.nextToken();
				dates = st.nextToken();
				times = st.nextToken();
		 	    st.nextToken();
				st.nextToken();
				st.nextToken();
				
			try{
				
				startDate = (Date)formatter.parse(dates.substring(5,14));//parse the start date in string to date object  
				endDate = (Date)formatter.parse(dates.substring(18,27));//parse the end date in string to date object
					
				td = (Date)formatter.parse(d.substring(5,14));	
					
				//check the same date of the new task and previous task
				if((td.after(startDate) && td.before(endDate)) || td.equals(startDate) || td.equals(endDate)){
						
					//if the time of the new task and previous task are same then true 
					if(Integer.parseInt(times.substring(0,2))==Integer.parseInt(t.substring(0,2))){
						previousAdded = true;
					}
				}	
				
			}catch(Exception ex){System.out.println(ex);}
		
			}
		}
		
		return previousAdded;
	}
	
	public boolean deleteTask(int taskID){
		
		boolean success = false;
		
		//delete the task/event to Google Calendar, One-Way-Sync
		EventFeed googleSync = new EventFeed();
		try{
					
			getTask(String.valueOf(taskID));		
			
			googleSync.deleteGoogleCal(this.desc);
				
		}catch(Exception ex){System.out.println("deleteGoogle "+ex);}	
				
		DeleteFromText deleteTask = new DeleteFromText();		
		deleteTask.delete(taskID);
						
		success = true;
		
		return success;
	}	

	public boolean updateTask(){
		
		boolean success = false;
			
		GetLastID getLast = new GetLastID();
		WriteToText writeTask = new WriteToText();
		DeleteFromText deleteTask = new DeleteFromText();
		deleteTask.delete(this.id);
			
		this.id = getLast.getLastID() + 1;
			
		writeTask.write(this);
		
		 String changedTodayMonth="",changedTodayMonth2="";
		  String taskStartDate="",taskEndDate="",taskStartTime="",taskEndTime="";
			
		  changedTodayMonth = this.date.substring(8,11);
		  changedTodayMonth2 = this.date.substring(21,24);
			
		  if(changedTodayMonth.equalsIgnoreCase("Jan")) changedTodayMonth="01";
	      else if(changedTodayMonth.equalsIgnoreCase("Feb")) changedTodayMonth="02";
	      else if(changedTodayMonth.equalsIgnoreCase("Mar")) changedTodayMonth="03";
	      else if(changedTodayMonth.equalsIgnoreCase("Apr")) changedTodayMonth="04";
	      else if(changedTodayMonth.equalsIgnoreCase("May")) changedTodayMonth="05";
	      else if(changedTodayMonth.equalsIgnoreCase("Jun")) changedTodayMonth="06";
	      else if(changedTodayMonth.equalsIgnoreCase("Jul")) changedTodayMonth="07";
	      else if(changedTodayMonth.equalsIgnoreCase("Aug")) changedTodayMonth="08";
	      else if(changedTodayMonth.equalsIgnoreCase("Sep")) changedTodayMonth="09";
	      else if(changedTodayMonth.equalsIgnoreCase("Oct")) changedTodayMonth="10";
	      else if(changedTodayMonth.equalsIgnoreCase("Nov")) changedTodayMonth="11";
	      else changedTodayMonth="12";
			
		  if(changedTodayMonth2.equalsIgnoreCase("Jan")) changedTodayMonth2="01";
	      else if(changedTodayMonth2.equalsIgnoreCase("Feb")) changedTodayMonth2="02";
	      else if(changedTodayMonth2.equalsIgnoreCase("Mar")) changedTodayMonth2="03";
	      else if(changedTodayMonth2.equalsIgnoreCase("Apr")) changedTodayMonth2="04";
	      else if(changedTodayMonth2.equalsIgnoreCase("May")) changedTodayMonth2="05";
	      else if(changedTodayMonth2.equalsIgnoreCase("Jun")) changedTodayMonth2="06";
	      else if(changedTodayMonth2.equalsIgnoreCase("Jul")) changedTodayMonth2="07";
	      else if(changedTodayMonth2.equalsIgnoreCase("Aug")) changedTodayMonth2="08";
	      else if(changedTodayMonth2.equalsIgnoreCase("Sep")) changedTodayMonth2="09";
	      else if(changedTodayMonth2.equalsIgnoreCase("Oct")) changedTodayMonth2="10";
	      else if(changedTodayMonth2.equalsIgnoreCase("Nov")) changedTodayMonth2="11";
	      else changedTodayMonth2="12";
			
		  taskStartDate="20"+this.date.substring(12,14)+changedTodayMonth+this.date.substring(5,7);
		  taskEndDate="20"+this.date.substring(25)+changedTodayMonth2+this.date.substring(18,20);
			
		  if(!this.time.equalsIgnoreCase("nil")){
			  taskStartTime = this.time.substring(0,2)+"0000";
			  taskEndTime = this.time.substring(3)+"0000";
		  }else{
			  taskStartTime = "000000";
			  taskEndTime = "230000";
		  }
		  
		//update the task/event to Google Calendar, One-Way-Sync
		EventFeed googleSync = new EventFeed();
		try{
			
		googleSync.updateGoogleCal(Global.tempOldDesc, this.desc, taskStartDate,taskEndDate,taskStartTime,taskEndTime);
		}catch(Exception ex){System.out.println("updateGoogle "+ex);}	
		
		Global.tempOldDesc="";
		
		success = true;
		
		return success;
		
	}
	
	/*get all today tasks, a two dimensionals object array, 1st dimension is the row of tasks, 
	 * 2nd dimension store the attributes of each task 
	 */
	public Object[][] getAllTaskDay(String todayDates){
		
		
		int lastID = 0, g = 0;
		int startTime = 0;
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yy"); 
		Date td=null, startDate=null, endDate=null, reminderTd=null;
		Calendar todayDate,reminderTodayDate;
					
		GetLastID fileIO = new GetLastID();
		lastID = fileIO.getLastID();
		
		ReadFromText readTask = new ReadFromText();
		
		StringTokenizer st;
		
		//store the available timeslot for today
		String timesInDay [] = {"01:00 - 02:00", "02:00 - 03:00","03:00 - 04:00","04:00 - 05:00","05:00 - 06:00","06:00 - 07:00",
				"07:00 - 08:00","08:00 - 09:00","09:00 - 10:00","10:00 - 11:00","11:00 - 12:00", "12:00 - 13:00", "13:00 - 14:00",
				"14:00 - 15:00", "15:00 - 16:00", "16:00 - 17:00", "17:00 - 18:00", "18:00 - 19:00", "19:00 - 20:00", 
				"20:00 - 21:00", "21:00 - 22:00", "22:00 - 23:00", "23:00 - 24:00"};
		
		Object[][] alltask = new Object[timesInDay.length][8];
		
		for(int c=0;c<timesInDay.length;c++){
			
			alltask [c][0]= timesInDay[c];
			alltask [c][1]= "  ";
			alltask [c][2]= "  ";
			alltask [c][3]= "  ";
			alltask [c][4]= "  ";
			alltask [c][5]= "  ";
			alltask [c][6]= "  ";
			alltask [c][7]= "  ";
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
				
				startDate = (Date)formatter.parse(this.date.substring(5,14));//parse the start date in string to date object  
				endDate = (Date)formatter.parse(this.date.substring(18,27));//parse the end date in string to date object
				
				if(todayDates.equalsIgnoreCase("")){
					
					todayDate = Calendar.getInstance();
					todayDate.set(Calendar.HOUR_OF_DAY, 0);
					todayDate.set(Calendar.MINUTE, 0);
					todayDate.set(Calendar.SECOND, 0);
					todayDate.set(Calendar.MILLISECOND, 0);
					td = todayDate.getTime();
					
				}else{
					
					td = (Date)formatter.parse(todayDates);
				}
			}catch(Exception ex){System.out.println(ex);}
				
			//check whether the tasks is today
			if((!this.time.equalsIgnoreCase("nil")) && ((td.after(startDate) && td.before(endDate)) || td.equals(startDate) || td.equals(endDate))){				
			
				if(this.time.substring(0,1).equalsIgnoreCase("0")){
				
					startTime = Integer.parseInt(this.time.substring(0,2));
					
				}else{
					
					startTime = Integer.parseInt(this.time.substring(0,1)+"0");
					startTime += Integer.parseInt(this.time.substring(1,2));
					
				} 
				
				for(int c=0;c<timesInDay.length;c++){
					
					//assign the tasks to each corresponding timeslot
					if(startTime==(Integer.parseInt(alltask [c][0].toString().substring(0,1))) || startTime==(Integer.parseInt(alltask [c][0].toString().substring(0,2)))){
						
						alltask [c][1]= this.desc;			
						alltask [c][2]= this.priority;			
						alltask [c][3]= this.onAlert;
						alltask [c][4]= this.id;
						alltask [c][5]= this.date;
						alltask [c][6]= this.status;
						alltask [c][7]= this.time;
												
						if(this.status.replace(" ", "").equalsIgnoreCase("nonactive")){
							
							alltask [c][2]= " "+priority;			
							
						}
						
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
	
	//get all this week tasks
	public Object[][] getAllTaskWeek(String todayDates){
		
		String changedTodayDates = "", changedTodayMonth = "", changedTodayYear = "", concate="",sample="";
		SimpleDateFormat sdf;
        Calendar cal;
        Date date;
		int lastID = 0, g = 0;
		DateFormat formatter ; 
		Date td=null, startDate=null, endDate=null;
		Calendar todayDate = null;
		Object[][] alltask;
		Object[][] newAllTask;
		Calendar weekStartCal=null, weekEndCal=null;
		Calendar selectedStartCal = null, selectedEndCal = null;
		
		GetLastID fileIO = new GetLastID();
		lastID = fileIO.getLastID();
		
		alltask = new Object[lastID][8];
		
		ReadFromText readTask = new ReadFromText();
		
		StringTokenizer st;
		
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
								
				endDate = (Date)formatter.parse(this.date.substring(18,27));
				weekEndCal = Calendar.getInstance();
				weekEndCal.setTime(endDate);
				
				if(todayDates.equalsIgnoreCase("")){
					
					todayDate = Calendar.getInstance();
					todayDate.set(Calendar.HOUR_OF_DAY, 0);
					todayDate.set(Calendar.MINUTE, 0);
					todayDate.set(Calendar.SECOND, 0);
					todayDate.set(Calendar.MILLISECOND, 0);
					td = todayDate.getTime();
					
					//get the first date of the week selected
					selectedStartCal = (Calendar) todayDate.clone();
					selectedStartCal.add(Calendar.DAY_OF_WEEK, 
							selectedStartCal.getFirstDayOfWeek() - selectedStartCal.get(Calendar.DAY_OF_WEEK));
					
					//get the last date of the week selected
					selectedEndCal = (Calendar) selectedStartCal.clone();
					selectedEndCal.add(Calendar.DAY_OF_YEAR, 6);
					
				}else{
					
					 
			         StringTokenizer sts = new StringTokenizer(todayDates,"-");
			         while(sts.hasMoreTokens()) {
			        	 changedTodayDates = sts.nextToken();
			        	 changedTodayMonth = sts.nextToken();
			        	 changedTodayYear = sts.nextToken();
			        	 
				        
			         }
			        
		                if(changedTodayMonth.equalsIgnoreCase("Jan")) changedTodayMonth="1";
		                else if(changedTodayMonth.equalsIgnoreCase("Feb")) changedTodayMonth="2";
		                else if(changedTodayMonth.equalsIgnoreCase("Mar")) changedTodayMonth="3";
		                else if(changedTodayMonth.equalsIgnoreCase("Apr")) changedTodayMonth="4";
		                else if(changedTodayMonth.equalsIgnoreCase("May")) changedTodayMonth="5";
		                else if(changedTodayMonth.equalsIgnoreCase("Jun")) changedTodayMonth="6";
		                else if(changedTodayMonth.equalsIgnoreCase("Jul")) changedTodayMonth="7";
		                else if(changedTodayMonth.equalsIgnoreCase("Aug")) changedTodayMonth="8";
		                else if(changedTodayMonth.equalsIgnoreCase("Sep")) changedTodayMonth="9";
		                else if(changedTodayMonth.equalsIgnoreCase("Oct")) changedTodayMonth="10";
		                else if(changedTodayMonth.equalsIgnoreCase("Nov")) changedTodayMonth="11";
		                else changedTodayMonth="12";
		                
			         concate = changedTodayMonth+"/"+changedTodayDates+"/"+("20"+changedTodayYear);
			         sample = concate;			         
			         sdf = new SimpleDateFormat("MM/dd/yyyy");
			         date = sdf.parse(sample);
			         cal = Calendar.getInstance();
			         cal.setTime(date);
			         td = (Date)formatter.parse(todayDates);
			         
			         //get the first date of the week selected
					 selectedStartCal = (Calendar) cal.clone();
					 selectedStartCal.add(Calendar.DAY_OF_WEEK, 
					 selectedStartCal.getFirstDayOfWeek() - selectedStartCal.get(Calendar.DAY_OF_WEEK));
						
					 //get the last date of the week selected
					 selectedEndCal = (Calendar) selectedStartCal.clone();
					 selectedEndCal.add(Calendar.DAY_OF_YEAR, 6);			         
				}		
						
			}catch(Exception ex){System.out.println(ex);}
					
			//check whether the tasks is this week
			if((!this.time.equalsIgnoreCase("nil")) && (!weekEndCal.before(selectedStartCal) && 
					(!weekStartCal.after(selectedEndCal))))
					
					{
					
					//assign day of the week
					if(startDate.getDay()==0){
						alltask [g][0]="Sunday";
					}else if(startDate.getDay()==1){
						alltask [g][0]="Monday";
					}else if(startDate.getDay()==2){
						alltask [g][0]="Tuesday";
					}else if(startDate.getDay()==3){
						alltask [g][0]="Wednesday";
					}else if(startDate.getDay()==4){
						alltask [g][0]="Thursday";
					}else if(startDate.getDay()==5){
						alltask [g][0]="Friday";
					}else{
						alltask [g][0]="Saturday";
					}
					
					alltask [g][1]= this.desc;			
					alltask [g][2]= this.priority;			
					alltask [g][3]= this.onAlert;
					alltask [g][4]= this.id;
					alltask [g][5]= this.date;
					alltask [g][6]= this.status;
					alltask [g][7]= this.time;
					
					if(this.status.replace(" ", "").equalsIgnoreCase("nonactive")){
						
						alltask [g][2]=  " "+priority;			
						
					}
					
					g++;
					
					
				}
			
		}
		
		// create a new smaller array to remove empty task object array
	    newAllTask = new Object[g][8];
		
	    for (int i=0;i<g;i++) {	        
		
	        	for(int c=0; c<8; c++){
	        		newAllTask[i][c] = alltask[i][c];
	        		
	        	}
	        
	    }
				
		return newAllTask;
		
	}	
	
	//get all this month tasks
	public Object[][] getAllTaskMonth(String todayDates){
		
		int lastID = 0, g=0;
		Object[][] alltask;
		Object[][] newAllTask;
		DateFormat formatter , formatter2 ; 
		Date td=null, startDate=null, endDate=null,startDate2=null;
		Calendar todayDate;
		Calendar mthStartCal = null, mthEndCal = null;
		Calendar selectedStartCal = null, selectedEndCal = null;
		Calendar cal=null;
		
		GetLastID fileIO = new GetLastID();
		lastID = fileIO.getLastID();
		
		alltask = new Object[lastID][8];
		
		ReadFromText readTask = new ReadFromText();
		
		StringTokenizer st;
		
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
				mthStartCal = Calendar.getInstance();
				mthStartCal.setTime(startDate);
				
				endDate = (Date)formatter.parse(this.date.substring(18,27));
				mthEndCal = Calendar.getInstance();
				mthEndCal.setTime(endDate);

				if(todayDates.equalsIgnoreCase("")){
					
					todayDate = Calendar.getInstance();
					todayDate.set(Calendar.HOUR_OF_DAY, 0);
					todayDate.set(Calendar.MINUTE, 0);
					todayDate.set(Calendar.SECOND, 0);
					todayDate.set(Calendar.MILLISECOND, 0);
					td = todayDate.getTime();
					
					//get the first date of the mth selected
					selectedStartCal = (Calendar) todayDate.clone();
					selectedStartCal.set(Calendar.DAY_OF_MONTH, 1);
						
					//get the last date of the mth selected
					selectedEndCal = (Calendar) selectedStartCal.clone();
					selectedEndCal.add(Calendar.DAY_OF_MONTH, selectedStartCal.getActualMaximum(Calendar.DAY_OF_MONTH)-1);	
					
				}else{
					
					td = (Date)formatter.parse(todayDates);
					cal = Calendar.getInstance();
					cal.setTime(td);
					
					//get the first date of the mth selected
					selectedStartCal = (Calendar) cal.clone();
					selectedStartCal.set(Calendar.DAY_OF_MONTH, 1);
						
					//get the last date of the mth selected
					selectedEndCal = (Calendar) selectedStartCal.clone();
					selectedEndCal.add(Calendar.DAY_OF_MONTH, selectedStartCal.getActualMaximum(Calendar.DAY_OF_MONTH)-1);	
										
				}		
				
			}catch(Exception ex){System.out.println(ex);}
				
			//check whether the tasks is this month
			if((!this.time.equalsIgnoreCase("nil"))){	

					try{
						
						formatter2 = new SimpleDateFormat("dd-MMM-yy");
						startDate2 = (Date)formatter2.parse(this.date.substring(5,14)); 
						
					}catch(Exception ex){System.out.println(ex);}
				
					if((!mthEndCal.before(selectedStartCal) && 
							(!mthStartCal.after(selectedEndCal)))){
						alltask [g][0]= startDate2;
						alltask [g][1]= this.desc;			
						alltask [g][2]= this.priority;			
						alltask [g][3]= this.onAlert;
						alltask [g][4]= this.id;
						alltask [g][5]= this.date;
						alltask [g][6]= this.status;
						alltask [g][7]= this.time;
						
						if(this.status.replace(" ", "").equalsIgnoreCase("nonactive")){
							
							alltask [g][2]=  " "+priority;			
							
						}
						
						g++;
					}				
		
			}
			
		}
		
		// create a new smaller array to remove empty task object array
	    newAllTask = new Object[g][8];
		
	    for (int i=0;i<g;i++) {	        
		
	        	for(int c=0; c<8; c++){
	        		newAllTask[i][c] = alltask[i][c];
	        	}
	        
	    }
				
		return newAllTask;
		
	}	
	
	/////////Get all tasks and events
	public Object[][] getAllTasks(){
			
		int lastID = 0, g=0;
		Object[][] alltask;
		Object[][] newAllTask;
		DateFormat formatter;
		Date startDate=null;

		GetLastID fileIO = new GetLastID();
		lastID = fileIO.getLastID();
		
		alltask = new Object[lastID][8];
		
		ReadFromText readTask = new ReadFromText();
		
		StringTokenizer st;
		
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
				
			}catch(Exception ex){System.out.println(ex);}
			
			alltask [g][0]= startDate;
			alltask [g][1]= this.desc;			
			alltask [g][2]= this.priority;			
			alltask [g][3]= this.onAlert;
			alltask [g][4]= this.id;
			alltask [g][5]= this.date;
			alltask [g][6]= this.status;
			alltask [g][7]= this.time;
			
			if(this.status.replace(" ", "").equalsIgnoreCase("nonactive")){
				
			alltask [g][2]=  " "+priority;			
			
			}	
			
			g++;
		}
		
		/*
		// create a new smaller array to remove empty task object array
	    newAllTask = new Object[g][8];
		
	    for (int i=0;i<g;i++) {	        
		
	        	for(int c=0; c<8; c++){
	        		newAllTask[i][c] = alltask[i][c];
	        	}
	        
	    }
	    */
				
		return alltask;
			
	}	
	
	//////End of getAllTasks()
	
	//get all today event
	public Object[][] getAllEventDay(String todayDates){
		
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
				
				if(todayDates.equalsIgnoreCase("")){

					todayDate = Calendar.getInstance();
					todayDate.set(Calendar.HOUR_OF_DAY, 0);
					todayDate.set(Calendar.MINUTE, 0);
					todayDate.set(Calendar.SECOND, 0);
					todayDate.set(Calendar.MILLISECOND, 0);
					td = todayDate.getTime();
					
				}else{
					
					td = (Date)formatter.parse(todayDates);
					
				}			
						
			}catch(Exception ex){System.out.println(ex);}
			
			if(!this.status.equalsIgnoreCase("non active")){
				
			//check the task whether is today & with time attribute of 'nil'
			if(((this.time.equalsIgnoreCase("nil")) && ((td.after(startDate) && td.before(endDate)) || td.equals(startDate) || td.equals(endDate)))||
					this.desc.toLowerCase().contains("birthday") && td.getDate()==startDate.getDate() && td.getMonth()==startDate.getMonth()){				
				
				alltask [g][0]= this.id;		
				alltask [g][1]= this.desc;
				
				g++;
			}
			}
			
		}
		
		return alltask;
		
	}	
	
	//check the today task & remind one hour before the task due
	public boolean todayHaveReminder(){		
		
		int lastID = 0, count=0;
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yy"); 
		Date td=null, startDate=null, endDate=null, reminderTd=null;
		Calendar todayDate,reminderTodayDate;
		boolean haveReminder = false, raiseAlarm=false;
		Global.reminderDesc = "<html>";
							
		GetLastID fileIO = new GetLastID();
		lastID = fileIO.getLastID();
		String concateTask="", frm = "", til="";
		
		ReadFromText readTask = new ReadFromText();
		
		StringTokenizer st;		
		
		ReadEmailAddr receiverEmail = new ReadEmailAddr();
		
		SendReminderEmail sendEmail = new SendReminderEmail();
		
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
				
				startDate = (Date)formatter.parse(this.date.substring(5,14));//parse the start date in string to date object  
				endDate = (Date)formatter.parse(this.date.substring(18,27));//parse the end date in string to date object			
				todayDate = Calendar.getInstance();
				todayDate.set(Calendar.HOUR_OF_DAY, 0);
				todayDate.set(Calendar.MINUTE, 0);
				todayDate.set(Calendar.SECOND, 0);
				todayDate.set(Calendar.MILLISECOND, 0);
				td = todayDate.getTime();
					
			}catch(Exception ex){System.out.println(ex);}
							
			//check whether the tasks is today then raise alarm & send reminder email if on alert is true
			if((this.onAlert==true && ((td.after(startDate) && td.before(endDate)) || td.equals(startDate) || td.equals(endDate)|| 
					(this.time.equalsIgnoreCase("nil"))))){				
				
				reminderTodayDate = Calendar.getInstance();
				
				if(!this.time.equalsIgnoreCase("nil")){
				
					reminderTodayDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(this.time.substring(0, 2)));
					reminderTodayDate.add(Calendar.HOUR, -1);//remind one hour before the task due
					
				}else{
					
					reminderTodayDate.set(Calendar.HOUR_OF_DAY, 0);
					//reminderTodayDate.set(Calendar.MONTH, Integer.parseInt(this.date.substring(5,14)));
					
					
				}				
				reminderTodayDate.set(Calendar.MINUTE, 0);
				reminderTodayDate.set(Calendar.SECOND, 0);
				reminderTodayDate.set(Calendar.MILLISECOND, 0);
				reminderTd = reminderTodayDate.getTime();
								
				//initiate reminder
				runReminder reminder = new runReminder();
			            
				//check whether the date & time of the task is suppose to remind, true or false
				haveReminder = reminder.reminder(formatter.format(reminderTd));							
				
				//if suppose to remind
				if(haveReminder==true){
										
					ReadHpNo receiverHpNo = new ReadHpNo();
													
					SendSMSmessage sendsms = new SendSMSmessage();
				
					todayDate = Calendar.getInstance();
					
					td = todayDate.getTime();
					
			
					try{
						//for task reminder
						if(!this.time.equalsIgnoreCase("nil") && ((td.getHours()==reminderTd.getHours()))){
								
							count ++;
							
							if(Integer.parseInt(this.time.substring(0,2))<12){
								frm = "AM";
							}else{
								frm="PM";
							}
							
							if(Integer.parseInt(this.time.substring(3))<12){
								til = "AM";
							}else{
								til="PM";
							}
							
							//store the task details for the label of reminder
							Global.reminderDesc += count + ". " + this.desc + " from " + this.time.substring(0,2) + " " + frm + 
									" till " + this.time.substring(3) + " "+ til+"<br><br>";
							
							//send sms alert, msg cannot be too long due to free sms server
						    sendsms.sendMessage(receiverHpNo.read().trim(), "Rem: "+ this.desc + "@"+ this.time, "", "", "", "");
							
							//store the task details for the email message
							concateTask += "\n\n" + count + ". " + "Task/Event: " + this.desc + "\nDate: " + this.date+ 
									"\nTime: " + " from " + this.time.substring(0,2) + " " + frm + 
									" till " + this.time.substring(3) + " "+ til + "\nPriority: " + this.priority + "\n\n";
							
							raiseAlarm = true;						
							
							
						}
						
						//for event reminder
						if(this.time.equalsIgnoreCase("nil") && ((td.getMonth()+1>=startDate.getMonth()+1)) && ((td.getDate()+1==startDate.getDate()+1))){
							
							count++;
							
							//store the event details for the label of reminder
							Global.reminderDesc += count + ". " + this.desc +"<br><br>";
							
							//send sms alert, msg cannot be too long due to free sms server
							sendsms.sendMessage(receiverHpNo.read().trim(), "Rem: "+ this.desc, "", "", "", "");
							
							//store the event details for the email message
							concateTask += "\n\n" + count + ". " + "Task/Event: " + this.desc + "\nDate: " + this.date+ 
								"\nPriority: " + this.priority + "\n\n";
																					
							raiseAlarm = true;						
							
							
						}
			
						
						
					}catch(Exception e){System.out.println(e);}
					
					
				}
				
			}
			
				
		}
		
		try{
			if(raiseAlarm == true){
			
				//send reminder email
				sendEmail.sendEmail(receiverEmail.read(),concateTask);
			}
		}catch(Exception e){System.out.println(e);}
		
		//store the task/event details for the label of reminder
		Global.reminderDesc += "</html>";
		
		return raiseAlarm;
		
	}	
		
}
