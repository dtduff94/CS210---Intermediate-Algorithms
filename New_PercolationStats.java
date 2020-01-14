import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// Estimates percolation threshold for an N-by-N percolation system.
public class New_PercolationStats {
    private int N;
    private int T;
    private double[] percthreshold;

    // Perform T independent experiments (Monte Carlo simulations) on an 
    // N-by-N grid.
    public New_PercolationStats(int N, int T) {
	this.N = N;
	this.T = T;
	if (N <= 0) {
	    throw new java.lang.IllegalArgumentException("N needs to be greater than 0");
	}
	if (T <= 0) {
	    throw new java.lang.IllegalArgumentException("T needs to be greater than 0");
	}
	this.percthreshold = new double[T];
	for (int x = 0; x < T; x++) {
	    New_Percolation percsystem = new New_Percolation(N);
	    int threshold = 0;
	    while (!percsystem.percolates()) {
		int i = StdRandom.uniform(0, N);
		int j = StdRandom.uniform(0, N);
		//int k = StdRandom.uniform(1, 4);
		if (!percsystem.isOpen(i, j)) {
		    percsystem.open(i, j, 1);
		    threshold += 1;
		}
	    }
	    percthreshold[x] = (threshold / (double) (N * N));
	}
    }
    
    // Sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(percthreshold);
    }

    // Sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(percthreshold);
    }

    // Low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(percthreshold.length));
    }

    // High endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / Math.sqrt(percthreshold.length));
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        New_PercolationStats stats = new New_PercolationStats(N, T);
        StdOut.printf("mean           = %f\n", stats.mean());
        StdOut.printf("stddev         = %f\n", stats.stddev());
        StdOut.printf("confidenceLow  = %f\n", stats.confidenceLow());
        StdOut.printf("confidenceHigh = %f\n", stats.confidenceHigh());
    }
}
