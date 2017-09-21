package a01;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private boolean[][] grid;
	private int n;
	private WeightedQuickUnionUF uF;
	private int topVirtualSite;
	private int bottomVirtualSite;
	private int uFSize;
	private int numOfOpenSpaces = 0;
	
	public Percolation(int n) {

		if (n <= 0)
			throw new java.lang.IllegalArgumentException();
		this.n = n;
		uFSize = n * n - 1;
		topVirtualSite = n * n;
		bottomVirtualSite = n * n + 1;
		uF = new WeightedQuickUnionUF(n * n + 2);

		for (int i = 0; i < n; i++) {
			uF.union(topVirtualSite, i);
		}

		for (int i = 0; i < n; i++) {
			uF.union(bottomVirtualSite, uFSize - i);
		}

		// Initializing 2d array grid all slots are false, which means they are
		// not open.
		grid = new boolean[n][n];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				grid[i][j] = false;
			}
		}
	}
	
	public void open(int row, int col) {
		inputValidation(row , col);
		grid[row][col] = true;		
	}
	
	/**
	 * checks to see if a row,col is full it needs to be open and connected to
	 * the top for it to be full
	 * 
	 * @param row
	 * @param col
	 * @return
	 */

	public boolean isFull(int row, int col) {
		inputValidation(row, col);
		return isOpen(row, col) == true && uF.connected(topVirtualSite, converts2dTo1d(row, col));
	}

	/**
	 * makes a row,col open by making the grid array at position row,col true
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isOpen(int row, int col) {
		inputValidation(row, col);
		return grid[row][col] == true;
	}

	public String numberOfOpenSites() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * percolates will tell you if the top connects to the bottom so it can
	 * percolate
	 * 
	 * @return
	 */
	public boolean percolates() {
		return uF.connected(topVirtualSite, bottomVirtualSite);
	}
	
	/**
	 * takes a row and col and turning it into a single number
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	private int converts2dTo1d(int row, int col) {
		return row * n + col;
	}

	private void inputValidation(int i, int j) {
		if (i < 0 || i >= n || j < 0 || j >= n)
			throw new IndexOutOfBoundsException("row index " + i + " must be between 0 and " + (n - 1));
	}
	
	public static void main(String[] args) {
		Percolation temp = new Percolation(3);

		temp.open(0, 0);
		temp.open(1, 0);
		temp.open(1, 1);
		temp.open(1, 2);
		temp.open(2, 2);

		System.out.println(temp.isOpen(0, 0));
		System.out.println(temp.isFull(0, 0));
		System.out.println(temp.percolates());
		// System.out.println(temp.uF.connected(temp.bottomVirtualSite, 0));
		// System.out.println(temp.uF.connected(0, 3));

	}


}

