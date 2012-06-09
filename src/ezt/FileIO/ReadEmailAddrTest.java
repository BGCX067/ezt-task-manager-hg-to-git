package ezt.FileIO;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReadEmailAddrTest {

	@Test
	public void testRead() {
		
		
		String actualMessage = ReadEmailAddr.read();
		String expectedMessage = "vincentyueng@gmail.com";
		
		assertEquals(expectedMessage,actualMessage);
	}

}
