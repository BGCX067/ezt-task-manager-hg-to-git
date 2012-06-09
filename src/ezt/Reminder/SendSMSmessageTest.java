package ezt.Reminder;

import static org.junit.Assert.*;

import org.junit.Test;

public class SendSMSmessageTest {

	@Test
	public void testSendMessage() {
		SendSMSmessage sendsms = new SendSMSmessage();
		
		sendsms.sendMessage("65"+"84018091", "Rem: "+ "test", "", "", "", "");
	}

}
