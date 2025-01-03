package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;

/**
 * 
 * @author Westin Gjervold
 * 
 *         This class sorts all the points in an array of 2D points to determine
 *         a reference point whose x and y coordinates are respectively the
 *         medians of the x and y coordinates of the original points.
 * 
 *         It records the employed sorting algorithm as well as the sorting time
 *         for comparison.
 *
 */
public class PointScanner {
	private Point[] points;
	private Point medianCoordinatePoint;
	private Algorithm sortingAlgorithm;
	private String outputFileName;
	protected long scanTime;

	/**
	 * This constructor accepts an array of points and one of the four sorting
	 * algorithms as input. Copy the points into the array points[].
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException {
		// Checks if the array is null or has no points
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException();
		}
		points = pts;
		sortingAlgorithm = algo;
	}

	/**
	 * This constructor reads points from a file.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException if the input file contains an odd number of
	 *                                integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException {
		// Sets up file and scanners
		sortingAlgorithm = algo;
		File file = new File(inputFileName);
		outputFileName = inputFileName;
		Scanner scan = new Scanner(file);
		Scanner sc = new Scanner(file);
		// Counts the number or ints in the file
		int numCount = 0;
		int length = 0;
		while (scan.hasNextInt()) {
			scan.nextInt();
			numCount++;
		}
		// Checks if the file has an even number of ints
		if (numCount % 2 != 0) {
			scan.close();
			sc.close();
			throw new InputMismatchException();
		}
		// Scans the file for points and creates a point array
		length = numCount / 2;
		points = new Point[length];
		for (int i = 0; i < length; i++) {
			points[i] = new Point(sc.nextInt(), sc.nextInt());
		}
		scan.close();
		sc.close();
	}

	/**
	 * Carry out two rounds of sorting using the algorithm designated by
	 * sortingAlgorithm as follows:
	 * 
	 * a) Sort points[] by the x-coordinate to get the median x-coordinate. b) Sort
	 * points[] again by the y-coordinate to get the median y-coordinate. c)
	 * Construct medianCoordinatePoint using the obtained median x- and
	 * y-coordinates.
	 * 
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter,
	 * InsertionSorter, MergeSorter, or QuickSorter to carry out sorting.
	 * 
	 * @param algo
	 */
	public void scan() {
		// Creates an abstractSorter
		AbstractSorter aSorter = null;
		if (sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(points);
		} else if (sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(points);
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(points);
		} else if (sortingAlgorithm == Algorithm.QuickSort) {
			aSorter = new QuickSorter(points);
		}
		// Runs sorting algorithm
		aSorter.setComparator(0);
		long xStart = System.nanoTime();
		aSorter.sort();
		long xStop = System.nanoTime();
		Point xMedian = new Point(aSorter.getMedian());
		aSorter.setComparator(1);
		long yStart = System.nanoTime();
		aSorter.sort();
		long yStop = System.nanoTime();
		Point yMedian = new Point(aSorter.getMedian());
		medianCoordinatePoint = new Point(xMedian.getX(), yMedian.getY());
		long xTime = xStop - xStart;
		long yTime = yStop - yStart;
		scanTime = xTime + yTime;
	}

	/**
	 * Outputs performance statistics in the format:
	 * 
	 * <sorting algorithm> <size> <time>
	 * 
	 * For instance,
	 * 
	 * selection sort 1000 9200867
	 * 
	 * @return stats
	 */
	public String stats() {
		String stats = "";
		if (sortingAlgorithm == Algorithm.InsertionSort) {
			stats += "insertion sort   ";
		} else if (sortingAlgorithm == Algorithm.SelectionSort) {
			stats += "selection sort   ";
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			stats += "merge sort       ";
		} else if (sortingAlgorithm == Algorithm.QuickSort) {
			stats += "quick sort       ";
		}
		stats += (points.length + "	  " + scanTime);
		return stats;

	}

	/**
	 * Write MCP after a call to scan(), in the format "MCP: (x, y)" The x and y
	 * coordinates of the point are displayed on the same line with exactly one
	 * blank space in between.
	 * 
	 * @return toString
	 */
	@Override
	public String toString() {
		String toString = "MCP: " + medianCoordinatePoint.toString();
		return toString;
	}

	/**
	 * 
	 * This method, called after scanning, writes point data into a file by
	 * outputFileName. The format of data in the file is the same as printed out
	 * from toString(). The file can help you verify the full correctness of a
	 * sorting result and debug the underlying algorithm.
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException {
		File f = new File(outputFileName);
		PrintWriter pw = new PrintWriter(f);
		pw.write(toString());
		pw.close();
	}

}
