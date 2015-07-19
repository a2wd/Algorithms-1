import java.util.ArrayList;

public class Board {

  private int[][] board;
  private int N;

  private void copyBoard(int[][] src, int[][] dest, int N) {
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++)
        dest[i][j] = src[i][j];
    }
  }

  public Board(int[][] blocks) {         // construct a board from an N-by-N array of blocks
    if (blocks == null)
      throw new java.lang.NullPointerException("Null blocks array passed to board constructor");

    N = blocks.length;
    board = new int[N][N];
    copyBoard(blocks, board, N);
  }
  // (where blocks[i][j] = block in row i, column j)
  public int dimension()       {         // board dimension N
    return N;
  }

  public int hamming()         {         // number of blocks out of place
    int hammingNo = 0;
    int index = 0;

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        index++;
        if (board[i][j] == 0) continue;
        if (board[i][j] != index) hammingNo++;
      }
    }

    return hammingNo;
  }

  public int manhattan()       {         // sum of Manhattan distances between blocks and goal
    int manhattanSum = 0;
    int index = 0;

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        int tile = board[i][j];
        index++;

        //Break for zero-tile
        if(tile == 0) continue;

        //Calculate tile distances
        int dy = ((tile - 1) / N) - i;
        int dx = ((tile - 1) % N) - j;
        manhattanSum += Math.abs(dx) + Math.abs(dy);
      }
    }

    return manhattanSum;
  }

  public boolean isGoal()      {         // is this board the goal board?
    return hamming() == 0;
  }

  public Board twin()          {         // a board that is obtained by exchanging two adjacent blocks in the same row
    //Copy board tiles
    int[][] twinBoard = new int[N][N];
    copyBoard(board, twinBoard, N);

    //Switch two adjacent tiles, check for the blank space
    int row = 0;
    if (twinBoard[row][0] == 0 || twinBoard[row][1] == 0) row++;
    int temp = twinBoard[row][0];
    twinBoard[row][0] = twinBoard[row][1];
    twinBoard[row][1] = temp;

    return new Board(twinBoard);
  }

  public boolean equals(Object y) {      // does this board equal y?
    //Basic Java checks
    if (y == this) return true;
    if (y == null) return false;
    if (y.getClass() != this.getClass()) return false;

    //Object-specific check
    Board that = (Board) y;
    if (that.toString() != this.toString())  return false;

    //else
    return true;
  }

  public Iterable<Board> neighbors() {   // all neighboring boards
    ArrayList<Board> neighbors = new ArrayList<Board>();
    int[][] tempBoard = new int[N][N];
    int x = 0, y = 0;

    copyBoard(board, tempBoard, N);

outerLoop:
    for (x = 0; x < N; x++) {
      for (y = 0; y < N; y++) {
        if(tempBoard[x][y] == 0) break outerLoop;
      }
    }

    //Neighbor left
    if (x > 0) {
      tempBoard[x][y] = tempBoard[x - 1][y];
      tempBoard[x - 1][y] = 0;

      neighbors.add(new Board(tempBoard));

      tempBoard[x - 1][y] = tempBoard[x][y];
      tempBoard[x][y] = 0;
   }

    //Neighbor right
    if (x < N - 1) {
      tempBoard[x][y] = tempBoard[x + 1][y];
      tempBoard[x + 1][y] = 0;

      neighbors.add(new Board(tempBoard));

      tempBoard[x + 1][y] = tempBoard[x][y];
      tempBoard[x][y] = 0;
   }

    //Neighbor above
    if (y > 0) {
      tempBoard[x][y] = tempBoard[x][y - 1];
      tempBoard[x][y - 1] = 0;

      neighbors.add(new Board(tempBoard));

      tempBoard[x][y - 1] = tempBoard[x][y];
      tempBoard[x][y] = 0;
    }

    //Neighbor below
    if (y < N - 1) {
      tempBoard[x][y] = tempBoard[x][y + 1];
      tempBoard[x][y + 1] = 0;

      neighbors.add(new Board(tempBoard));

      tempBoard[x][y + 1] = tempBoard[x][y];
      tempBoard[x][y] = 0;
    }

    return neighbors;
  }

  public String toString()     {         // string representation of this board (in the output format specified below)
    StringBuilder s = new StringBuilder();
    s.append(N + "\n");
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        s.append(String.format("%2d ", board[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  public static void main(String[] args){// unit tests (not graded)
    // create a board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
    Board test = new Board(blocks);

    StdOut.println("Board created from " + args[0]);
    StdOut.println("Board dimension: " + test.dimension());
    StdOut.println(test.toString());

    StdOut.println("Is goal: " + test.isGoal());
    StdOut.println("Manhattan no.: " + test.manhattan());
    StdOut.println("Hamming no.: " + test.hamming());

    StdOut.println("Twin of input board:");
    Board twin = test.twin();
    StdOut.println(twin.toString());

    StdOut.println("Equals self: " + test.equals(test));
    StdOut.println("Equals twin: " + test.equals(twin));

    StdOut.println("\nNeighbours:");
    int l = 1;
    for(Board b : test.neighbors()) {
      StdOut.println("Equals neighbor " + l++ + ": " + test.equals(b));
    }

    for(Board b : test.neighbors()) {
      StdOut.println("\n" + b.toString());
    }
  }
}
