package ezt.Export;

import ezt.DetectInput.*;

import java.io.*;

import javax.swing.JOptionPane;

public class Export
{
	public void ExportHTML(String filename) throws IOException
	{
		DetectInput d = new DetectInput();
		Object[][] List= d.allTasks(); //new function
		
		int length = List.length;
		
		String userHomeFolder = System.getProperty("user.home");
		
		filename = userHomeFolder + "\\Desktop\\" + filename + ".htm";
		
		FileWriter f = new FileWriter(filename);
		PrintWriter Export = new PrintWriter(f);
		
		Export.print("<HTML>\n");
		Export.print("<TITLE>\n");
		Export.write("EZ TODO MANAGER DATA EXPORT\n");
		Export.write("</TITLE>\n");
		Export.write("<BODY>\n");
		Export.write("<h1>EZ TODO MANAGER DATA EXPORT</h1><br /><br />");
		
		Export.write("<TABLE BORDER='1' CELLPADDING='5' BORDERCOLOR='#999999'>");
		Export.write("<TR bgcolor='#6699CC'>");
		Export.write("<TD><b> Id </b></TD>");
		Export.write("<TD><b> Date </b></TD>");
		Export.write("<TD><b> Time </b></TD>");
		Export.write("<TD><b> Status </b></TD>");
		Export.write("<TD><b> Description </b></TD>");
		Export.write("<TD><b> Priority </b></TD>");
		Export.write("<TD><b> Alert </b></TD>");
		Export.write("</TR>");

		for(int i=0; i<length; i++)
		{
			Export.write("<TR>");
			Export.write("<TD>");
			Export.write(List[i][4].toString());
			Export.write("</TD>");
			Export.write("<TD>");
			Export.write(List[i][5].toString());
			Export.write("</TD>");
			Export.write("<TD>");
			Export.write(List[i][7].toString());
			Export.write("</TD>");
			Export.write("<TD>");
			Export.write(List[i][6].toString());
			Export.write("</TD>");
			Export.write("<TD>");
			Export.write(List[i][1].toString());
			Export.write("</TD>");
			Export.write("<TD>");
			Export.write(List[i][2].toString());
			Export.write("</TD>");
			Export.write("<TD>");
			Export.write(List[i][3].toString());
			Export.write("</TD>");
			Export.write("</TR>");
		}
		
		Export.write("</Body>");
		Export.write("</HTML>");
		Export.close();
	
		JOptionPane.showMessageDialog(null, "Export to HTML successfully.", "Message", 1);
		
	}
	
	public void ExportTXT(String filename) throws IOException
	{
		DetectInput d = new DetectInput();
		Object[][] List= d.allTasks(); //new function
		
		int length = List.length;
		
		String userHomeFolder = System.getProperty("user.home");
		
		filename = userHomeFolder + "\\Desktop\\" + filename + ".txt";
		
		FileWriter f = new FileWriter(filename);
		PrintWriter Export = new PrintWriter(f);
		
		for(int i=0; i<length; i++)
		{
			Export.write(List[i][4].toString());
			Export.write("/");
			Export.write(List[i][1].toString());
			Export.write("/");
			Export.write(List[i][5].toString());
			Export.write("/");
			Export.write(List[i][7].toString());
			Export.write("/");
			Export.write(List[i][2].toString());
			Export.write("/");
			Export.write(List[i][3].toString());
			Export.write("/");
			Export.write(List[i][6].toString());
			Export.write("/");
			Export.write("\n");
		}

		Export.close();
		
		JOptionPane.showMessageDialog(null, "Export to text successfully.", "Message", 1);
	
	}
}