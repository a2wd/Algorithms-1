public class Brute {
	protected static void printUsage() {
		StdOut.println("Usage: javac Brute input.txt");
		StdOut.println("where input.txt is a file containing a number n followed by n coodinates in the form x y <newline>");
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

		//Compare all points
		for (int a = 0; a < allCoords.length - 3; a++) {
			for (int b = a + 1; b < allCoords.length - 2; b++) {
				for (int c = b + 1; c < allCoords.length - 1; c++) {

					//Skip inner loop if a,b,c are not collinear
					double slope = allCoords[a].slopeTo(allCoords[b]);
					if (slope != allCoords[a].slopeTo(allCoords[c]))
						break;

					//Check for a four-point collinear slope
					for (int d = c + 1; d < allCoords.length; d++) {
						if (slope == allCoords[a].slopeTo(allCoords[d])) {
							//Print out coordinates
							StdOut.println(allCoords[a].toString());
							StdOut.println(allCoords[b].toString());
							StdOut.println(allCoords[c].toString());
							StdOut.println(allCoords[d].toString());
							//Plot collinear slope
							allCoords[a].drawTo(allCoords[b]);
							allCoords[a].drawTo(allCoords[c]);
							allCoords[a].drawTo(allCoords[d]);
						}
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
