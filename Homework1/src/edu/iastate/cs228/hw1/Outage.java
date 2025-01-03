package edu.iastate.cs228.hw1;

/**
 * 
 * @author Westin Gjervold
 * 
 *         The Outage class is a subclass of TownCell and represents a Outage
 *         cell in a town
 *
 */
public class Outage extends TownCell {

	public Outage(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	@Override
	public State who() {
		return State.OUTAGE;
	}

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	@Override
	public TownCell next(Town tNew) {
		census(nCensus);
		if (nCensus[CASUAL] >= 5) {
			return new Streamer(tNew, row, col);
		} else {
			return new Empty(tNew, row, col);
		}
	}
}
