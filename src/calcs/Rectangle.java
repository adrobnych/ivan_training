package calcs;

public class Rectangle {
	private int width;
	private int height;
	
	public Rectangle(int width, int height) {
		if (width > 0 && height > 0) {
			this.width = width;
			this.height = height;
		} else {
			throw new IllegalArgumentException(String.format("Parameters \"width\" (%1$d) and \"height\" (%2$d) must be greater than zero (0)", width, height));
		}
	}
	
	public static String getName() {
		return "Rectangle";
	}
	
	public final int getSquare() {
		return width * height;
	}
	
	private int getPerimeter() {
		return 2 * (width + height);
	}
	
	@Override
	public String toString() {
		return String.format("%1$s (%2$d x %3$d)", getName(), width, height);
	}
	
	public String getInfo() {
		return String.format("%1$s\n\nsquare = %2$d\nperimeter = %3$d", toString(), getSquare(), getPerimeter());
	}
	
	public static void main(String[] args) {
		Rectangle rectangle = new Rectangle(2, 3);
		
		System.out.println(rectangle.getInfo());
	}
}
