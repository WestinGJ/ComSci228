package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

/**
 * 
 * @author Westin Gjervold
 * 
 *         This class executes four sorting algorithms: selection sort,
 *         insertion sort, mergesort, and quicksort, over randomly generated
 *         integers as well integers from a file input. It compares the
 *         execution times of these algorithms on the same input.
 *
 */

public class CompareSorters {
	/**
	 * Repeatedly take integer sequences either randomly generated or read from
	 * files. Use them as coordinates to construct points. Scan these points with
	 * respect to their median coordinate point four times, each time using a
	 * different sorting algorithm.
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException {
		PointScanner[] scanners = new PointScanner[4];
		int trials = 0;
		int input = 0;
		Scanner scan = new Scanner(System.in);
		Scanner sc = new Scanner(System.in);
		// Asks the user if they want to use random ints, scan from file, or exit
		System.out.println("Performances of Four Sorting Algorithms in Point Scanning");
		System.out.println();
		System.out.println("keys: 1 (random integers) 2 (file input) 3 (exit)");
		while (input != 3) {
			trials++;
			System.out.print("Trial " + trials + ": ");
			input = scan.nextInt();
			// If asked to scan random points, call generateRandomPoints and sets up sorters
			if (input == 1) {
				System.out.print("Enter number of random points: ");
				int numPts = scan.nextInt();
				Point[] randPoints = generateRandomPoints(numPts, new Random());
				scanners[0] = new PointScanner(randPoints, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(randPoints, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(randPoints, Algorithm.MergeSort);
				scanners[3] = new PointScanner(randPoints, Algorithm.QuickSort);
			}
			// If asked to scan file, scans file and sets up sorters
			else if (input == 2) {
				System.out.print("File name: ");
				String fileName = sc.nextLine();
				scanners[0] = new PointScanner(fileName, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(fileName, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(fileName, Algorithm.MergeSort);
				scanners[3] = new PointScanner(fileName, Algorithm.QuickSort);
			}
			// If asked to exit, exit
			else if (input == 3) {
				scan.close();
				sc.close();
				break;
			}
			// If Invalid Input
			else {
				System.out.println("Error Invalid Input");
				continue;
			}
			// Sorts all four sorters
			for (int i = 0; i < scanners.length; i++) {
				scanners[i].scan();
			}
			// Print output
			System.out.println();
			System.out.println("algorithm        size     time (ns)");
			System.out.println("----------------------------------");
			for (int i = 0; i < scanners.length; i++) {
				System.out.println(scanners[i].stats());
			}
			System.out.println("----------------------------------");
			System.out.println();
		}
	}

	/**
	 * This method generates a given number of random points. The coordinates of
	 * these points are pseudo-random numbers within the range [-50,50] � [-50,50].
	 * Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing.
	 * 
	 * @param numPts number of points
	 * @param rand   Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException {
		// Throws IllegalArgumentException if numPts < 1
		if (numPts < 1) {
			throw new IllegalArgumentException();
		}
		// Creates Point array with random ints
		Point[] points = new Point[numPts];
		for (int i = 0; i < points.length; i++) {
			int x = rand.nextInt(101) - 50;
			int y = rand.nextInt(101) - 50;
			points[i] = new Point(x, y);
		}
		return points;
	}
}
