package ezt.BasicTaskFunc;

import ezt.FileIO.*;

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
		DateFormat formatter ; 
		Date td=null, startDate=null, endDate=null;
		Calendar todayDate;
					
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
				
				formatter = new SimpleDateFormat("dd-MMM-yy");
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
			if((this.time.equalsIgnoreCase("nil")) && ((td.after(startDate) && td.before(endDate)) || td.equals(startDate) || td.equals(endDate))){				
				
				alltask [g][0]= this.id;		
				alltask [g][1]= this.desc;
				
				g++;
			}
			}
			
		}
		
		return alltask;
		
	}	
}
