package edu.iastate.cs228.hw2;

/**
 * This class simulates a coordinate point
 * 
 * @author Westin Gjervold
 *
 */
public class Point implements Comparable<Point> {
	private int x;
	private int y;
	public static boolean xORy;

	public Point() {
		x = 0;
		y = 0;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point p) {
		x = p.getX();
		y = p.getY();
	}

	/**
	 * Returns the value of x.
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the value of y.
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set the value of the static instance variable xORy.
	 * 
	 * @param xORy
	 */
	public static void setXorY(boolean xORy) {
		Point.xORy = xORy;
	}

	/**
	 * Checks if two points are equal
	 * 
	 * @param obj
	 * @return true if points are equal false if points are not equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}

	/**
	 * Compare this point with a second point q depending on the value of the static
	 * variable xORy
	 * 
	 * @param q
	 * @return -1 if (xORy == true && (this.x < q.x || (this.x == q.x && this.y <
	 *         q.y))) || (xORy == false && (this.y < q.y || (this.y == q.y && this.x
	 *         < q.x))) 0 if this.x == q.x && this.y == q.y) 1 otherwise
	 */
	public int compareTo(Point q) {
		if ((xORy == true) && (this.x < q.x)) {
			return -1;
		} else if ((xORy == true) && (this.x == q.x) && (this.y < q.y)) {
			return -1;
		} else if ((xORy == false) && (this.y < q.y)) {
			return -1;
		} else if ((xORy == false) && (this.y == q.y) && (this.x < q.x)) {
			return -1;
		} else if ((this.x == q.x) && (this.y == q.y)) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Output a point in the standard form (x, y).
	 * 
	 * @return toString
	 */
	@Override
	public String toString() {
		String toString = "(" + this.x + ", " + this.y + ")";
		return toString;
	}
}
