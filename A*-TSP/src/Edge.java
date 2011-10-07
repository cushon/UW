// Stores a pair of Posn. (ie an egde)
// It would be delightful if Java had a Pair<,> built-in.
public class Edge {

	Edge(Posn a, Posn b) {
		this.a = a;
		this.b = b;
	}
	
	private Posn a;
	private Posn b;
	
	public void setA(Posn a) { this.a = a; }
	public Posn getA() { return a; }
	public void setB(Posn b) { this.b = b; }
	public Posn getB() { return b; }
}
