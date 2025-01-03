package edu.iastate.cs228.hw1;

/**
 * 
 * @author Westin Gjervold
 *	
 * The TownCell class represents a generic cell in a town
 *
 */
public abstract class TownCell {

	protected Town plain;
	protected int row;
	protected int col;
	
	
	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	public static final int NUM_CELL_TYPE = 5;
	
	//Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}
	
	/**
	 * Checks all neigborhood cell types in the neighborhood.
	 * Refer to homework pdf for neighbor definitions (all adjacent
	 * neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 *  
	 * @param counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0; 
		nCensus[EMPTY] = 0; 
		nCensus[CASUAL] = 0; 
		nCensus[OUTAGE] = 0; 
		nCensus[STREAMER] = 0; 

		//Determines the size of the neighborhood
		int upperBound = Math.max(0, row - 1);
		int lowerBound = Math.min(plain.grid.length - 1, row + 1);
		int leftBound = Math.max(0, col - 1);
		int rightBound = Math.min(plain.grid.length - 1, col + 1);
		
		//Reads through the neighborhood and records the neighbors state
		for (int r = upperBound; r <= lowerBound; r++) {
			for (int c = leftBound; c <= rightBound; c++) {
				if (r == row && c == col) {
					continue;
				} 
				else if (plain.grid[r][c].who() == State.RESELLER) {
					nCensus[RESELLER]++;
				} 
				else if (plain.grid[r][c].who() == State.EMPTY) {
					nCensus[EMPTY]++;
				}
				else if (plain.grid[r][c].who() == State.CASUAL) {
					nCensus[CASUAL]++;
				} 
				else if (plain.grid[r][c].who() == State.OUTAGE) {
					nCensus[OUTAGE]++;
				} 
				else if (plain.grid[r][c].who() == State.STREAMER) {
					nCensus[STREAMER]++;
				}
			}
		}

	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);
}
