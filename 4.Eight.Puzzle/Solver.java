public class Solver {
  private int movesToSolve;
  private Stack<Board> history;

  private class Node implements Comparable<Node> {
    private Board _state;
    private Node _previous;
    private int _numberOfMoves;


    public int compareTo(Node that) {
      if (moves() < that.moves()) return -1;
      if (moves() > that.moves()) return 1;

      return 0;
    }

    public Node(Board state, Node p, int m){
      _state = state;
      _previous = p;
      _numberOfMoves = m;
    }

    public Node previous() {
      return _previous;
    }

    public Board state() {
      return _state;
    }

    public int moves() {
      return _numberOfMoves;
    }
  }

  public Solver(Board initial) {         // find a solution to the initial board (using the A* algorithm)
    boolean notSolved = true;

    //Queue up given board
    MinPQ<Node> originalQueue = new MinPQ<Node>();
    originalQueue.insert(new Node(initial, null, 0));

    //Queue up twin board, to check if given board is solveable
    MinPQ<Node> twinQueue = new MinPQ<Node>();
    twinQueue.insert(new Node(initial.twin(), null, 0));

    Node originalNode, twinNode;

    while(notSolved) {
      originalNode = originalQueue.delMin();
      if (originalNode.state().isGoal()) {
        notSolved = false;
        movesToSolve = originalNode.moves();

        //Reconstruct history
        history = new Stack<Board>();
        Node pointer = originalNode;
        while(pointer != null) {
          history.push(pointer.state());
          pointer = pointer.previous();
        }
      }
      else {
         for (Board b : originalNode.state().neighbors()) {
          if (originalNode.previous() == null || !originalNode.previous().equals(b))
            originalQueue.insert(new Node(b, originalNode, originalNode.moves() + 1));
        }
      }

      twinNode = twinQueue.delMin();
      if (twinNode.state().isGoal()) {
        StdOut.println(twinNode.state().toString());
        notSolved = false;
        movesToSolve = -1;
        history = null;
      }
      else {
        for (Board b : twinNode.state().neighbors()) {
          if (twinNode.previous() == null || !twinNode.previous().equals(b))
            twinQueue.insert(new Node(b, twinNode, twinNode.moves() + 1));
        }
      }
    }
  }

  public boolean isSolvable()  {         // is the initial board solvable?
    return movesToSolve > -1;
  }

  public int moves() {                   // min number of moves to solve initial board; -1 if unsolvable
    return movesToSolve;
  }

  public Iterable<Board> solution() {    // sequence of boards in a shortest solution; null if unsolvable
    return history;
  }

  public static void main(String[] args){// solve a slider puzzle (given below)
    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }
}
