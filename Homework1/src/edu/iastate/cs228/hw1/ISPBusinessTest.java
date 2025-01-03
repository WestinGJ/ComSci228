package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Westin Gjervold
 * 
 *         Tests the ISPBusiness class
 * 
 */

class ISPBusinessTest {
	// Tests the updatePlain function
	@Test
	void test1() {
		Town t = new Town(4, 4);
		t.grid[0][0] = new Empty(t, 0, 0);
		t.grid[0][1] = new Empty(t, 0, 1);
		t.grid[0][2] = new Empty(t, 0, 2);
		t.grid[0][3] = new Empty(t, 0, 3);
		t.grid[1][0] = new Casual(t, 1, 0);
		t.grid[1][1] = new Casual(t, 1, 1);
		t.grid[1][2] = new Outage(t, 1, 2);
		t.grid[1][3] = new Empty(t, 1, 3);
		t.grid[2][0] = new Casual(t, 2, 0);
		t.grid[2][1] = new Outage(t, 2, 1);
		t.grid[2][2] = new Empty(t, 2, 2);
		t.grid[2][3] = new Outage(t, 2, 3);
		t.grid[3][0] = new Casual(t, 3, 0);
		t.grid[3][1] = new Empty(t, 3, 1);
		t.grid[3][2] = new Empty(t, 3, 2);
		t.grid[3][3] = new Empty(t, 3, 3);
		t = ISPBusiness.updatePlain(t);
		String townString = "R C C C \nC C E C \nC E C E \nC C C C \n";
		assertEquals(townString, t.toString());
	}

	// Tests the getProfit function
	@Test
	void test2() {
		Town t = new Town(4, 4);
		t.grid[0][0] = new Empty(t, 0, 0);
		t.grid[0][1] = new Empty(t, 0, 1);
		t.grid[0][2] = new Empty(t, 0, 2);
		t.grid[0][3] = new Empty(t, 0, 3);
		t.grid[1][0] = new Casual(t, 1, 0);
		t.grid[1][1] = new Casual(t, 1, 1);
		t.grid[1][2] = new Outage(t, 1, 2);
		t.grid[1][3] = new Empty(t, 1, 3);
		t.grid[2][0] = new Casual(t, 2, 0);
		t.grid[2][1] = new Outage(t, 2, 1);
		t.grid[2][2] = new Empty(t, 2, 2);
		t.grid[2][3] = new Outage(t, 2, 3);
		t.grid[3][0] = new Casual(t, 3, 0);
		t.grid[3][1] = new Empty(t, 3, 1);
		t.grid[3][2] = new Empty(t, 3, 2);
		t.grid[3][3] = new Empty(t, 3, 3);
		int testProfit = ISPBusiness.getProfit(t);
		assertEquals(testProfit, 4);
	}
}
