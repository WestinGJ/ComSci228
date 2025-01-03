package edu.iastate.cs228.hw1;

/**
 * 
 * @author Westin Gjervold
 * 
 *         The Reseller class is a subclass of TownCell and represents a
 *         Reseller cell in a town
 *
 */
public class Reseller extends TownCell {

	public Reseller(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	@Override
	public State who() {
		return State.RESELLER;
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
		if (nCensus[CASUAL] <= 3) {
			return new Empty(tNew, row, col);
		} else if (nCensus[EMPTY] >= 3) {
			return new Empty(tNew, row, col);
		} else if (nCensus[CASUAL] >= 5) {
			return new Streamer(tNew, row, col);
		} else {
			return new Reseller(tNew, row, col);
		}
	}
}
