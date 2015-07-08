/*************************************************************************
 * Name: a2wd.com
 * Email: github@a2wd.com
 *
 * Compilation:  javac Point.java
 * Execution:	 java Point
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER;       // TODO

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
				SLOPE_ORDER = new slopeOrder();
    }

		//Comparator class
		private static class slopeOrder implements Comparator<Point> {
			public int compare(Point p, Point q) {
				return 0;
			}
		}

    // plot this point to standard drawing
    public void draw() {
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
			double dy = that.y - this.y;
			double dx = that.x - this.x;
			if(dy == 0 && dx == 0)
				return Double.NEGATIVE_INFINITY;
			else if(dy == 0)
				return Double.POSITIVE_INFINITY;
			else if(dx == 0)
				return 0.0;
			else
				return (dy/dx);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
			if      (this.y < that.y ||
							(this.y == that.y && this.x < that.x)) return -1;
			else if (this.y > that.y ||
							(this.y == that.y && this.x > that.x)) return  1;
			else 																					 return  0;
		}

    // return string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
    }
}
