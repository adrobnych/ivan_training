package test.calcs;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import calcs.Calc;
import calcs.Rectangle;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Calc.class, Rectangle.class})
public class CalcPMTest {
	private Calc calc;
	
	@Before
	public void createCalc() throws Exception {
		calc = new Calc();
	}

	@Test
	public void rectangleSquareGivesMockResult() throws Exception {
		Rectangle mockRect = PowerMockito.mock(Rectangle.class);
		PowerMockito.doReturn(100).when(mockRect).getSquare();
		PowerMockito.whenNew(Rectangle.class).withAnyArguments().thenReturn(mockRect);
		
		int square = calc.rectangleSquare(-2, -3);

		assertThat(square, equalTo(100));
	}
}
