import java.util.List;
import java.util.Deque;

public class TSPState implements Comparable<TSPState> {
	
	public TSPState(
			double priority,
			double distance,
			Deque<Posn> travelledCities,
			List<Posn> untravelledCities) {
		this.setDistance(distance);
		this.setPriority(priority);
		this.setTravelledCities(travelledCities);
		this.setUntravelledCities(untravelledCities);
	}
	

	private double priority;
	private double distance;
	private Deque<Posn> travelledCities;
	private List<Posn> untravelledCities;
	
	@Override
	public int compareTo(TSPState o) {
		return new Double(this.getPriority()).compareTo(new Double(o.getPriority()));
	}

	public void setPriority(double priority) {
		this.priority = priority;
	}

	public double getPriority() {
		return priority;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}

	public void setTravelledCities(Deque<Posn> travelledCities) {
		this.travelledCities = travelledCities;
	}

	public Deque<Posn> getTravelledCities() {
		return travelledCities;
	}

	public void setUntravelledCities(List<Posn> untravelledCities) {
		this.untravelledCities = untravelledCities;
	}

	public List<Posn> getUntravelledCities() {
		return untravelledCities;
	}
}
