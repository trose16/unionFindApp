package a01;

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
	private double[] thresholds;
	private Percolation percolation;
	private int openCount = 0;

	/**
	 * Performs T independent experiments on an N­ x ­N grid
	 */
	public PercolationStats(int N, int T) {

		if (N <= 0)
			throw new java.lang.IllegalArgumentException();
		thresholds = new double[T];
		openCount = 0;

		for (int i = 0; i < T; i++) {
			percolation = new Percolation(N);
			do {
				// Choose a site (row i, column j) uniformly at random
				int row = StdRandom.uniform(N);
				int col = StdRandom.uniform(N);
				if (!percolation.isOpen(row, col)) {
					percolation.open(row, col);
					openCount++;
				}
			} while (!percolation.percolates());
			thresholds[i] = (double) openCount / (N * N);
			openCount = 0;
		}
	}

	/**
	 * Sample mean of percolation threshold
	 */
	public double mean() {
		return StdStats.mean(thresholds);
	}

	/**
	 * Sample standard deviation of percolation threshold
	 */
	public double stddev() {
		return StdStats.stddev(thresholds);
	}

	/**
	 * Low endpoint of 95% confidence interval
	 */
	public double confidenceLow() {
		return mean() - ((1.96 * stddev()) / Math.sqrt(thresholds.length));
	}

	/** 
	 * High endpoint of 95% confidence interval
	 */
	public double confidenceHigh() {
		return mean() + ((1.96 * stddev()) / Math.sqrt(thresholds.length));
	}

	public static void main(String[] args) {

		PercolationStats perStats = new PercolationStats(2, 100000);
		System.out.printf("mean() = %f %n", perStats.mean());
		System.out.printf("stddev() = %f %n", perStats.stddev());
		System.out.printf("confidenceLow() = %f %n", perStats.confidenceLow());
		System.out.printf("confidenceHigh() = %f %n", perStats.confidenceHigh());
	}
}