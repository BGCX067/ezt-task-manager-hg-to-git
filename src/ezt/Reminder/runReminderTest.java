package ezt.Reminder;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class runReminderTest {

	@Test
	public void testReminder() {
		
		boolean haveReminder = false;
		boolean expected = true;
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		
		Calendar reminderTodayDate = Calendar.getInstance();
		
		reminderTodayDate.set(Calendar.HOUR_OF_DAY, 0);
						
		reminderTodayDate.set(Calendar.MINUTE, 0);
		reminderTodayDate.set(Calendar.SECOND, 0);
		reminderTodayDate.set(Calendar.MILLISECOND, 0);
		Date reminderTd = reminderTodayDate.getTime();
		
		runReminder reminder = null;
		
		haveReminder = reminder.reminder(formatter.format(reminderTd));
		
		assertEquals(haveReminder,expected);
	}

}
