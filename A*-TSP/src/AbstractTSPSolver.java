import java.util.List;

public abstract class AbstractTSPSolver {
	protected List<Posn> tour;
	protected double tourLength;
	
	public AbstractTSPSolver(List<Posn> tour) {
		this.tour = tour;
	}
	
	public abstract void solve();
	
	public List<Posn> getTour() { return tour; }
	public double getTourLength() { return tourLength; }
}
