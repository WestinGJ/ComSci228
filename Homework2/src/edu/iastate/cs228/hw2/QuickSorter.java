package edu.iastate.cs228.hw2;

/**
 * 
 * @author Westin Gjervold
 * 
 *         This class implements the version of the quicksort algorithm
 *         presented in the lecture.
 *
 */
public class QuickSorter extends AbstractSorter {

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts input array of integers
	 */
	public QuickSorter(Point[] pts) {
		super(pts);
		algorithm = "QuickSort";
	}

	/**
	 * Carry out quicksort on the array points[] of the AbstractSorter class.
	 * 
	 */
	@Override
	public void sort() {
		quickSortRec(0, points.length - 1);
	}

	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first starting index of the subarray
	 * @param last  ending index of the subarray
	 */
	private void quickSortRec(int first, int last) {
		if (first >= last) {
			return;
		}
		int partition = partition(first, last);
		quickSortRec(first, partition - 1);
		quickSortRec(partition + 1, last);
	}

	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return first
	 */
	private int partition(int first, int last) {
		Point pivot = points[last];
		for (int i = first; i < last; i++) {
			if (points[i].compareTo(pivot) == -1) {
				swap(first, i);
				first++;
			}
		}
		swap(first, last);
		return first;
	}
}
