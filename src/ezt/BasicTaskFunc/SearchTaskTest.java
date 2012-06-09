package ezt.BasicTaskFunc;

import static org.junit.Assert.*;

import org.junit.Test;

public class SearchTaskTest {

	@Test
	public void testSearchByDesc() {
		SearchTask s = new SearchTask();
		Object [][] result = s.searchByDesc("dinner");
		String expected = "dinner";
		assertEquals(result[0][1],expected);
		
	}

	@Test
	public void testSearchByPriority() {
		SearchTask s = new SearchTask();
		Object [][] result = null;
		result = s.searchByPriority("medium");
		String expected = "medium";
		
		assertEquals(result[0][2],expected);
	}

	@Test
	public void testSearchByStatus() {
		SearchTask s = new SearchTask();
		Object [][] result = s.searchByStatus("active");
		String expected = "Active";
		assertEquals(result[0][6],expected);
	}

}
