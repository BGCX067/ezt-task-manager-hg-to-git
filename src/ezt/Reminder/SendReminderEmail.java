package ezt.Reminder;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

//send reminder email
public class SendReminderEmail {

    private static final String SMTP_HOST_NAME = "smtp.gmail.com";//email protocol
    private static final int SMTP_HOST_PORT = 465;//email port no
    private static final String SMTP_AUTH_USER = "vincentyueng@gmail.com";//email sending acc username
    private static final String SMTP_AUTH_PWD  = "vy321259";//email sending acc password

    //send reminder email method
    public static void sendEmail(String receiverEmail, String messages) throws Exception{
    	
        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", SMTP_HOST_NAME);
        props.put("mail.smtps.auth", "true");
       
        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject("EZ Todo Manager Reminder");//set the email subject
        message.setContent(messages, "text/plain");

        //set the recipient of the email reminder
        message.addRecipient(Message.RecipientType.TO,
             new InternetAddress(receiverEmail));

        transport.connect
          (SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

      //set the email message
        transport.sendMessage(message,
            message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
}