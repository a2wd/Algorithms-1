import java.util.Arrays;

public class Fast {
  private static void printUsage() {
    StdOut.println("Usage: javac Brute input.txt");
    StdOut.println("where input.txt is a file containing a number n followed by n coodinates in the form x y <newline>");
  }

  private static void printArray(Point[] arr, Point origin) {
    for (Point i : arr)
      StdOut.println(i.toString() + "|" + origin.slopeTo(i));

    StdOut.println();
  }

  private static void printPoints(Point[] arr) {
    //Print vertices
    for (int i = 0; i < arr.length; i++) {
      StdOut.print(arr[i].toString());

      if (i < arr.length - 1)
        StdOut.print(" -> ");
      else
        StdOut.println();

      arr[i].draw();
    }

    //Plot collinear line
    arr[0].drawTo(arr[arr.length - 1]);
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
    final int pointCount = allCoords.length;

    for (int x = 0; x < pointCount; x++) {
      allCoords[x] = new Point(inputData[(x * 2) + 1], inputData[(x * 2) + 2]);
    }

    Point[] originalCoords = new Point[inputData[0]];
    System.arraycopy(allCoords, 0, originalCoords, 0, pointCount);

    for (int x = 0; x < pointCount; x++) {
    //for (int x = 0; x < 1; x++) {
      Arrays.sort(allCoords, originalCoords[x].SLOPE_ORDER);

      StdOut.println("In " + x + ", with " + originalCoords[x].toString());
      printArray(allCoords, originalCoords[x]);
    
      //Find first point of current slope
      int matched = 1;
      double prevSlope = allCoords[0].slopeTo(allCoords[x]);

      //Find a matching slope
      for (int i = 1; i < pointCount; i++) {
        double currentSlope = allCoords[0].slopeTo(allCoords[i]);
        StdOut.print("Matched: " + matched + ", i: " + i + ", arr[i]=" + allCoords[i]);
        StdOut.println(" || prevSlope: " + prevSlope + ", currentSlope: "+ currentSlope);

        if(matched > 2){
          //If we have a set, proceed through entries for tail
          while (i < pointCount && currentSlope == prevSlope) {
          StdOut.println("Now inner loop, i: " + i + ", curSlop: " + currentSlope + ", matched: " + matched);
            currentSlope = allCoords[0].slopeTo(allCoords[i++]);
            matched++;
          }
          i--;

          StdOut.println("Now out of inner loop, i: " + i + ", curSlop: " + currentSlope + ", matched: " + matched);
          //Sort matched slope
          Point[] match = new Point[matched];
          match[0] = allCoords[0];
          for(int j = 1; j < matched; j ++)
            match[j] = allCoords[i - matched + j];

          //Print matched array if we have lowest point
          //StdOut.println("Sort matches:");
          printArray(match, allCoords[0]);
          Arrays.sort(match);
          //StdOut.println("after sort:");
          printArray(match, allCoords[0]);

          if(match[0].compareTo(allCoords[0]) == 0)
            printPoints(match);

          //Reset counter
          matched = 1;
        }

         if(prevSlope == currentSlope)
          matched++;
        else
          matched = 1;

       //Store current slope for next iteration
        prevSlope = currentSlope;
      }

      //Check for match at end of list
      if(matched > 2) {
          //Sort matched slope
          Point[] match = new Point[matched + 1];
          match[0] = allCoords[0];
          for(int j = 1; j < matched + 1; j ++)
            match[j] = allCoords[allCoords.length - matched + j - 1];

          Arrays.sort(match);
          if(match[0].compareTo(allCoords[0]) == 0)
            printPoints(match);
      }
    }

    // display to screen all at once
    StdDraw.show(0);

    // reset the pen radius
    StdDraw.setPenRadius();

  }
}
