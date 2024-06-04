import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] spaces;
    private int dim;
    private int trialNum;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        dim = (int)Math.pow(n, 2);
        trialNum = trials;
        if (n<=0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        spaces = new double[trials];
        for (int i = 0; i<trials; i++) {
            Percolation run = new Percolation(n);
            while (!run.percolates()) {
                run.open(StdRandom.uniformInt(1, n+1), StdRandom.uniformInt(1, n+1));
            }
            spaces[i] = (double)run.numberOfOpenSites()/dim;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(spaces);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(spaces);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean()-(1.96*stddev()/Math.sqrt(trialNum));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean()+(1.96*stddev()/Math.sqrt(trialNum));
    }

    // test client (see below)
    public static void main(String[] args) {
        // int n = StdIn.readInt();
        // int trials = StdIn.readInt();
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats perc = new PercolationStats(n, trials);
        System.out.println("mean                    = " + perc.mean());
        System.out.println("stddev                  = " + perc.stddev());
        System.out.println("95% confidence interval = [" + perc.confidenceLo() +", "+ perc.confidenceHi() + "]");
    }
}
