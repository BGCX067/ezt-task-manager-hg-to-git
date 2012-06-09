package ezt.Reminder;

import org.junit.Test;

public class SendReminderEmailTest {

	@Test
	public void test() throws Exception {
		SendReminderEmail sendEmail = new SendReminderEmail();
		sendEmail.sendEmail("vincentyueng@gmail.com", "test");
	}

}
