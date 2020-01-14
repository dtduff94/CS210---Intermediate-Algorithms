import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Models an N-by-N percolation system.
public class New_Percolation {
    private int N;
    private int opencount = 0;
    private int [][] percsystem;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private WeightedQuickUnionUF weightedQuickUnionUFtop;

    // Create an N-by-N grid, with all sites blocked.
    public New_Percolation(int N) {
	this.N = N;
	if (N <= 0) {
	    throw new java.lang.IllegalArgumentException();
	}
	percsystem = new int[N][N];
	for (int i = 0; i < N; i++) {
	    for (int j = 0; j < N; j++) {
		percsystem[i][j] = 0;
	    }
	}
	opencount = 0;
	weightedQuickUnionUF = new WeightedQuickUnionUF((N * N) + 2);
	weightedQuickUnionUFtop = new WeightedQuickUnionUF((N * N) + 1);
    }

    // Open site (i, j) if it is not open already.
    public void open(int i, int j, int k) {
	if (i < 0 || i > (N - 1) || j < 0 || j > (N - 1)) {
	    throw new java.lang.IndexOutOfBoundsException("Spot out of grid");
	}
	if (isOpen(i, j)) {
	    return;
	}    
	if (percsystem[i][j] == 0) {
	    percsystem[i][j] = k;
	    if (i == 0) {
		weightedQuickUnionUF.union(0, encode(i, j));
		weightedQuickUnionUFtop.union(0, encode(i, j));
	    }
	    if (i == (N - 1)) {
		weightedQuickUnionUF.union((encode(i, j)), ((N * N) + 1));
	    }
	    if (i > 0 && percsystem[i][j] == percsystem[i - 1][j]) {
		weightedQuickUnionUF.union((encode(i, j)), (encode((i - 1), j)));
		weightedQuickUnionUFtop.union((encode(i, j)), (encode((i - 1), j)));
	    }
	    if (i < (N - 1) && percsystem[i][j] == percsystem[i + 1][j]) {
		weightedQuickUnionUF.union((encode(i, j)), (encode((i + 1), j)));
		weightedQuickUnionUFtop.union((encode(i, j)), (encode((i + 1), j)));
	    }
	    if (j > 0 && percsystem[i][j] == percsystem[i][j - 1]) {
		weightedQuickUnionUF.union((encode(i, j)), (encode(i, (j - 1))));
		weightedQuickUnionUF.union((encode(i, j)), (encode(i, (j - 1))));
	    }
	    if (j < (N - 1) && percsystem[i][j] == percsystem[i][j + 1]) {
		weightedQuickUnionUF.union((encode(i, j)), (encode(i, (j + 1))));
		weightedQuickUnionUF.union((encode(i, j)), (encode(i, (j + 1))));
	    }
	}
    } 

    // Is site (i, j) open?
    public boolean isOpen(int i, int j) {
	if (i < 0 || i > (N - 1) || j < 0 || j > (N - 1)) {
	    throw new java.lang.IndexOutOfBoundsException("Spot out of grid");
	}
	return (percsystem[i][j] == 1 || percsystem[i][j] == 2 || percsystem[i][j] == 3);
    }

    // Is site (i, j) full?
    public boolean isFull(int i, int j) {
	if (i < 0 || i > (N - 1) || j < 0 || j > (N - 1)) {
	    throw new java.lang.IndexOutOfBoundsException("Spot out of grid");
	}
	return ((percsystem[i][j] == 1 || percsystem[i][j] == 2 || percsystem[i][j] == 3) && weightedQuickUnionUFtop.connected(0, (encode(i, j))));
    }

    // Number of open sites.
    public int numberOfOpenSites() {
	for (int i = 0; i < N; i++) {
	    for (int j = 0; j < N; j++) {
		if (percsystem[i][j] == 1 || percsystem[i][j] == 2 || percsystem[i][j] == 3) {
		    opencount += 1;
		}
	    }
	}
	return opencount;
    }

    // Does the system percolate?
    public boolean percolates() {
	return weightedQuickUnionUF.connected(0, ((N * N) + 1));
    }

    // An integer ID (1...N) for site (i, j).
    private int encode(int i, int j) {
	return ((i * N) + (j + 1));
    }

  // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        New_Percolation perc = new New_Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            int k = in.readInt();
            perc.open(i, j, k);
        }
        StdOut.println(perc.numberOfOpenSites() + " open sites");
        if (perc.percolates()) {
            StdOut.println("percolates");
        }
        else {
            StdOut.println("does not percolate");
        }
        
        // Check if site (i, j) optionally specified on the command line
        // is full.
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.println(perc.isFull(i, j));
        }
    }
}
