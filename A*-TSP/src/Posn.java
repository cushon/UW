
public class Posn implements Comparable<Posn> {

	Posn(String name, int x, int y) {
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	private int x;
	private int y;
	private String name;
	
	public void setX(int x) {
		this.x = x;
	}
	public int getX() {
		return x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getY() {
		return y;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	@Override
	public int compareTo(Posn o) {
		return this.getName().compareTo(o.getName());
	}
	
	public double distanceFrom(Posn o) {
		return Math.sqrt(
				Math.pow(this.getX() - o.getX(), 2) +
				Math.pow(this.getY() - o.getY(), 2));
	}
}