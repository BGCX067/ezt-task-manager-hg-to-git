package ezt.BasicTaskFunc;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.google.gdata.util.ServiceException;

public class UpdateTaskTest {

	@Test
	public void testUpdate(){
		UpdateTask update = new UpdateTask();
		boolean result = false;
		boolean expected = true;
		result = update.update("6", "test test", "from 23-Jul-12 to 23-Jul-12", "12-13", "High", true, "Active");
		assertEquals(result,expected);
	}

}
