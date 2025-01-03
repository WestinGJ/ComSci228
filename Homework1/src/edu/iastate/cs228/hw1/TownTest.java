package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

/**
 * @author Westin Gjervold
 * 
 * Tests the Town class
 * 
 */

class TownTest {
	// Tests the getLength() function
	@Test
	void test1() {
		Town t = new Town(4, 3);
		assertEquals(t.getLength(), 4);
	}

	// Tests the getWidth() function
	@Test
	void test2() {
		Town t = new Town(4, 3);
		assertEquals(t.getWidth(), 3);
	}
	// Tests the toString() function
	@Test
	void test3() {
		Town t = new Town(3, 3);
		t.grid[0][0] = new Casual(t, 0, 0);
		t.grid[0][1] = new Casual(t, 0, 1);
		t.grid[0][2] = new Outage(t, 0, 2);
		t.grid[1][0] = new Empty(t, 1, 0);
		t.grid[1][1] = new Casual(t, 1, 1);
		t.grid[1][2] = new Empty(t, 1, 2);
		t.grid[2][0] = new Empty(t, 2, 0);
		t.grid[2][1] = new Streamer(t, 2, 1);
		t.grid[2][2] = new Casual(t, 2, 2);
		String townString = "C C O \nE C E \nE S C \n";
		assertEquals(townString, t.toString());
	}
	// Tests the file input constructor
	@Test
	void test4() throws FileNotFoundException {
		Town t = new Town("C:\\ComSci228\\Homework1\\src\\edu\\iastate\\cs228\\hw1\\ISP4x4");
		String testTown = "O R O R \nE E C O \nE S O S \nE O R R \n";
		assertEquals(testTown, t.toString());
	}
	// Tests the randomInit function
	@Test
	void test5() {
		Town t = new Town(3,3);
		t.randomInit(4);
		String testTown = "O O R \nR O C \nE E O \n";
		assertEquals(testTown, t.toString());
	}
}
