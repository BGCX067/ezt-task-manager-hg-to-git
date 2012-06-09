package ezt.BasicTaskFunc;

import static org.junit.Assert.*;

import org.junit.Test;

public class DeleteTaskTest {

	@Test
	public void testDelete() {
		DeleteTask delete = new DeleteTask();
		boolean result = false;
		boolean expected = true;
		result = delete.delete(6);
		assertEquals(result,expected);
	}

}
