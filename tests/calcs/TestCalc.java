package calcs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestCalc {
	@Mock private ComplexCalc mockcc;
	@InjectMocks private Calc c;
	
	@Before
	public void setUp() throws Exception {
		c = new Calc();
		MockitoAnnotations.initMocks(this);
		when(mockcc.getResult()).thenReturn(1);
	}

	@After
	public void tearDown() throws Exception {
		c = null;
	}

	@Test
	public void testAdd() {
		assertEquals(5, c.add(2, 3));
	}

	@Test
	public void testSub() {
		assertEquals(-1, c.sub(2, 3));
	}

	@Test
	public void testMult() {
		assertEquals(6, c.mult(2, 3));
	}

	@Test
	public void testDiv() {
		assertEquals(0, c.div(2, 3));
	}

	@Test(expected = ArithmeticException.class)
	public void testDivByZero() {
		c.div(2, 0);
	}

	@Test
	public void testComplx() {
		assertEquals(1, c.complx());
	}
}
