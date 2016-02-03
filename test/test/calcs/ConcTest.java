package test.calcs;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import calcs.Conc;

@RunWith(PowerMockRunner.class)
public class ConcTest {
	private final long topStair = 3000000L;
	private final long expCountStairs = 4500001500000L;
	private Conc conc;
	
	@Before
	public void creatConc() {
		conc = new Conc(false);
	}
	
	@Test
	public void printThreadNameDisplaysCurrentThread() throws Exception {
		boolean showThreads = Whitebox.getInternalState(conc, "showThreads");
		
		Whitebox.setInternalState(conc, "showThreads", true);
		Whitebox.invokeMethod(conc, "printThreadName");
		Whitebox.setInternalState(conc, "showThreads", showThreads);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void countStairsSingleThreadWithNegativeParamCausesException() throws Exception {
		conc.countStairsSingleThread(-1);
	}

	@Test
	public void countStairsSingleThreadGivesExpectedResult() throws Exception {
		assertThat(conc.countStairsSingleThread(topStair), equalTo(expCountStairs));
	}
	
	@Test
	public void countStairsMultiThreadGivesExpectedResult() throws Exception {
		assertThat(conc.countStairsMultiThread(topStair), equalTo(expCountStairs));
	}
	
	@Test
	public void countStairsAtomicOperationGivesExpectedResult() throws Exception {
		assertThat(conc.countStairsAtomicOperation(topStair), equalTo(expCountStairs));
	}
}
