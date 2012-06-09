package ezt.FileIO;

import static org.junit.Assert.*;

import org.junit.Test;

public class WriteEmailAddrTest {

	@Test
	public void testWrite() {

		boolean actualMessage = WriteEmailAddr.write("vincentyueng@gmail.com");
		boolean expectedMessage = true;
		
		assertEquals(expectedMessage,actualMessage);
	}

}
