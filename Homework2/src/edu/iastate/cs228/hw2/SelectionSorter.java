package edu.iastate.cs228.hw2;

/**
 * 
 * @author Westin Gjervold
 * 
 *         This class implements selection sort.
 *
 */
public class SelectionSorter extends AbstractSorter {

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts
	 */
	public SelectionSorter(Point[] pts) {
		super(pts);
		algorithm = "SelectionSort";
	}

	/**
	 * Apply selection sort on the array points[] of the parent class
	 * AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {
		for (int i = 0; i < points.length - 1; i++) {
			int min = i;
			for (int j = i + 1; j < points.length; j++) {
				if (points[j].compareTo(points[min]) == -1) {
					min = j;
				}
			}
			swap(i, min);
		}
	}
}
