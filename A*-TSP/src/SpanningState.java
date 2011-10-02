public class SpanningState implements Comparable<SpanningState> {
	
	public SpanningState(double priority, Posn value, Posn parent) {
		this.priority = priority;
		this.value = value;
		this.parent = parent;
	}
	
	public double priority;
	public Posn value;
	public Posn parent;
	
	@Override
	public int compareTo(SpanningState o) {
		if (this.priority != o.priority) {
			return this.priority < o.priority ? -1 : 1;
		} else {
			return 0;
		}
	}
}