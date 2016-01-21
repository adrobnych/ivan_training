package calcs;

public class ComplexCalc {
	public int getResult() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return 1;
	}
}
