package ezt.BasicTaskFunc;

import static org.junit.Assert.*;

import org.junit.Test;

public class CreateTaskTest {

	@Test
	public void testCreate() {
		CreateTask create = new CreateTask();
		boolean result = false;
		boolean expected = true;
		result = create.create("test", "from 23-Jul-12 to 23-Jul-12", "14-15", "High", true, "Active");
		assertEquals(result,expected);
		
	}

}
