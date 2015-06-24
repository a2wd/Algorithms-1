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

	private WeightedQuickUnionUF _unionStruct; 	// WeightedQuickUnionUF object to manage UnionFind operations
	private int[] _grid;												// grid array to track open/closed indices
	public int _gridWidth;											// gridWidth integer to record gid size

	private int xyTo1D(int x, int y) {			// Convert X and Y co-ords to a 1 dimensional array
		return (x * y) - 1;										// Origin is 1,1
	}

	private boolean _isValid(int x, int y) {		// Validate an indice
		if(x > 0 && y > 0 &&
				x <= _gridWidth && y <= _gridWidth) {
			return true;
				}
		else {
			return false;
		}
	}

	public Percolation(int N){              // create N-by-N grid, with all sites blocked
		int size = N * N;
		_unionStruct = new WeightedQuickUnionUF(size);
		_grid = new int[size];
		_gridWidth = N;

		for(int x=0; x<size; x++) {
			_grid[x] = 0;
		}
	}

	public void open(int i, int j){         // open site (row i, column j) if it is not open already
		if(!_isValid(i, j)) {
			throw new IndexOutOfBoundsException("Invalid index");
		}

		if(!isOpen(i,j)) {
			int xy1D = xyTo1D(i,j);

			_grid[xy1D] = 1;

			//Open connections to surrounding cells
			if(i > 1 && isOpen(i-1, j)) {
				_unionStruct.union(xy1D, xyTo1D(i-1, j));
			}
			if(j > 1 && isOpen(i, j-1)) {
				_unionStruct.union(xy1D, xyTo1D(i, j-1));
			}
			if(j < _gridWidth && isOpen(i, j+1)) {
				_unionStruct.union(xy1D, xyTo1D(i, j+1));
			}
			if(i < _gridWidth && isOpen(i+1, j)) {
				_unionStruct.union(xy1D, xyTo1D(i+1, j));
			}
		}
	}

	public boolean isOpen(int i, int j){    // is site (row i, column j) open?
		if(!_isValid(i, j)) {
			throw new IndexOutOfBoundsException("Invalid index");
		}

		if(_grid[xyTo1D(i,j)] == 1) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isFull(int i, int j){    // is site (row i, column j) full?
		if(!_isValid(i, j)) {
			throw new IndexOutOfBoundsException("Invalid index");
		}

		return false;
	}

	public boolean percolates(){            // does the system percolate?
		return false;
	}

	public static void main(String[] args){ // test client (optional)
		StdOut.println("In main");
		Percolation p = new Percolation(5);
		
		StdOut.println("Ready to open paths");
		p.open(1,1);
		p.open(1,2);
		StdOut.println("Ready to check connected");
		StdOut.println("Connected: " + p._unionStruct.connected(0,1));
	}
}
