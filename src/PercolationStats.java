/**
 * Created by artemfomin on 5/17/17.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] times;

    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid

        checkInput(n, trials);
        times = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            double attempt = 0;
            while (!percolation.percolates()) {
                int column = StdRandom.uniform(n) + 1;
                int row = StdRandom.uniform(n) + 1;
                if (!percolation.isOpen(row, column)) {
                    percolation.open(row, column);
                    attempt++;
                }
            }
            times[i] = attempt / (n * n);
        }
    }

    public double mean() {                         // sample mean of percolation threshold

        return StdStats.mean(times);

    }

    public double stddev() {                        // sample standard deviation of percolation threshold

        return StdStats.stddev(times);

    }

    public double confidenceLo() {                  // low  endpoint of 95% confidence interval

        return mean() - ((1.96 * stddev()) / Math.sqrt(times.length));

    }

    public double confidenceHi() {                  // high endpoint of 95% confidence interval

        return mean() + ((1.96 * stddev()) / Math.sqrt(times.length));

    }

    private void checkInput(int N, int T) { //check user input

        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {       // test client (described below)

        In in = new In();
        Out out = new Out();

        int N = in.readInt();
        int T = in.readInt();

        PercolationStats ps = new PercolationStats(N, T);

        double mean = ps.mean();
        double stddev = ps.stddev();
        double lowConf = ps.confidenceLo();
        double highConf = ps.confidenceHi();

        out.println("mean                    = " + mean);
        out.println("stddev                  = " + stddev);
        out.println("95% confidence interval = " + lowConf + " " + highConf);
        out.close();

    }
}
