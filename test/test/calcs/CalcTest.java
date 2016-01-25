package test.calcs;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.*;
import org.mockito.*;

import calcs.Calc;
import calcs.ComplexCalc;

public class CalcTest {
	@Mock private ComplexCalc mockComplexCalc;
	@InjectMocks private Calc calc;
	
	@Before
	public void createCalc() throws Exception {
		calc = new Calc();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void addingTwoNumbersGivesExpectedResult() {
		assertEquals(5, calc.add(2, 3));
	}

	@Test
	public void subtractionTwoNumbersGivesExpectedResult() {
		assertEquals(-1, calc.sub(2, 3));
	}

	@Test
	public void multiplicationTwoNumbersGivesExpectedResult() {
		assertEquals(6, calc.mult(2, 3));
	}

	@Test
	public void divisionTwoNumbersGivesExpectedResult() {
		assertEquals(0, calc.div(2, 3));
	}

	@Test(expected = ArithmeticException.class)
	public void divisionByZeroCausesException() {
		calc.div(2, 0);
	}

	@Test(timeout = 200)
	public void complexCalculationsGivesExpectedResult() {
		when(mockComplexCalc.getResult()).thenReturn(1);
		
		assertThat(calc.complx(), equalTo(1));
	}
}
