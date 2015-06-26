/*
 * PercolationStats.java
 *
 * A solution to the percolation problem via a monte carlo simulation
 *
 */

public class PercolationStats
{
  private double[] openSites;

  public PercolationStats(int N, int T)
  {
    if (N <= 0 || T <= 0)
      throw new java.lang.IllegalArgumentException("Both N and T must be greater than 0");

    openSites = new double[T];

    for (int x = 0; x < T; x++) {
      Percolation grid = new Percolation(N);

      do {
        int i = StdRandom.uniform(N) + 1;
        int j = StdRandom.uniform(N) + 1;

        if (!grid.isOpen(i, j)) {
          grid.open(i, j);
          openSites[x] += 1;
        }

      } while (!grid.percolates());

      openSites[x] /= (N * N);
    }

  }

  public double mean()
  {
    //mu = (x1 + x2 .. xn) / _times
    return StdStats.mean(openSites);
  }

  public double stddev()
  {
    // stdev[rho^2] = [(x1 - mu)^2 + (x2 - mu)^2 + .. (xn - mu)^2] / (_times - 1)
    return StdStats.stddev(openSites);
  }

  public double confidenceLo()
  {
    //95% Confidence interval: { mu ± [(1.96 * rho) / sqrt(T)] }
    return (mean() - ((1.96 * stddev()) / Math.sqrt(openSites.length)));
  }

  public double confidenceHi()
  {
    return (mean() + ((1.96 * stddev()) / Math.sqrt(openSites.length)));
  }

  public static void main(String[] args)
  {
    if (args.length != 2) {
      StdOut.println("Usage: PercolationStats N T, where N is the grid size and T is the number of trials");
      return;
    }

    int N, T;
    try {
      N = Integer.parseInt(args[0]);
      T = Integer.parseInt(args[1]);
    }
    catch (NumberFormatException e) {
      StdOut.println("Arguments must be integers");
      return;
    }

    PercolationStats mcSim = new PercolationStats(N, T);
    StdOut.println("mean: " + mcSim.mean());
    StdOut.println("stddev: " + mcSim.stddev());
    StdOut.println("95% confidence interval: [" + mcSim.confidenceLo() + ", " + mcSim.confidenceHi() + "]");
  }
}
