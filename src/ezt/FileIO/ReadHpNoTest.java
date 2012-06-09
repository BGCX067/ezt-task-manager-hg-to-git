package ezt.FileIO;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReadHpNoTest {

	@Test
	public void testRead() {

		String actualMessage = ReadHpNo.read();
		String expectedMessage = "84028091";
		
		assertEquals(expectedMessage,actualMessage);
	}

}
