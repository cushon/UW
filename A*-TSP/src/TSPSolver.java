import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;

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
		
		final long bfstart = System.nanoTime();
		
		String[] order = new String[10];
		order[0] = "A";
		
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
		/*
		double minDistance = Double.MAX_VALUE;
		
		for (int i = 1; i <= 9; ++i) {
			for (int j = 1; j <= 9; ++j) {
				for (int k = 1; k <= 9; ++k) {
					for (int l = 1; l <= 9; ++l) {
						for (int m = 1; m <= 9; ++m) {
							for (int n = 1; n <= 9; ++n) {
								for (int o = 1; o <= 9; ++o) {
									for (int p = 1; p <= 9; ++p) {
										for (int q = 1; q <= 9; ++q) {
											
											Set<Integer> uniques = new HashSet<Integer>();
											uniques.add(i);
											uniques.add(j);
											uniques.add(k);
											uniques.add(l);
											uniques.add(m);
											uniques.add(n);
											uniques.add(o);
											uniques.add(p);
											uniques.add(q);
											if (uniques.size() != 9) {
												continue;
											}
												
											double distance = 0;
											distance += all[0].distanceFrom(all[i]);
											distance += all[i].distanceFrom(all[j]);
											distance += all[j].distanceFrom(all[k]);
											distance += all[k].distanceFrom(all[l]);
											distance += all[l].distanceFrom(all[m]);
											distance += all[m].distanceFrom(all[n]);
											distance += all[n].distanceFrom(all[o]);
											distance += all[o].distanceFrom(all[p]);
											distance += all[p].distanceFrom(all[q]);
											
											if (distance < minDistance) {
												minDistance = distance;
												order[1] = all[i].getName();
												order[2] = all[j].getName();
												order[3] = all[k].getName();
												order[4] = all[l].getName();
												order[5] = all[m].getName();
												order[6] = all[n].getName();
												order[7] = all[o].getName();
												order[8] = all[p].getName();
												order[9] = all[q].getName();
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		System.out.println(minDistance);
		System.out.printf("%s %s %s %s %s\n",
				order[0],
				order[1],
				order[2],
				order[3],
				order[4],
				order[5],
				order[6],
				order[7],
				order[8],
				order[9]);
		
		System.out.printf("Brute force: %d\n", System.nanoTime() - bfstart);
		*/
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
			/*
			for (TSPState state : pq) {
				System.out.printf("[%s, %f, %f]\n",
						state.getTravelledCities().getLast().getName(),
						state.getPriority(),
						state.getDistance());
			}
			System.out.println("\n");
			*/
			
			TSPState current = pq.remove();
			if (current.getUntravelledCities().isEmpty()) {
				goal = current;
				break;
			}
			
			System.out.println("Considering: ");
			for (Posn t : current.getTravelledCities()) {
				System.out.printf("%s, ", t.getName());
			}
			System.out.println();
			System.out.printf("Distance so far: %f\n\n", current.getDistance());
			
			
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
		System.out.printf("A*: 234052000\n");
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
		
	
		
		double alt = 0;
		int[] path = {0, 7, 1, 5, 4, 8, 2, 3, 6, 9};
		//int[] path = {0, 1, 5, 4, 8, 2, 3, 6, 9, 7};
		for (int i = 0; i < path.length - 1; ++i) {
			alt += all[i].distanceFrom(all[i+1]);
		}
		System.out.println(alt);
		
		System.out.printf("%f\n", all[0].distanceFrom(all[7]));
		System.out.printf("%f\n", all[7].distanceFrom(all[1]));
		
		System.out.printf("%f\n", all[0].distanceFrom(all[1]));
		System.out.printf("%f\n", all[9].distanceFrom(all[7]));
	}
}