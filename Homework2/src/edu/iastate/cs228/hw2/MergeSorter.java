package edu.iastate.cs228.hw2;

/**
 * 
 * @author Westin Gjervold
 * 
 *         This class implements the mergesort algorithm.
 *
 */
public class MergeSorter extends AbstractSorter {

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts input array of integers
	 */
	public MergeSorter(Point[] pts) {
		super(pts);
		algorithm = "MergeSort";
	}

	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {
		mergeSortRec(points);
	}

	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of
	 * points. One way is to make copies of the two halves of pts[], recursively
	 * call mergeSort on them, and merge the two sorted subarrays into pts[].
	 * 
	 * @param pts point array
	 */
	private void mergeSortRec(Point[] pts) {
		// Throws the method if the Point array is less than or equal to 1
		if (pts.length <= 1) {
			return;
		}
		// Sets up and fills the left and right partitions
		Point[] leftPartition = new Point[pts.length / 2];
		Point[] rightPartition = new Point[pts.length - (pts.length / 2)];
		for (int i = 0; i < leftPartition.length; i++) {
			leftPartition[i] = pts[i];
		}
		for (int j = 0; j < rightPartition.length; j++) {
			rightPartition[j] = pts[leftPartition.length + j];
		}
		// Calls mergeSortRec for each partition and merges them at the end
		mergeSortRec(leftPartition);
		mergeSortRec(rightPartition);
		merge(pts, leftPartition, rightPartition);
	}

	/**
	 * Merges the left and right partitions to pts array in numerical order
	 * 
	 * @param pts            point array
	 * @param leftPartition  left point array
	 * @param rightPartition right point array
	 */
	private void merge(Point[] pts, Point[] leftPartition, Point[] rightPartition) {
		int firstL = 0;
		int firstR = 0;
		int index = 0;
		while ((firstL < leftPartition.length) && (firstR < rightPartition.length)) {
			if (leftPartition[firstL].compareTo(rightPartition[firstR]) == -1) {
				pts[index] = leftPartition[firstL++];
			} else {
				pts[index] = rightPartition[firstR++];
			}
			index++;
		}
		while (firstL < leftPartition.length) {
			pts[index++] = leftPartition[firstL++];
		}
		while (firstR < rightPartition.length) {
			pts[index++] = rightPartition[firstR++];
		}
	}
}
