/*************************************************************************
 * Name: a2wd.com
 * Email: github@a2wd.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
       public int compare(Point p, Point q) {
        double slopeToP = slopeTo(p);
        double slopeToQ = slopeTo(q);
        
        if (slopeToP < slopeToQ)
          return -1;
        else if (slopeToP > slopeToQ)
          return 1;
        
        return 0; // slopeToP == slopeToQ
      }
   };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
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
      double dy = that.y - y;
      double dx = that.x - x;
      if (dy == 0 && dx == 0)
        return Double.NEGATIVE_INFINITY;
      else if (dy == 0)
        return 0.0;
      else if (dx == 0)
        return Double.POSITIVE_INFINITY;
      else
        return (dy/dx);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
      if      (this.y < that.y 
          ||  (this.y == that.y && this.x < that.x)) return -1;
      else if (this.y > that.y
          ||  (this.y == that.y && this.x > that.x)) return  1;
      else                                           return  0;
    }

    // return string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
      StdOut.println("Compare p(10,0) to (0,0)");
      Point p = new Point(10, 0);
      Point q = new Point(0, 0);
      StdOut.println("p.slopeTo(q) = " + p.slopeTo(q));

      Point a = new Point(67, 410);
      Point b = new Point(23, 299);
      Point c = new Point(13, 262);

      StdOut.println("Comparing:");
      StdOut.println("a, " + a.toString());
      StdOut.println("b, " + b.toString());
      StdOut.println("c, " + c.toString());

      StdOut.println("a.SLOPE_ORDER.compare(b, c) = " + a.SLOPE_ORDER.compare(b, c));
      StdOut.println("a.slopeTo(b) = " + a.slopeTo(b));
      StdOut.println("a.slopeTo(c) = " + a.slopeTo(c));
    }
}
