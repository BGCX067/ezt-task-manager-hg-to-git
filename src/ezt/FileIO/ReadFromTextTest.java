package ezt.FileIO;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ReadFromTextTest {

	@Test
	public void testRead() {
		
		String searhTaskID="1";
		
		String actualMessage = ReadFromText.read(searhTaskID);
		String expectedMessage = "1.dinner.from 03-Oct-12 to 03-Nov-12.07-08.medium.true.Active.";
		
		assertEquals(expectedMessage,actualMessage);
		
	}

	@Test
	public void testReadByDesc() {

		String desc="dinner";
		
		Object [][] taskObject = ReadFromText.readByDesc(desc);		
		
		String actualMessage = taskObject[0][0].toString()+"."+taskObject[0][1].toString()+"."+taskObject[0][2].toString()+"."+taskObject[0][3].toString()+
				"."+taskObject[0][4].toString()+"."+taskObject[0][5].toString()+"."+taskObject[0][6].toString()+".";
		
		String expectedMessage = "from 03-Oct-12 to 03-Nov-12.dinner.medium.true.1.07-08.Active.";
		
		assertEquals(expectedMessage,actualMessage.toString());
	}

	@Test
	public void testReadByStatus() {
		String status="active";
		
		Object [][] taskObject = ReadFromText.readByStatus(status);		
		
		String actualMessage = taskObject[0][0].toString()+"."+taskObject[0][1].toString()+"."+taskObject[0][2].toString()+"."+taskObject[0][3].toString()+
				"."+taskObject[0][4].toString()+"."+taskObject[0][5].toString()+"."+taskObject[0][6].toString()+".";
		
		String expectedMessage = "from 03-Oct-12 to 03-Nov-12.dinner.medium.true.1.07-08.Active.";
		
		assertEquals(expectedMessage,actualMessage.toString());
	}

	@Test
	public void testReadByPriority() {
		String priority="medium";
		
		Object [][] taskObject = ReadFromText.readByPriority(priority);		
		
		String actualMessage = taskObject[0][0].toString()+"."+taskObject[0][1].toString()+"."+taskObject[0][2].toString()+"."+taskObject[0][3].toString()+
				"."+taskObject[0][4].toString()+"."+taskObject[0][5].toString()+"."+taskObject[0][6].toString()+".";
		
		String expectedMessage = "from 03-Oct-12 to 03-Nov-12.dinner.medium.true.1.07-08.Active.";
	
		assertEquals(expectedMessage,actualMessage.toString());
	}

	@Test
	public void testReadWordList() {
		
		
		List<String> list= new ArrayList<String>();
		list = ReadFromText.readWordList();		
		
		String actualMessage = list.toString();
		
		String expectedMessage = "[add, update, delete, s, s-p, s-s, Description:Clean House, Description:jack's birthday, Description:meeting, Description:swimming, Description:dinner, Priority:low, Priority:medium, Priority:high, Alert:yes, Alert:no, Status:Active, Status:Non Active, Description:birthday, Description:work, Description:Clean House, Description:Clean House, Description:from 21-Jun-12 to 21-Jun-12, Description:from 21-Jun-12 to 21-Jun-12]";
		
		assertEquals(expectedMessage,actualMessage.toString());
	}

}
