package ezt.DetectInput;

import static org.junit.Assert.*;

import org.junit.Test;

import ezt.BasicTaskFunc.GetAllTask;

public class DetectInputTest {

	@Test
	public void testDetect() {
		DetectInput g = new DetectInput();
		boolean result = false;
		boolean expected = true;
		result = g.detect("add test,from 21-Jun-12 to 21-Jun-12,12-13,high,true,Active");		
		assertEquals(result,expected);
	}
	
}
