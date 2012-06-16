/*Author: Yueng Shu Sheng
 * Purpose:to sych the task/event to Google Calendar
*/
package ezt.GoogleSync;

import com.google.gdata.client.Query;
import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.batch.BatchOperationType;
import com.google.gdata.data.batch.BatchStatus;
import com.google.gdata.data.batch.BatchUtils;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.data.calendar.WebContent;
import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.data.extensions.Recurrence;
import com.google.gdata.data.extensions.Reminder;
import com.google.gdata.data.extensions.Reminder.Method;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.ServiceException;

import ezt.DetectInput.Global;

import java.awt.Font;
import java.awt.SystemColor;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class EventFeed {

  // The base URL for a user's calendar metafeed (needs a username appended).
  private static final String METAFEED_URL_BASE = 
      "https://www.google.com/calendar/feeds/";

  // The string to add to the user's metafeedUrl to access the event feed for
  // their primary calendar.
  private static final String EVENT_FEED_URL_SUFFIX = "/private/full";

  // The URL for the metafeed of the specified user.
  private static URL metafeedUrl = null;

  // The URL for the event feed of the specified user's primary calendar.
  private static URL eventFeedUrl = null;

  
  /**
   * Instantiates a CalendarService object and uses the command line arguments
   * to authenticate. The CalendarService object is used to demonstrate
   * interactions with the Calendar data API's event feed.
   * 
   * @param args Must be length 2 and contain a valid username/password
   */
  /**
   * Creates a new recurring event.
   * 
   * @param service An authenticated CalendarService object.
   * @param eventTitle Title of the event to create.
   * @param eventContent Text content of the event to create.
   * @return The newly-created CalendarEventEntry.
   * @throws ServiceException If the service is unable to handle the request.
   * @throws IOException Error communicating with the server.
   */
  private static CalendarEventEntry createRecurringEvent(
      CalendarService service, String eventTitle, String eventContent, String startDates, String startTimes,String endDates, String endTimes)
      throws ServiceException, IOException {
	  
	  String recurData="";
	  
	  if(startTimes.equalsIgnoreCase("000000") && endTimes.equalsIgnoreCase("230000")){
		  
		// syntax; see http://www.ietf.org/rfc/rfc2445.txt for more information
		  recurData = "DTSTART;VALUE=DATE:"+startDates+"\r\n"
          + "DTEND;VALUE=DATE:"+startDates+"\r\n"
          + "UNTIL="+startDates+"\r\n";
		  
	  }else{
		  
	  String increasedEndDates = String.format("%02d%n",Integer.parseInt(endDates)+01);
	  
	  recurData = "X-WR-TIMEZONE:Asia/Singapore\r\n" +
	    				"DTSTART;VALUE=DATE-TIME:"+startDates+"T"+startTimes+"Z\r\n"
	            + "DTEND;VALUE=DATE-TIME:"+startDates+"T"+endTimes+"Z\r\n"
	            + "RRULE:FREQ=DAILY;UNTIL="+increasedEndDates+"\r\n";
	  }

    return createEvent(service, eventTitle, eventContent, recurData, false,
        null);
  }
  
  /**
   * Helper method to create either single-instance or recurring events. For
   * simplicity, some values that might normally be passed as parameters (such
   * as author name, email, etc.) are hard-coded.
   * 
   * @param service An authenticated CalendarService object.
   * @param eventTitle Title of the event to create.
   * @param eventContent Text content of the event to create.
   * @param recurData Recurrence value for the event, or null for
   *        single-instance events.
   * @param isQuickAdd True if eventContent should be interpreted as the text of
   *        a quick add event.
   * @param wc A WebContent object, or null if this is not a web content event.
   * @return The newly-created CalendarEventEntry.
   * @throws ServiceException If the service is unable to handle the request.
   * @throws IOException Error communicating with the server.
   */
  private static CalendarEventEntry createEvent(CalendarService service,
      String eventTitle, String eventContent, String recurData,
      boolean isQuickAdd, WebContent wc) throws ServiceException, IOException {
    CalendarEventEntry myEntry = new CalendarEventEntry();

    myEntry.setTitle(new PlainTextConstruct(eventTitle));
    myEntry.setContent(new PlainTextConstruct(eventContent));
    myEntry.setQuickAdd(isQuickAdd);
    myEntry.setWebContent(wc);

    // If a recurrence was requested, add it. Otherwise, set the
    // time (the current date and time) and duration (30 minutes)
    // of the event.
    if (recurData == null) {
      Calendar calendar = new GregorianCalendar();
      DateTime startTime = new DateTime(calendar.getTime(), TimeZone
          .getDefault());

      calendar.add(Calendar.MINUTE, 30);
      DateTime endTime = new DateTime(calendar.getTime(), 
          TimeZone.getDefault());

      When eventTimes = new When();
      eventTimes.setStartTime(startTime);
      eventTimes.setEndTime(endTime);
      myEntry.addTime(eventTimes);
    } else {
      Recurrence recur = new Recurrence();
      recur.setValue(recurData);
      myEntry.setRecurrence(recur);
    }

    // Send the request and receive the response:
    return service.insert(eventFeedUrl, myEntry);
  }
  
  //method to add task/event to google calendar
  public static void GoogleCalSyn(String taskToSyn,String dates, String times){ 
	
	  
    CalendarService myService = new CalendarService("Ez Task Manager");
    
    // Set username and password
    String userName = "vincentyueng@gmail.com";
    String userPassword = "vy321259";

    // Create the necessary URL objects.
    try {
      metafeedUrl = new URL(METAFEED_URL_BASE + userName);
      eventFeedUrl = new URL(METAFEED_URL_BASE + userName
          + EVENT_FEED_URL_SUFFIX);
    } catch (MalformedURLException e) {
      // Bad URL
      System.err.println("Uh oh - you've got an invalid URL.");
      e.printStackTrace();
      return;
    }

    try {
         	
      myService.setUserCredentials(userName, userPassword);
   
	  String changedTodayMonth="",changedTodayMonth2="";
	  String taskStartDate="",taskEndDate="",taskStartTime="",taskEndTime="";
		
	  changedTodayMonth = dates.substring(8,11);
	  changedTodayMonth2 = dates.substring(21,24);
		
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
		
	  taskStartDate="20"+dates.substring(12,14)+changedTodayMonth+dates.substring(5,7);
	  taskEndDate="20"+dates.substring(25)+changedTodayMonth2+dates.substring(18,20);
		
	  if(!times.equalsIgnoreCase("nil")){
		  taskStartTime = times.substring(0,2)+"0000";
		  taskEndTime = times.substring(3)+"0000";
	  }else{
		  taskStartTime = "000000";
		  taskEndTime = "230000";
	  }
	  
   //  creating a recurring event.
      CalendarEventEntry recurringEvent = createRecurringEvent(myService, taskToSyn, "",taskStartDate,taskStartTime,taskEndDate,taskEndTime);
           
    } catch (IOException e) {
      // Communications error
      System.err.println("There was a problem communicating with the service.");
      e.printStackTrace();
    } catch (ServiceException e) {
      // Server side error
      System.err.println("The server had a problem handling your request.");
      e.printStackTrace();
    }
  }
  
 //method to update task/event to google calendar
  public void updateGoogleCal(String oldTaskToSyn, String newTaskToSyn, String startDates, String endDates, String startTimes,String endTimes){
	  
	  // Set username and password
	    String userName = "vincentyueng@gmail.com";
	    String userPassword = "vy321259";	  
		   
	    try{
	    CalendarService myService = new CalendarService("Ez Task Manager");
	    myService.setUserCredentials(userName, userPassword);
	    
	    URL postURL = new URL("http://www.google.com/calendar/feeds/vincentyueng@gmail.com/private/full");
	    
	    //get the task which want to update from google cal
	    Query myQuery = new Query(postURL);
	    myQuery.setFullTextQuery(oldTaskToSyn);
	    CalendarEventFeed myResultsFeed = myService.query(myQuery,CalendarEventFeed.class);

	    Iterator iterFeed = myResultsFeed.getEntries().iterator();
	    CalendarEventEntry myEvent = (CalendarEventEntry) iterFeed.next();
	    
	    //update the previous task/event from google cal to new description
	    myEvent.setTitle(new PlainTextConstruct(newTaskToSyn));
	    URL editUrl = new URL(myEvent.getEditLink().getHref());
	    CalendarEventEntry updatedEntry = (CalendarEventEntry)myService.update(editUrl,  myEvent);		
	    
	    String recurData="";
	    System.out.println(endTimes);
	    //update the previous event from google cal to new time
		if(startTimes.equalsIgnoreCase("000000") && endTimes.equalsIgnoreCase("230000")){
	    	
	     // syntax; see http://www.ietf.org/rfc/rfc2445.txt for more information
	     recurData = "DTSTART;VALUE=DATE:"+startDates+"\r\n"
		          + "DTEND;VALUE=DATE:"+startDates+"\r\n"
		          + "UNTIL="+startDates+"\r\n";
	   
	    }else{
	    	
	    	//update the previous task from google cal to new time
	    	 String increasedEndDates = String.format("%02d%n",Integer.parseInt(endDates)+01);
			  
			 recurData = "X-WR-TIMEZONE:Asia/Singapore\r\n" +
			    				"DTSTART;VALUE=DATE-TIME:"+startDates+"T"+startTimes+"Z\r\n"
			            + "DTEND;VALUE=DATE-TIME:"+startDates+"T"+endTimes+"Z\r\n"
			            + "RRULE:FREQ=DAILY;UNTIL="+increasedEndDates+"\r\n";
		   
	    }
		
		//perform the task/event time date update
		 Recurrence recur = new Recurrence();
		 recur.setValue(recurData);
		 myEvent.setRecurrence(recur);
		 myEvent.update();

	    }catch(Exception ex){System.out.println(ex);}  	    
	 
  }
  
  //method to update task/event to google calendar
  public void deleteGoogleCal(String taskToSyn){
	  
	  // Set username and password
	    String userName = "vincentyueng@gmail.com";
	    String userPassword = "vy321259";	  
		   
	    try{
	    CalendarService myService = new CalendarService("Ez Task Manager");
	    myService.setUserCredentials(userName, userPassword);
	    
	    URL postURL = new URL("http://www.google.com/calendar/feeds/vincentyueng@gmail.com/private/full");
	    
	    //get the task which want to update from google cal
	    Query myQuery = new Query(postURL);
	    myQuery.setFullTextQuery(taskToSyn);
	    CalendarEventFeed myResultsFeed = myService.query(myQuery,CalendarEventFeed.class);

	    Iterator iterFeed = myResultsFeed.getEntries().iterator();
	    CalendarEventEntry myEvent = (CalendarEventEntry) iterFeed.next();
	    
	    myEvent.delete();

	    }catch(Exception ex){System.out.println(ex);}  	    
	 
  }

}