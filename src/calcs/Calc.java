package calcs;

public class Calc {
	private ComplexCalc cc = new ComplexCalc();
	
	public int add(int x, int y) {
		return x + y;
	}

	public int sub(int x, int y) {
		return x - y;
	}

	public int mult(int x, int y) {
		return x * y;
	}

	public int div(int x, int y) {
		return x / y;
	}
	
	public int complx() {
		return cc.getResult();
	}
	
	public static void main(String[] args) {
		Calc c = new Calc();
		int x = 2;
		int y = 3;

		System.out.println(String.format("%1$d + %2$d = %3$d", x, y, c.add(x, y)));
		
		System.out.println("\nStart complex caluclations");
		System.out.println(String.format("End complex caluclations\nResult = %1$d", c.complx()));
	}
}
