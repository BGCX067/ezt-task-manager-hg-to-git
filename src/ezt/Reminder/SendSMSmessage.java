/*Author: Yueng Shu Sheng
 * Purpose:send reminder sms
*/
package ezt.Reminder;

import javax.servlet.*;
import javax.servlet.http.*;

import java.net.*;
import java.io.*;   

//send reminder sms
public class SendSMSmessage extends HttpServlet
{
    public void init(ServletConfig config) throws ServletException
    {
       super.init(config);
    }

    //send reminder sms method
    public static boolean sendMessage(String phoneNo, String msg, String pic, String op, String profilenm, String model)
    {
     String host = "www.smsxchange.com";
     int port = 80;
     String user = "ezt_2"; // smsxchange username
     String passwd = "90907";  //smsxchange password
     String query;
     String headers;
     int length_query;
     int length1;

     Socket soc = null;
     DataOutputStream o =null;
     DataInputStream i =null;
     
     try {
    	 
	  // Create a socket  
	  soc = new Socket(host , port );
	  o = new DataOutputStream(soc.getOutputStream());
	  i= new DataInputStream(soc.getInputStream());

      query = "UserID=" + user +
              "&Password=" + passwd + 
		      "&Phone=" + phoneNo +
              "&SMS=" + msg +
              "&PIC=" + pic +
              "&OP=" + op +
              "&profileNM =" + profilenm +
              "&model=" + model;

      length_query = query.length();
      headers = "Host: www.smsxchange.com\n"  
                        + "Content-length: " + length_query + "\n"
                        + "Content-type: application/x-www-form-urlencoded";


	  o.writeBytes("POST /scripts/sendsms.asp HTTP/1.0\n" +
                            headers + "\n\n" +
                            query);

	  o.flush(); 
      System.out.println(i.available());//print sms initiate message 
	  System.out.println(i.read());//print sms initiate message
      o.close();


     } 
     catch (Exception e) {
    	 System.out.println(e);
	return false;
     }
  return true;
}
}






