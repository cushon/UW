import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
	
	public static TSPState findRoute(TSPState start) {
		PriorityQueue<TSPState> pq = new PriorityQueue<TSPState>();
		pq.add(start);
		
		while (true) {
			
			TSPState current = pq.remove();
			
			if (current.getUntravelledCities().isEmpty()) {
				if (current.getTravelledCities().getFirst().getName().compareTo( 
						current.getTravelledCities().getLast().getName()) == 0) {
					return current;
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
	}
	
	public static void main(String[] args) throws IOException {
		
		FileReader fileReader = new FileReader("/Users/cushon/Documents/cs486-workspace/A*-TSP/problem/problem10-1");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		int nodes = Integer.parseInt(bufferedReader.readLine());
		
		final long startTime = System.nanoTime();
		
		List<Posn> nodeList = new ArrayList<Posn>();
		String inputLine = null;
		while ((inputLine = bufferedReader.readLine()) != null) {
			nodeList.add(Posn.parsePosn(inputLine));
		}
		assert(nodeList.size() == nodes);
		
		Deque<Posn> travelled = new LinkedList<Posn>(nodeList.subList(0, 1));
		List<Posn> untravelled = new ArrayList<Posn>(nodeList.subList(1, nodeList.size()));
		
		TSPState goal = findRoute(new TSPState(0, 0, travelled, untravelled));
		
		System.out.printf("A* Runtime: %f\n", (System.nanoTime() - startTime) / 1000000000.0);
		
		System.out.printf("Goal %f\n", goal.getDistance());
		
		for (Posn city : goal.getTravelledCities()) {
			System.out.printf("%s ", city.getName());
		}
		System.out.println();
		
		for (Posn city : goal.getTravelledCities()) {
			System.out.printf("%d ", city.getName().charAt(0) - 'A');
		}
		System.out.println();
	}
}