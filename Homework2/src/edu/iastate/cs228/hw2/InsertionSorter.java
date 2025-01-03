package edu.iastate.cs228.hw2;

/**
 * 
 * @author Westin Gjervold
 * 
 *         This class implements insertion sort.
 *
 */
public class InsertionSorter extends AbstractSorter {

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts
	 */
	public InsertionSorter(Point[] pts) {
		super(pts);
		algorithm = "InsertionSort";
	}

	/**
	 * Perform insertion sort on the array points[] of the parent class
	 * AbstractSorter.
	 */
	@Override
	public void sort() {
		for (int i = 1; i < points.length; ++i) {
			int j = i;
			while (j > 0 && (points[j].compareTo(points[j - 1]) == -1)) {
				swap(j, j - 1);
				--j;
			}
		}
	}
}
