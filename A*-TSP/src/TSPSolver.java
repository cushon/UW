import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class TSPSolver {

	public static double heuristic(Posn start, List<Posn> points) {
		Collections.sort(points);

		PriorityQueue<SpanningState> pq = new PriorityQueue<SpanningState>();

		for (Posn pt : points) {
			if (pt.compareTo(start) == 0) continue;
			pq.add(new SpanningState(pt.distanceFrom(start), pt, start));
		}

		List<Edge> edges = new ArrayList<Edge>();
		while(!pq.isEmpty()) {
			SpanningState current = pq.remove();
			edges.add(new Edge(current.parent, current.value));
			for (SpanningState state : pq) {
				double newDistance = current.value.distanceFrom(state.value);
				if (newDistance < state.priority) {
					state.priority = newDistance;
					state.parent = current.value;
				}
			}
		}
		
		double totalDistance = 0;
		for (Edge e : edges) {
			totalDistance += e.getA().distanceFrom(e.getB());
		}
		return totalDistance;
	}
	
	public static void main(String[] args) {
		final long startTime = System.nanoTime();
		
		Deque<Posn> travelled = new LinkedList<Posn>();
		travelled.add(new Posn("A", 82, 27));
		
		List<Posn> untravelled = new ArrayList<Posn>();
		untravelled.add(new Posn("B", 75, 11));
		untravelled.add(new Posn("C", 27, 33));
		untravelled.add(new Posn("D", 51, 53));
		untravelled.add(new Posn("E",  5, 29));
		untravelled.add(new Posn("F", 13,  8));
		untravelled.add(new Posn("G", 59, 79));
		untravelled.add(new Posn("H", 96, 26));
		untravelled.add(new Posn("I", 16, 32));
		untravelled.add(new Posn("J", 80, 91));
		
		TSPState start = new TSPState(0, 0, travelled, untravelled);
		
		PriorityQueue<TSPState> pq = new PriorityQueue<TSPState>();
		pq.add(start);
		
		TSPState goal;
		while (true) {
			
			TSPState current = pq.remove();
			
			if (current.getUntravelledCities().isEmpty()) {
				if (current.getTravelledCities().getFirst().getName().compareTo( 
						current.getTravelledCities().getLast().getName()) == 0) {
					goal = current;
					break;
				} else {
					Posn endCity = current.getTravelledCities().getFirst();
					current.getUntravelledCities().add(endCity);
				}
			}
			
			Posn lastCity = current.getTravelledCities().getLast();
			
			for (Posn next : current.getUntravelledCities()) {
				Deque<Posn> nowTravelled = new LinkedList<Posn>(current.getTravelledCities());
				nowTravelled.addLast(next);
				List<Posn> stillUntravelled = new ArrayList<Posn>(current.getUntravelledCities());
				stillUntravelled.remove(next);
				
				double newDistance = current.getDistance() + lastCity.distanceFrom(next);
				double newPriority = newDistance + heuristic(next, stillUntravelled);
				
				pq.add(new TSPState(newPriority, newDistance, nowTravelled, stillUntravelled));
			}
		}
		
		System.out.printf("A*: %d\n", System.nanoTime() - startTime);
		
		System.out.printf("Goal %f\n", goal.getDistance());
		
		for (Posn city : goal.getTravelledCities()) {
			System.out.printf("%s ", city.getName());
		}
		System.out.println();
		
		for (Posn city : goal.getTravelledCities()) {
			System.out.printf("%d ", city.getName().charAt(0) - 'A');
		}
		System.out.println();
		
		Posn[] all = new Posn[10];
		all[0] = new Posn("A", 82, 27);
		all[1] = new Posn("B", 75, 11);
		all[2] = new Posn("C", 27, 33);
		all[3] = new Posn("D", 51, 53);
		all[4] = new Posn("E", 5,  29);
		all[5] = new Posn("F", 13,  8);
		all[6] = new Posn("G", 59, 79);
		all[7] = new Posn("H", 96, 26);
		all[8] = new Posn("I", 16, 32);
		all[9] = new Posn("J", 80, 91);
		
		double alt = 0;
		double alt2 = 0;
		int[] path1 = {0, 7, 1, 5, 4, 8, 2, 3, 6, 9, 0};
		int[] path2 = {0, 1, 5, 4, 8, 2, 3, 6, 9, 7, 0};
		for (int i = 0; i < path1.length - 1; ++i) {
			alt += all[path1[i]].distanceFrom(all[path1[i+1]]);
			alt2 += all[path2[i]].distanceFrom(all[path2[i+1]]);
		}
		System.out.println(alt);
		System.out.println(alt2);
		
		System.out.printf("%f\n", all[0].distanceFrom(all[7]));
		System.out.printf("%f\n", all[7].distanceFrom(all[1]));
		
		System.out.printf("%f\n", all[0].distanceFrom(all[1]));
		System.out.printf("%f\n", all[9].distanceFrom(all[7]));
	}
}