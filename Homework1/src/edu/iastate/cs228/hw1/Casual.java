package edu.iastate.cs228.hw1;

/**
 * 
 * @author Westin Gjervold
 * 
 *         The Casual class is a subclass of TownCell and represents a Casual
 *         cell in a town
 *
 */
public class Casual extends TownCell {

	public Casual(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	@Override
	public State who() {
		return State.CASUAL;
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
		if ((nCensus[EMPTY] + nCensus[OUTAGE]) <= 1) {
			return new Reseller(tNew, row, col);
		} else if (nCensus[RESELLER] != 0) {
			return new Outage(tNew, row, col);
		} else if (nCensus[STREAMER] != 0) {
			return new Streamer(tNew, row, col);
		} else if (nCensus[CASUAL] >= 5) {
			return new Streamer(tNew, row, col);
		} else {
			return new Casual(tNew, row, col);
		}
	}
}
