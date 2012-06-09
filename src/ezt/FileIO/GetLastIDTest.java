package ezt.FileIO;

import static org.junit.Assert.*;

import org.junit.Test;

public class GetLastIDTest {

	@Test
	public void testGetLastID() {
				
		int actualMessage = GetLastID.getLastID();
		int expectedMessage = 5;
		
		assertEquals(expectedMessage,actualMessage);
	}

}
