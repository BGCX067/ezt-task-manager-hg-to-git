package ezt.BasicTaskFunc;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.google.gdata.util.ServiceException;

public class UpdateStatusTest {

	@Test
	public void testStatusUpdate(){
		UpdateStatus us = new UpdateStatus();
		boolean result = false;
		boolean expected = true;
		result = us.statusUpdate("6", "Non Active");
		assertEquals(result,expected);
	}

}
