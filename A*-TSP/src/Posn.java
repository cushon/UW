// A Posn (position) stores a node's name and coordinates.
public class Posn implements Comparable<Posn> {
	
	private int x;
	private int y;
	private String name;
	
	Posn(String name, int x, int y) {
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	// Calculate the Euclidian distance from another Posn.
	public double distanceFrom(Posn o) {
		return Math.sqrt(
				Math.pow(this.getX() - o.getX(), 2) +
				Math.pow(this.getY() - o.getY(), 2));
	}
	
	// Parse a line from one of the problem input files into a Posn. Assume input is valid.
	public static Posn parsePosn(String input) {
		String[] pieces = input.split("\\s+");
		return new Posn(pieces[0], Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));
	}
	
	public void setX(int x) { this.x = x; }
	public int getX() { return x; }
	public void setY(int y) { this.y = y; }
	public int getY() { return y; }
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	
	@Override
	public int compareTo(Posn o) {
		return this.getName().compareTo(o.getName());
	}
}