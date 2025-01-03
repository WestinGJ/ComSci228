package edu.iastate.cs228.hw1;

/**
 * 
 * @author Westin Gjervold
 * 
 *         The Empty class is a subclass of TownCell and represents a Empty cell
 *         in a town
 *
 */
public class Empty extends TownCell {

	public Empty(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	@Override
	public State who() {
		return State.EMPTY;
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
		} else {
			return new Casual(tNew, row, col);
		}
	}
}
