import java.util.Arrays;

public class Brute {
  private static void printUsage() {
    StdOut.println("Usage: javac Brute input.txt");
    StdOut.println("where input.txt is a file containing a number n followed by n coodinates in the form x y <newline>");
  }

  private void printPoints(Point a, Point b, Point c, Point d) {
    Point[] arr = new Point[] {a, b, c, d};
    Arrays.sort(arr);

    //Print vertices
    for (int i = 0; i < 4; i++) {
      StdOut.print(arr[i].toString());

      if (i < 3)
        StdOut.print(" -> ");
      else
        StdOut.println();

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
    Brute plotter = new Brute();

    for (int x = 0; x < allCoords.length; x++) {
      allCoords[x] = new Point(inputData[(x * 2) + 1], inputData[(x * 2) + 2]);
      allCoords[x].draw();
    }

    //Compare all points
    for (int a = 0; a < allCoords.length - 3; a++) {
      for (int b = a + 1; b < allCoords.length - 2; b++) {

        //Pre calculate slope for a to b
        double slope = allCoords[a].slopeTo(allCoords[b]);

        for (int c = b + 1; c < allCoords.length - 1; c++) {

          //Pre calculate slope for a to c
          double slopeB = allCoords[a].slopeTo(allCoords[c]);

          //Check for a four-point collinear slope
          for (int d = c + 1; d < allCoords.length; d++) {
            double slopeC = allCoords[a].slopeTo(allCoords[d]);

            if (slope == slopeB && slope == slopeC)
              plotter.printPoints(allCoords[a], allCoords[b], allCoords[c], allCoords[d]);
          }
        }
      }
    }

    // display to screen all at once
    StdDraw.show(0);

    // reset the pen radius
    StdDraw.setPenRadius();
  }
}
