package ezt.BasicTaskFunc;

import ezt.DetectInput.Global;
import ezt.FileIO.*;
import ezt.Reminder.SendReminderEmail;
import ezt.Reminder.SendSMSmessage;
import ezt.Reminder.runReminder;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.text.*;

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
		
		if(previousAdded(this.date,this.time) == false){
			
			WriteToText writeTask = new WriteToText();
			writeTask.write(this);
			
			success = true;
		
		}
		
		return success;
		
	}
	
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
		
		GetLastID fileIO = new GetLastID();
		lastID = fileIO.getLastID();
				
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
					
				if((td.after(startDate) && td.before(endDate)) || td.equals(startDate) || td.equals(endDate)){
						
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
		
		// create a new smaller array
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
		
		// create a new smaller array
	    newAllTask = new Object[g][8];
		
	    for (int i=0;i<g;i++) {	        
		
	        	for(int c=0; c<8; c++){
	        		newAllTask[i][c] = alltask[i][c];
	        	}
	        
	    }
				
		return newAllTask;
		
	}	
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
								
				runReminder reminder = new runReminder();
			            
				haveReminder = reminder.reminder(formatter.format(reminderTd));			
				
				
				if(haveReminder==true){
										
					ReadHpNo receiverHpNo = new ReadHpNo();
													
					SendSMSmessage sendsms = new SendSMSmessage();
				
					todayDate = Calendar.getInstance();
					
					td = todayDate.getTime();
					
			
					try{
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
																				
							Global.reminderDesc += count + ". " + this.desc + " from " + this.time.substring(0,2) + " " + frm + 
									" till " + this.time.substring(3) + " "+ til+"<br><br>";
							
							//send sms alert, msg cannot be too long due to free sms server
						 //  sendsms.sendMessage("65"+receiverHpNo.read(), "Rem: "+ this.desc + " | Time: "+ this.time, "", "", "", "");
							
							concateTask += "\n\n" + count + ". " + "Task/Event: " + this.desc + "\nDate: " + this.date+ 
									"\nTime: " + " from " + this.time.substring(0,2) + " " + frm + 
									" till " + this.time.substring(3) + " "+ til + "\nPriority: " + this.priority + "\n\n";
							
							raiseAlarm = true;						
							
							
						}
						
						if(this.time.equalsIgnoreCase("nil") && ((td.getMonth()+1>=startDate.getMonth()+1)) && ((td.getDate()+1==startDate.getDate()+1))){
							
							count++;
							
							Global.reminderDesc += count + ". " + this.desc +"<br><br>";
							
							//send sms alert, msg cannot be too long due to free sms server
							//sendsms.sendMessage("65"+receiverHpNo.read(), "Rem: "+ this.desc, "", "", "", "");
							
							//send reminder email
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
		
		Global.reminderDesc += "</html>";
		
		return raiseAlarm;
		
	}	
		
}
