package ezt.FileIO;

import static org.junit.Assert.*;

import org.junit.Test;

public class WriteHpNoTest {

	@Test
	public void testWrite() {

		boolean actualMessage = WriteHpNo.write("84028091");
		boolean expectedMessage = true;
		
		assertEquals(expectedMessage,actualMessage);
	}

}
