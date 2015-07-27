/* PointSET.java
 * Dependencies: algs4.jar, RectHV.java
 * Make: javac-algs4 PointSET.java
 * Usage: java-algs4 PointSET filename.txt
 */

import java.util.TreeSet;

public class PointSET {
  //private variables
  private TreeSet<Point2D> points;

  // construct an empty set of points 
  public  PointSET() {
    points = new TreeSet<Point2D>();
  }

  // is the set empty? 
  public  boolean isEmpty() {
    return points.size() == 0;
  }

  // number of points in the set 
  public  int size() {
    return points.size();
  }

  // add the point to the set (if it is not already in the set)
  public  void insert(Point2D p) {
    if (p == null)
      throw new java.lang.NullPointerException("Attempt to add a null Point2D");

    points.add(p);
  }

  // does the set contain point p? 
  public  boolean contains(Point2D p) {
    if (p == null)
      throw new java.lang.NullPointerException("Attempt to search on null Point2D");

    return points.contains(p);
  }

  // draw all points to standard draw 
  public  void draw() {
    for (Point2D p : points)
      p.draw();
  }

  // all points that are inside the rectangle 
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null)
      throw new java.lang.NullPointerException("Attempt to iterate on null RectHV");


    TreeSet<Point2D> pointsInRect = new TreeSet<Point2D>();

    for (Point2D p : points) {
      if(rect.contains(p))
        pointsInRect.add(p);
    }

    return pointsInRect;
  }

  // a nearest neighbor in the set to point p; null if the set is empty 
  public  Point2D nearest(Point2D p) {
    if (p == null)
      throw new java.lang.NullPointerException("Attempt to find nearest on null Point2D");

    if (points.isEmpty())
      return null;

    if(points.contains(p))
      return p;

    TreeSet<Point2D> tail = (TreeSet<Point2D>) points.tailSet(p);
    
    if(tail.isEmpty())
      return null;

    //else
    return tail.first();
  }

  // unit testing of the methods (optional) 
  public static void main(String[] args) {
    PointSET mySet = new PointSET();
    RectHV rect = new RectHV(0.2, 0.2, 0.5, 0.5);

    mySet.insert(new Point2D(0.5, 0.5));
    mySet.insert(new Point2D(0.6, 0.7));
    mySet.insert(new Point2D(0.8, 0.1));
    mySet.insert(new Point2D(0.9, 0.4));
    mySet.insert(new Point2D(0.2, 0.3));
    mySet.insert(new Point2D(0.3, 0.3));
    mySet.insert(new Point2D(0.4, 0.2));
    mySet.insert(new Point2D(0.5, 0.1));

    // rescale coordinates and turn on animation mode
    StdDraw.setXscale(0, 1); 
    StdDraw.setYscale(0, 1); 
    StdDraw.show(0);

    //Draw all points
    StdDraw.setPenRadius(0.01); 
    StdDraw.setPenColor(StdDraw.RED);
    mySet.draw();

    //Draw all points in RectHV
    StdDraw.setPenColor(StdDraw.BLUE);
    for (Point2D p : mySet.range(rect))
      p.draw();

    //Draw RectHV
    StdDraw.setPenRadius();
    StdDraw.setPenColor(StdDraw.BLACK);
    rect.draw();

    // display to screen all at once
    StdDraw.show(0);
  }
}
