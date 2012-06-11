package ezt.DetectInput;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.google.gdata.util.ServiceException;

import ezt.BasicTaskFunc.GetAllTask;

public class DetectInputTest {

	@Test
	public void testDetect() throws IOException, ServiceException {
		DetectInput g = new DetectInput();
		boolean result = false;
		boolean expected = true;
		result = g.detect("add test,from 21-Jun-12 to 21-Jun-12,12-13,high,true,Active");		
		assertEquals(result,expected);
	}
	
}
