package calcs;

public class Calc {
	private ComplexCalc complexCalc = new ComplexCalc();
	
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
		int temp = complexCalc.getResult();
		
		return temp * 1;
	}
	
	public int rectangleSquare(int width, int height) {
		return (new Rectangle(width, height)).getSquare();
	}
	
	public static void main(String[] args) {
		Calc calc = new Calc();
		int x = 2;
		int y = 3;
		int width = 2, height = 3;

		System.out.println(String.format("%1$d + %2$d = %3$d", x, y, calc.add(x, y)));
		System.out.println(String.format("\nSquare of rectangle (%1$d x %2$d) = %3$d", width, height, calc.rectangleSquare(2, 3)));
		
		System.out.println("\nStart complex caluclations");
		System.out.println(String.format("End complex caluclations\nResult = %1$d", calc.complx()));
	}
}
