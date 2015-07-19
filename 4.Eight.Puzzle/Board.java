public class Board {

  private char[][] board;
  private int N;

  public Board(int[][] blocks) {         // construct a board from an N-by-N array of blocks
    N = blocks.length;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++)
        board[i][j] = (char)blocks[i][j];
    }
  }
  // (where blocks[i][j] = block in row i, column j)
  public int dimension()       {         // board dimension N
    return N;
  }

  public int hamming()         {         // number of blocks out of place
    int hammingNo = 0;
    int index = 1;

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (board[i][j] != index++) hammingNo++;
      }
    }

    return hammingNo;
  }

  public int manhattan()       {         // sum of Manhattan distances between blocks and goal
    int manhattanSum = 0;

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        int tile = board[i][j];
        //e to avoid divide by zero for tile 1
        int e = tile == 1 ? 1 : 0;
        //Calculate tile distances
        int dy = (tile - 1 + e) / N;
        int dx = (tile % N) - 1;
        manhattanSum += dx + dy; 
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
     for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++)
        twinBoard[i][j] = (int)board[i][j];
    }

    //Switch two adjacent tiles, check for the blank space
    int row = 0;
    if (twinBoard[row][0] != 0 || twinBoard[row][1] != 0) row++;
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
  }
}
