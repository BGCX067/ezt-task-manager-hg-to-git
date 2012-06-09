package ezt.BasicTaskFunc;

import static org.junit.Assert.*;

import org.junit.Test;

public class GetAllTaskTest {

	@Test
	public void testAllTaskDay() {
		GetAllTask g = new GetAllTask();
		Object [][] result = new Object[1][7];
		String expected = "01:00 - 02:00";
		result = g.allTaskDay("8-Jun-12");		
		assertEquals(result[0][0].toString(),expected);
	}

	@Test
	public void testAllTaskWeek() {
		GetAllTask g = new GetAllTask();
		Object [][] result = new Object[1][7];
		String expected = "Wednesdaydinnersmediumtrue5from 06-Jun-12 to 06-Dec-12Active12-13";
		result = g.allTaskWeek("8-Jun-12");		
		assertEquals(result[0][0].toString()+result[0][1].toString()+result[0][2].toString()+
				result[0][3].toString()+result[0][4].toString()+result[0][5].toString()+result[0][6].toString()+result[0][7].toString(),expected);
	}

	@Test
	public void testAllTaskMonth() {
		GetAllTask g = new GetAllTask();
		Object [][] result = new Object[1][7];
		String expected = "Wed Jun 06 00:00:00 SGT 2012dinnersmediumtrue5from 06-Jun-12 to 06-Dec-12Active12-13";
		result = g.allTaskMonth("8-Jun-12");				
		assertEquals(result[0][0].toString()+result[0][1].toString()+result[0][2].toString()+
				result[0][3].toString()+result[0][4].toString()+result[0][5].toString()+result[0][6].toString()+result[0][7].toString(),expected);
	}

	@Test
	public void testAllEventToday() {
		GetAllTask g = new GetAllTask();
		Object [][] result = new Object[1][7];
		String expected = "4Party";
		result = g.allEventToday("8-Jun-12");				
		
		assertEquals(result[0][0].toString()+result[0][1].toString(),expected);
	}

	@Test
	public void testIsEvent() {
		GetAllTask g = new GetAllTask();
		boolean result = false;
		boolean expected = false;
		result = g.isEvent("1");				
		
		assertEquals(result,expected);
	}

}
