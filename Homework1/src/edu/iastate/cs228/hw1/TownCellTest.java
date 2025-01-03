package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Westin Gjervold
 * 
 * Tests the TownCell class
 * 
 */
class TownCellTest {
	// Tests the census function
		@Test
		void test1() {
			Town t = new Town(3, 3);
			t.grid[0][0] = new Casual(t, 0, 0);
			t.grid[0][1] = new Casual(t, 0, 1);
			t.grid[0][2] = new Streamer(t, 0, 2);
			t.grid[1][0] = new Outage(t, 1, 0);
			t.grid[1][1] = new Casual(t, 1, 1);
			t.grid[1][2] = new Empty(t, 1, 2);
			t.grid[2][0] = new Empty(t, 2, 0);
			t.grid[2][1] = new Reseller(t, 2, 1);
			t.grid[2][2] = new Reseller(t, 2, 2);
			int[] censusTest = new int[5];
			t.grid[1][1].census(censusTest);
			assertEquals(censusTest[0], 2);
			assertEquals(censusTest[1], 2);
			assertEquals(censusTest[2], 2);
			assertEquals(censusTest[3], 1);
			assertEquals(censusTest[4], 1);
		}
}
