package ezt.Reminder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;

//run reminder frame
public class runReminder extends JFrame{

		
	public static boolean reminder(String taskDate) {

		DateFormat formatter ;		

		Date remindDate,td;
		Calendar todayDate;
		boolean remind = false;
		
		try{

			formatter = new SimpleDateFormat("dd-MMM-yy");
			remindDate = (Date)formatter.parse(taskDate); 
	
			todayDate = Calendar.getInstance();
			todayDate.set(Calendar.MINUTE, 0);
			todayDate.set(Calendar.SECOND, 0);
			todayDate.set(Calendar.MILLISECOND, 0);
			td = todayDate.getTime();
			
			//if the task date is same as current date and its one hour before time is now, active the the reminder
			if(td.getHours()==td.getHours()){
						
				remind = true;							
		    }
			
		}catch(Exception ex){System.out.println("Error in runReminder: "+ex);}
		
		return remind;
	}
}
