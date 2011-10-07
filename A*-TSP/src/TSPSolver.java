import java.io.BufferedReader;
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
	
	public static AStarNode findRoute(AStarNode start) {
		PriorityQueue<AStarNode> pq = new PriorityQueue<AStarNode>();
		pq.add(start);
		
		
		
		while (true) {
			
			AStarNode current = pq.remove();
			
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
				
				pq.add(new AStarNode(newPriority, newDistance, nowTravelled, stillUntravelled));
			}
		}
	}
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		
		FileReader fileReader = new FileReader(String.format("/Users/cushon/Documents/cs486-workspace/A*-TSP/problem/problem26"));
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		int nodes = Integer.parseInt(bufferedReader.readLine());
		
		final long startTime = System.nanoTime();
		
		List<Posn> nodeList = new ArrayList<Posn>();
		String inputLine = null;
		while ((inputLine = bufferedReader.readLine()) != null) {
			nodeList.add(Posn.parsePosn(inputLine));
		}
		assert(nodeList.size() == nodes);
		
		LocalSearchTSPSolver lstsp = new LocalSearchTSPSolver(nodeList);
		lstsp.solve();
		List<Posn> tour = lstsp.getTour();
		
		System.out.println(lstsp.getTourLength());
		
		return;
		/*
		int[] sums = new int[10];
		int[] tots = new int[10];
		long[] time = new long[10];
		
		for (int n = 1; n <= 10; ++n) {
			long t = System.nanoTime();
			for (int i = 1; i <= 5; ++i) {
				if (n == 1 && i == 1) continue;
				AStarNode.instantions = 0;
				//System.out.printf("%d-%d\n", n, i);
				FileReader fileReader = new FileReader(String.format("/Users/cushon/Documents/cs486-workspace/A*-TSP/problem/problem%d-%d", n, i));
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				
				int nodes = Integer.parseInt(bufferedReader.readLine());
				
				final long startTime = System.nanoTime();
				
				List<Posn> nodeList = new ArrayList<Posn>();
				String inputLine = null;
				while ((inputLine = bufferedReader.readLine()) != null) {
					nodeList.add(Posn.parsePosn(inputLine));
				}
				assert(nodeList.size() == nodes);
				
//				List<Double> results = new ArrayList<Double>();
//				NELState goal = anneal(new NELState(nodeList), results);
//				
				
				Deque<Posn> travelled = new LinkedList<Posn>(nodeList.subList(0, 1));
				List<Posn> untravelled = new ArrayList<Posn>(nodeList.subList(1, nodeList.size()));
				AStarNode goal = findRoute(new AStarNode(0, 0, travelled, untravelled));
				
//				for (Posn city : goal.getTravelledCities()) {
//					System.out.printf("%d ", city.getName().charAt(0) - 'A');
//				}
				//System.out.println();
				
				//System.out.println(goal.getDistance());
				//System.out.printf("%d\n", TSPState.instantions);
				System.out.printf("%d %d\n", AStarNode.instantions, n);
				sums[n-1] += AStarNode.instantions;
				tots[n-1]++;
				
//				System.out.printf("%f\n", goal.getFitness());
//				
//				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/Users/cushon/Desktop/out.csv"));
//				for (Double fitness : results) {
//					bufferedWriter.write(String.format("%f, ", fitness));
//				}
				
			}
			time[n-1] = System.nanoTime() - t;
		}
		
		for (int i = 1; i <= 10; ++i) {
			 
			//System.out.println(sums[i-1] / tots[i-1]);
			System.out.println(time[i-1] / tots[i-1]);
		}
		*/
		/*
		
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
		
		goal.getTravelledCities().removeLast();
	*/
	}
}