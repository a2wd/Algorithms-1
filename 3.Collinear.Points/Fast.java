import java.util.Arrays;

public class Fast {
  private static void printUsage() {
    StdOut.println("Usage: javac Brute input.txt");
    StdOut.println("where input.txt is a file containing a number n followed by n coodinates in the form x y <newline>");
  }

  private static void printPoints(Point a, Point b, Point c, Point d) {
    Point[] arr = new Point[] {a, b, c, d};
    Arrays.sort(arr);

    //Print vertices
    for (int i = 0; i < 4; i++) {
      StdOut.print(arr[i].toString());

      if (i != 3)
        StdOut.print(" -> ");
      else
        StdOut.println();

      arr[i].draw();
    }

    //Plot collinear line
    arr[0].drawTo(arr[3]);
  }

  public static void main(String[] args) {
    if (args.length < 1)
    {
      printUsage();
      return;
    }

    In inputFile = new In(args[0]);
    //Checks for valid file in In()

    int[] inputData = inputFile.readAllInts();

    if (inputData.length < 9) {
      printUsage();
      return;
    }

    if (inputData.length != (inputData[0] * 2) + 1) {
      printUsage();
      return;
    }

    // rescale coordinates and turn on animation mode
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    StdDraw.show(0);
    StdDraw.setPenRadius(0.01);  // make the points a bit larger

    Point[] allCoords = new Point[inputData[0]];

    for (int x = 0; x < allCoords.length; x++) {
      allCoords[x] = new Point(inputData[(x * 2) + 1], inputData[(x * 2) + 2]);
    }

    Point[] originalCoords = new Point[inputData[0]];
    System.arraycopy(allCoords, 0, originalCoords, 0, allCoords.length);

    for (int x = 0; x < originalCoords.length; x++) {
      Arrays.sort(allCoords, originalCoords[x].SLOPE_ORDER);
      for (int s = 0; s < allCoords.length - 2; s++) {

        if (originalCoords[x].slopeTo(allCoords[s]) == originalCoords[x].slopeTo(allCoords[s + 2]))
          printPoints(originalCoords[x], allCoords[s], allCoords[s + 1], allCoords[s + 2]);
      }
    }

    // display to screen all at once
    StdDraw.show(0);

    // reset the pen radius
    StdDraw.setPenRadius();

  }
}
