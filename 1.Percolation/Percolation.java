/************************************************************************************
 * Compilation: javac Percolation.java
 * Execution: java Percolation < input.txt
 * Dependencies: QuickFindUF.java, WeightedQuickFindUF.java, stdlib.jar, algs4.jar
 *
 * Percolation.java: Find the percolation threshold via a Monte Carlo simulation
 ************************************************************************************/

/**
 * The <tt>Percolation.java</tt> class initialises and tracks a monte-carlo representation
 * of the percolation problem as per Algorithms 1 (Princeton)
 *
 * @author a2wd.com
 */

public class Percolation {

  private WeightedQuickUnionUF unionStruct;   // WeightedQuickUnionUF object to manage UnionFind operations
  private WeightedQuickUnionUF backWash;   // WeightedQuickUnionUF object to manage backwash
  private byte[] grid;                       // grid array to track open/closed indices
  private int gridWidth;                      // gridWidth integer to record gid size

  public Percolation(int N) {              // create N-by-N grid, with all sites blocked
    if (N <= 0)
      throw new IllegalArgumentException("N must be greater than 0");

    int size = (N * N) + 2;
    unionStruct = new WeightedQuickUnionUF(size);
    backWash = new WeightedQuickUnionUF(size);
    grid = new byte[size];
    gridWidth = N;

    //Initialise grid with open top/bottom elements
    grid[0] = 1;
    grid[size-1] = 1;
  }

  private int xyTo1D(int i, int j) {      // Convert X and Y co-ords to a 1 dimensional array
    return (gridWidth * (j - 1)) + i;   // Origin is 1,1. 0th element is top, max element is bottom
  }

  private boolean isValid(int i, int j) {   // Validate an indice
    if (i > 0 && j > 0
        && i <= gridWidth && j <= gridWidth) {
      return true;
        }

    return false;
  }

  public void open(int i, int j) {         // open site (row i, column j) if it is not open already
    if (!isValid(i, j)) {
      throw new IndexOutOfBoundsException("Invalid index");
    }

    if (!isOpen(i, j)) {
      int xy1D = xyTo1D(i, j);

      grid[xy1D] = 1;

      //Open connections to surrounding cells
      if (i > 1 && isOpen(i - 1, j)) {
        unionStruct.union(xy1D, xyTo1D(i - 1, j));
        backWash.union(xy1D, xyTo1D(i - 1, j));
      }
      if (j > 1 && isOpen(i, j-1)) {
        unionStruct.union(xy1D, xyTo1D(i, j - 1));
        backWash.union(xy1D, xyTo1D(i, j - 1));
      }
      if (j < gridWidth && isOpen(i, j + 1)) {
        unionStruct.union(xy1D, xyTo1D(i, j + 1));
        backWash.union(xy1D, xyTo1D(i, j + 1));
      }
      if (i < gridWidth && isOpen(i + 1, j)) {
        unionStruct.union(xy1D, xyTo1D(i + 1, j));
        backWash.union(xy1D, xyTo1D(i + 1, j));
      }

      //Fill from top to bottom
      if (i == 1) {
        unionStruct.union(xy1D, 0);
        backWash.union(xy1D, 0);
      }
      if (i == gridWidth) {
        unionStruct.union(xy1D, (gridWidth * gridWidth) + 1);
      }
    }
  }

  public boolean isOpen(int i, int j) {    // is site (row i, column j) open?
    if (!isValid(i, j)) {
      throw new IndexOutOfBoundsException("Invalid index");
    }

    if (grid[xyTo1D(i, j)] == 1) {
      return true;
    }

    return false;
  }

  public boolean isFull(int i, int j) {    // is site (row i, column j) full?
    if (!isValid(i, j)) {
      throw new IndexOutOfBoundsException("Invalid index");
    }

    if (backWash.connected(0, xyTo1D(i, j))) {
      return true;
    }

    return false;
  }

  public boolean percolates() {            // does the system percolate?
    if (unionStruct.connected(0, (gridWidth * gridWidth) + 1)) {
      return true;
    }

    return false;
  }

  public static void main(String[] args) { // test client (optional)
    StdOut.println("In main");
    Percolation p = new Percolation(5);

    StdOut.println("Ready to open paths");
    p.open(1, 1);
    p.open(1, 2);
    StdOut.println("Ready to check connected");
    StdOut.println("Connected: " + p.unionStruct.connected(p.xyTo1D(1, 1), p.xyTo1D(1, 2)));
    StdOut.println("Connected to top [1,1]: " + p.isFull(1, 1));
    StdOut.println("Connected to top [1,2]: " + p.isFull(1, 2));

    StdOut.println("Not connected to top [2,4]: " + p.isFull(2, 4));
  }
}
