package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Westin Gjervold
 * 
 *         Tests the Streamer class
 * 
 */
class StreamerTest {
	// Tests if there are no EMPTY or OUTAGE neighbors
	@Test
	void test1() {
		Town t = new Town(3, 3);
		t.grid[0][0] = new Casual(t, 0, 0);
		t.grid[0][1] = new Casual(t, 0, 1);
		t.grid[0][2] = new Casual(t, 0, 2);
		t.grid[1][0] = new Casual(t, 1, 0);
		t.grid[1][1] = new Streamer(t, 1, 1);
		t.grid[1][2] = new Casual(t, 1, 2);
		t.grid[2][0] = new Casual(t, 2, 0);
		t.grid[2][1] = new Casual(t, 2, 1);
		t.grid[2][2] = new Casual(t, 2, 2);
		assertEquals(State.RESELLER, t.grid[1][1].next(t).who());
	}

	// Tests if there are any RESELLER neighbors
	@Test
	void test2() {
		Town t = new Town(3, 3);
		t.grid[0][0] = new Casual(t, 0, 0);
		t.grid[0][1] = new Empty(t, 0, 1);
		t.grid[0][2] = new Empty(t, 0, 2);
		t.grid[1][0] = new Reseller(t, 1, 0);
		t.grid[1][1] = new Streamer(t, 1, 1);
		t.grid[1][2] = new Casual(t, 1, 2);
		t.grid[2][0] = new Casual(t, 2, 0);
		t.grid[2][1] = new Casual(t, 2, 1);
		t.grid[2][2] = new Casual(t, 2, 2);
		assertEquals(State.OUTAGE, t.grid[1][1].next(t).who());
	}

	// Tests if there are any OUTAGE neighbors
	@Test
	void test3() {
		Town t = new Town(3, 3);
		t.grid[0][0] = new Casual(t, 0, 0);
		t.grid[0][1] = new Empty(t, 0, 1);
		t.grid[0][2] = new Empty(t, 0, 2);
		t.grid[1][0] = new Outage(t, 1, 0);
		t.grid[1][1] = new Streamer(t, 1, 1);
		t.grid[1][2] = new Casual(t, 1, 2);
		t.grid[2][0] = new Casual(t, 2, 0);
		t.grid[2][1] = new Casual(t, 2, 1);
		t.grid[2][2] = new Casual(t, 2, 2);
		assertEquals(State.EMPTY, t.grid[1][1].next(t).who());
	}

	// Tests if none of the conditions are met
	@Test
	void test4() {
		Town t = new Town(3, 3);
		t.grid[0][0] = new Casual(t, 0, 0);
		t.grid[0][1] = new Casual(t, 0, 1);
		t.grid[0][2] = new Empty(t, 0, 2);
		t.grid[1][0] = new Empty(t, 1, 0);
		t.grid[1][1] = new Streamer(t, 1, 1);
		t.grid[1][2] = new Casual(t, 1, 2);
		t.grid[2][0] = new Casual(t, 2, 0);
		t.grid[2][1] = new Casual(t, 2, 1);
		t.grid[2][2] = new Casual(t, 2, 2);
		assertEquals(State.STREAMER, t.grid[1][1].next(t).who());
	}

	// Tests if the who() function works
	@Test
	void test5() {
		Town t = new Town(1, 1);
		t.grid[0][0] = new Streamer(t, 0, 0);
		assertEquals(State.STREAMER, t.grid[0][0].who());
	}
}
