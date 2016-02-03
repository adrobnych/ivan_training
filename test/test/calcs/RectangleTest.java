package test.calcs;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import calcs.Rectangle;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Rectangle.class)
public class RectangleTest {
	private Rectangle rectangle;
	
	@Before
	public void createSpyForRectangle() throws Exception {
		// rectangle =  spy(new Rectangle(2, 3));   // this won't work correct with final/private
		rectangle = PowerMockito.spy(new Rectangle(2, 3));
	}

	@Test
	public void constructorSetsCorrectInternalState() {
		Rectangle rect = new Rectangle(4, 5);
		
		int width = Whitebox.getInternalState(rect, "width");
		int height = Whitebox.getInternalState(rect, "height");
		
		assertThat(width, equalTo(4));
		assertThat(height, equalTo(5));
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorWithNotPositiveParamsCausesException() {
		new Rectangle(0, 10);
	}

	@Test
	public void mockConstructorWithAnyParamsReturnsMockRectangle() throws Exception {
		PowerMockito.mockStatic(Rectangle.class);
		Rectangle mockRect = PowerMockito.mock(Rectangle.class);
		
		PowerMockito.when(Rectangle.getName()).thenReturn("RECTANGLE");                  // stub static
		PowerMockito.doReturn(100).when(mockRect).getSquare();                           // stub final
		PowerMockito.doReturn(200).when(mockRect, "getPerimeter");                       // stub private
		PowerMockito.doReturn("INFO").when(mockRect).getInfo();                          // stub public
		// when(mockRect.getInfo()).thenReturn("INFO");                                  // stub public - this works too
		PowerMockito.whenNew(Rectangle.class).withAnyArguments().thenReturn(mockRect);   // stub constructor
		
		Rectangle rect = new Rectangle(-2, -3);    // get mock Rectangle ("mockRect") and work with him via "rect"
		String name = Rectangle.getName();
		int square = rect.getSquare();
		int perimeter = Whitebox.invokeMethod(rect, "getPerimeter");
		String info = rect.getInfo();
		
		assertThat(name, equalTo("RECTANGLE"));
		assertThat(square, equalTo(100));
		assertThat(perimeter, equalTo(200));
		assertThat(info, equalTo("INFO"));
	}

	@Test
	public void getNameGivesExpectedResult() {
		assertThat(Rectangle.getName(), equalTo("Rectangle"));
	}

	@Test
	public void getSquareGivesExpectedResult() {
		assertThat(rectangle.getSquare(), equalTo(6));
	}
	
	@Test
	public void getPerimeterGivesExpectedResult() throws Exception {
		int perimeter = Whitebox.invokeMethod(rectangle, "getPerimeter");
		
		assertThat(perimeter, equalTo(10));
	}
	
	@Test
	public void getInfoCallsAllOtherMethods() throws Exception {
		PowerMockito.mockStatic(Rectangle.class);   // is needed for PowerMockito.verifyStatic() - otherwise exception is raised
		
		rectangle.getInfo();
		
		PowerMockito.verifyStatic(times(1));
		Rectangle.getName();                                                      // verify static
		
		verify(rectangle, times(1)).getSquare();                                  // verify final
		PowerMockito.verifyPrivate(rectangle, times(1)).invoke("getPerimeter");   // verify private
	}
}
