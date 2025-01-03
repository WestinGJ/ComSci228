package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Westin Gjervold
 *
 *         The ISPBusiness class performs simulation over a grid plain with
 *         cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {

	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * 
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		// TODO: Write your code here.
		for (int row = 0; row < tOld.getLength(); row++) {
			for (int col = 0; col < tOld.getWidth(); col++) {
				tNew.grid[row][col] = tOld.grid[row][col].next(tNew);
			}
		}
		return tNew;
	}

	/**
	 * Returns the profit for the current state in the town grid.
	 * 
	 * @param town
	 * @return
	 */
	public static int getProfit(Town town) {
		int profit = 0;
		for (int row = 0; row < town.getLength(); row++) {
			for (int col = 0; col < town.getWidth(); col++) {
				if (town.grid[row][col].who().equals(State.CASUAL)) {
					profit++;
				}
			}
		}
		return profit;
	}

	/**
	 * Main method. Interact with the user and ask if user wants to specify elements
	 * of grid via an input file (option: 1) or wants to generate it randomly
	 * (option: 2).
	 * 
	 * Depending on the user choice, create the Town object using respective
	 * constructor and if user choice is to populate it randomly, then populate the
	 * grid here.
	 * 
	 * Finally: For 12 billing cycle calculate the profit and update town object
	 * (for each cycle). Print the final profit in terms of %. You should print the
	 * profit percentage with two digits after the decimal point: Example if profit
	 * is 35.5600004, your output should be:
	 *
	 * 35.56%
	 * 
	 * Note that this method does not throw any exception, so you need to handle all
	 * the exceptions in it.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * 
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Town town;
		int profit;
		double totalProfit = 0;
		System.out.println("How to populate grid (type 1 or 2): 1: from a file. 2: randomly with seed");
		Scanner s = new Scanner(System.in);
		int randomOrFile = s.nextInt();
		// File Grid Option
		if (randomOrFile == 1) {
			System.out.println("Please enter file path:");
			String filePath = s.next();
			s.close();
			try {
				town = new Town(filePath);
			} catch (FileNotFoundException e) {
				System.out.println("Invalid file path");
				e.printStackTrace();
				return;
			}
		}
		// Random Grid Option
		else if (randomOrFile == 2) {
			System.out.println("Provide rows, cols and seed integer separated by spaces:");
			int r = s.nextInt();
			int c = s.nextInt();
			int seed = s.nextInt();
			town = new Town(r, c);
			town.randomInit(seed);
		} else {
			System.out.println("Invalid option");
			return;
		}
		// Simulate Billing Cycle
		for (int itr = 0; itr < 12; itr++) {
			town = updatePlain(town);
			profit = getProfit(town);
			totalProfit += profit;
		}
		double potentialProfit = town.getLength() * town.getWidth() * 12.0;
		double profitPercentage = (totalProfit / potentialProfit) * 100.0;
		System.out.printf("%.2f", profitPercentage);
		System.out.print("%");
	}
}
