import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LocalSearchTSPSolver extends AbstractTSPSolver {
	
	public LocalSearchTSPSolver(List<Posn> tour) {
		super(tour);
	}

	// The number of temperature 'steps' between 1 and 0 that we will use.
	private static final double kMaxTemperature = 1000;
	
	// The number of permutations to perform before decreasing the temperature
	private static final double kCoolingPeriodLength = 1000;
	
	/**
	 * Find a solution to a TSP instance.
	 */
	public void solve() {
		// Initialize the temperature.
		double T = kMaxTemperature;
		long iterations = 0;
		
		while (T > 0) {
			// Randomly choose a local search operator (operators are identified by integers in
			// the range [1, 3], and use it to permute cities. See @permute for documentation
			// of the local search operators.
			List<Posn> permutedTour = permute(tour, randRange(1, 3));
			
			double permutedTourLength = getTourLength(permutedTour);
			double tourLength = getTourLength(tour);
			
			// Calculate the probability P that we will accept the new tour.
			double P = 0;
			if (permutedTourLength < tourLength) {
				// Always accept a better tour.
				P = 1;
			} else {
				// Accept an inferior tour according to the Boltzmann distribution.
				P = Math.exp((tourLength - permutedTourLength) / (T / kMaxTemperature));
			}
			
			// Accept the new tour with probability P.
			tour = (Math.random() < P) ? permutedTour : tour;
			
			// If we have finished a cooling period, decrease the temperature again.
			if ((++iterations % kCoolingPeriodLength) == 0) {
				T--;
			}
		}
		tourLength = getTourLength(tour);
	}

	/**
	 * Calculate a random integer in the given range.
	 */
	public static int randRange(int Min, int Max) {
		return Min + (int)(Math.random() * ((Max - Min) + 1));
	}
	
	/**
	 * Calculate the length of a tour. Assume that the start/end city is
	 * only provided once.
	 */
	private static double getTourLength(List<Posn> tour) {
		double length = 0;
		Posn last = tour.get(0);
		for (Posn city : tour) {
			length += city.distanceFrom(last);
			last = city;
		}
		length += last.distanceFrom(tour.get(0));
		return length;
	}
	
	/**
	 * Permute a tour. Possible permutations are:
	 *  1) Cut a random number of sequential nodes out of a random location in the tour. Put
	 *     them back in at a random index.
	 *  2) Reverse a random number of sequential nodes in the tour.
	 *  3) Do (1) and (2) (randomly move and reverse a set of nodes from the tour).
	 *  
	 *  The 'mode' specifies which operations will occur. Mode is in the range [1, 3]. The least
	 *  significant bit in mode indicates whether permutation (1) will occur, and the most
	 *  significant bit indicates whether permutation (2) will occur. So permutation (3) literally
	 *  means 'do permutations (1) and (2)'.
	 */
	private static List<Posn> permute(List<Posn> input, int mode) {
		// Should we move some nodes?
		boolean move = (mode & 1) == 1;
		// Should we reverse some nodes?
		boolean reverse = (mode & 2) == 1;
		
		// Extract a random number of sequential nodes from a random location in the tour
		int size = randRange(0, input.size() - 1);
		int offset = randRange(0, input.size() - size);
		List<Posn> extracted = input.subList(offset, offset + size);
		
		// Get the nodes that remain in the tour.
		List<Posn> retained = new ArrayList<Posn>(input);
		retained.removeAll(extracted);
		
		// Reverse the extracted nodes, if appropriate.
		if (reverse) {
			Collections.reverse(extracted);
		}
		
		// Put the extracted nodes back in (at a random index, if appropriate).
		int insertIndex = move ? randRange(0, retained.size()) : offset;
		
		retained.addAll(insertIndex, extracted);
		return retained;
	}
}