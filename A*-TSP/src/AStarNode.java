import java.util.List;
import java.util.Deque;

/**
 * AStarNode holds the state of a single node in an A* search. 
 */
public class AStarNode implements Comparable<AStarNode> {
	
	// Keep track of how many nodes are instantiated.
	public static int instantions;
	
	// The node's expansion priority (cost to get to this node + estimated cost to finish search 
	// from this node) 
	private double priority;
	// The distance traveled to reach this node
	private double distance;
	// The path taken to reach this node.
	private Deque<Posn> travelledCities;
	// The cities that we haven't traveled to yet.
	private List<Posn> untravelledCities;
	
	/**
	 * Nodes to need to be comparable by priority so we can store them in a priority
	 * queue.
	 */
	@Override
	public int compareTo(AStarNode o) {
		return new Double(this.getPriority()).compareTo(new Double(o.getPriority()));
	}
	
	public AStarNode(
			double priority,
			double distance,
			Deque<Posn> travelledCities,
			List<Posn> untravelledCities) {
		this.setDistance(distance);
		this.setPriority(priority);
		this.setTravelledCities(travelledCities);
		this.setUntravelledCities(untravelledCities);
		AStarNode.instantions++;
	}

	public void setPriority(double priority) { this.priority = priority; }
	public double getPriority() { return priority; }
	public void setDistance(double distance) { this.distance = distance; }
	public double getDistance() { return distance; }
	public void setTravelledCities(Deque<Posn> travelledCities) { this.travelledCities = travelledCities; }
	public Deque<Posn> getTravelledCities() { return travelledCities; }
	public void setUntravelledCities(List<Posn> untravelledCities) { this.untravelledCities = untravelledCities; }
	public List<Posn> getUntravelledCities() { return untravelledCities; }
}
