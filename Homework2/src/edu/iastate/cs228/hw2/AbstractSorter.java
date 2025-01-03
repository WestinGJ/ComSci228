package edu.iastate.cs228.hw2;

import java.util.Comparator;
import java.lang.IllegalArgumentException;

/**
 * 
 * @author Westin Gjervold
 * 
 *         This abstract class is extended by SelectionSort, InsertionSort,
 *         MergeSort, and QuickSort. It stores the input (later the sorted)
 *         sequence.
 *
 */
public abstract class AbstractSorter {
	protected Point[] points;
	protected String algorithm = null;
	protected Comparator<Point> pointComparator = null;

	/**
	 * This constructor accepts an array of points as input. Copy the points into
	 * the array points[].
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	protected AbstractSorter(Point[] pts) throws IllegalArgumentException {
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException();
		}
		points = pts;
	}

	/**
	 * Generates a comparator on the fly that compares by x-coordinate if order ==
	 * 0, by y-coordinate if order == 1. Assign the comparator to the variable
	 * pointComparator.
	 * 
	 * 
	 * @param order 0 by x-coordinate 1 by y-coordinate
	 * 
	 * 
	 * @throws IllegalArgumentException if order is less than 0 or greater than 1
	 * 
	 */
	public void setComparator(int order) throws IllegalArgumentException {
		if (order == 0) {
			Point.setXorY(true);
			;
		} else if (order == 1) {
			Point.setXorY(false);
		} else {
			throw new IllegalArgumentException();
		}
	}

	protected abstract void sort();

	/**
	 * Obtain the point in the array points[] that has median index
	 * 
	 * @return median point
	 */
	public Point getMedian() {
		return points[points.length / 2];
	}

	/**
	 * Copys the array pts[] onto the array points[].
	 * 
	 * @param pts
	 */
	public void getPoints(Point[] pts) {
		for (int i = 0; i < pts.length; i++) {
			this.points[i] = pts[i];
		}
	}

	/**
	 * Swaps the two elements indexed at i and j respectively in the array points[].
	 * 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j) {
		Point temp;
		temp = points[i];
		points[i] = points[j];
		points[j] = temp;
	}
}
