package a01;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * 
 * @author Jordan Ainlsie and Teako Warfield-Graham
 * 
 *  Percolation: Given a porous landscape with water on the surface 
 *  (or oil below), under what conditions will the water be able to 
 *  drain through to the bottom (or the oil to gush through to the 
 *  surface)?
 *  
 *  We model a percolation system using an N­ x N grid of sites. Each 
 *  site is either open or blocked. A full site is an open site that 
 *  can be connected to an open site in the top row via a chain of 
 *  neighboring (left, right, up, down) open sites. We say the system 
 *  percolates if there is a full site in the bottom row.
 *
 */

public class Percolation {
	
	private boolean[][] grid;
	private int n;
	private WeightedQuickUnionUF uF;
	private WeightedQuickUnionUF secretUF;
	private int topVirtualSite;
	private int bottomVirtualSite;
	private int uFSize;
	private int numOfOpenSpaces = 0;
	
	public Percolation(int n) {

		if (n <= 0)
			throw new java.lang.IllegalArgumentException();
		this.n = n;
		uFSize = n * n - 1; // Must -1 as an array starts at zero index
		topVirtualSite = n * n;
		bottomVirtualSite = n * n + 1;
		uF = new WeightedQuickUnionUF(n * n + 2); // Has top and bottom virtual sites to calculate percolation
		secretUF = new WeightedQuickUnionUF(n * n + 1); // Only top virtual site to eliminate backwash

		// Connects the top virtual sites to the top row
		for (int i = 0; i < n; i++) {
			uF.union(topVirtualSite, i);
			secretUF.union(topVirtualSite, i);
		}

		// Connects the bottom virtual site on uF to the bottom row
		for (int i = 0; i < n; i++) {
			uF.union(bottomVirtualSite, uFSize - i);
		}

		// Initializing 2d array grid all slots are false, which means they are blocked.
		grid = new boolean[n][n];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				grid[i][j] = false;
			}
		}
	}
	
	public void open(int row, int col) {
		inputValidation(row, col);
		if (!isOpen(row, col) == true) {
			numOfOpenSpaces++;
		}
		grid[row][col] = true;
	
		// GRID EDGE CASES
		// Upper left corner (row 0/col 0) ! to check above or left slots
		if (row == 0 && col == 0) {
			checkSlotRight(row, col);
			checkSlotBelow(row, col);
		}
		// Bottom left corner (row n/col 0) ! to check left or below slots
		else if (row == n - 1 && col == 0) {
			checkSlotRight(row, col);
			checkSlotAbove(row, col);
		}
		// Top right corner (row 0/col n) ! to check right or top slots
		else if (row == 0 && col == n - 1) {
			checkSlotLeft(row, col);
			checkSlotBelow(row, col);
		}
		// Bottom right corner (row n/col n) ! to check right or bottom slots
		else if (row == n - 1 && col == n - 1) {
			checkSlotLeft(row, col);
			checkSlotAbove(row, col);
		}		
		// Left border (col 0) ! left slots
		else if (col == 0) {
			checkSlotAbove(row, col);
			checkSlotRight(row, col);
			checkSlotBelow(row, col);
		}
		// Right border (col n) ! right slots
		else if (col == n - 1) {
			checkSlotAbove(row, col);
			checkSlotLeft(row, col);
			checkSlotBelow(row, col);
		}
		// Top border (row 0) ! above slots
		else if (row == 0) {
			checkSlotLeft(row, col);
			checkSlotRight(row, col);
			checkSlotBelow(row, col);
		}
		// Bottom border (row n) ! below slots
		else if (row == n - 1) {
			checkSlotAbove(row, col);
			checkSlotRight(row, col);
			checkSlotLeft(row, col);
		}
		// If ! a corner/border edge of the grid check all surrounding spaces.
		else {
			checkSlotAbove(row, col);
			checkSlotBelow(row, col);
			checkSlotRight(row, col);
			checkSlotLeft(row, col);
		}
	}
	
	/**
	 * Checks to see if a slot in the grid is full. 
	 * A slot is full if it's both open and connected to the top. 
	 * This method determines backwash, so we will utilize our secretUF
	 * which initializes an array without bottom virtual site. 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isFull(int row, int col) { 
		inputValidation(row, col);
		return isOpen(row, col) == true && secretUF.connected(0, converts2dTo1d(row, col));
	}

	/**
	 * makes a row,col open by making the grid array at position row,col true
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isOpen(int row, int col) {
		inputValidation(row, col);
		return grid[row][col] == true;
	}


	/**
	 * Percolates checks to see if uF object with top and bottom virtual sites
	 * are connected, which means the system percolates.
	 * @return
	 */
	public boolean percolates() {
		return uF.connected(topVirtualSite, bottomVirtualSite);
	}

	
	// PRIVATE HELPER METHODS
	
	/**
	 * converts2DTo1D() takes a row and col from percolation grid (2D Array) 
	 * and converts it into the corresponding single index reference used by 
	 * WeightedQuickUnionUF, which unions grid data in a single 1D array.
	 * @param row
	 * @param col
	 * @return
	 */
	private int converts2dTo1d(int row, int col) {
		return row * n + col;
	}
	
	/**
	 * Each percolation method should throw an exception for invalid indices, 
	 * inputValidation() method performs our validation process.
	 * @param row
	 * @param col
	 * @return
	 */
	private void inputValidation(int i, int j) {
		if (i < 0 || i >= n || j < 0 || j >= n)
			throw new IndexOutOfBoundsException("row index " + i + " must be between 0 and " + (n - 1));
	}
	
	/**
	 * Below is a series of helper methods used by our open() method.
	 * They check various grid slots surrounding the slot to be opened 
	 * to see if they are also open, in which case they are connected 
	 * to the newly opened slot via union find.
	 * @param row
	 * @param col
	 */
	private void checkSlotAbove(int row, int col) {
		if (isOpen(row - 1, col) == true) {
			uF.union(converts2dTo1d(row, col), converts2dTo1d(row - 1, col));
			secretUF.union(converts2dTo1d(row, col), converts2dTo1d(row - 1, col));
		}
	}
	
	private void checkSlotLeft(int row, int col) {
		if (isOpen(row, col - 1) == true) {
			uF.union(converts2dTo1d(row, col), converts2dTo1d(row, col - 1));
			secretUF.union(converts2dTo1d(row, col), converts2dTo1d(row, col - 1));
		}
	}
	
	private void checkSlotRight(int row, int col) {
		if (isOpen(row, col + 1) == true) {
			uF.union(converts2dTo1d(row, col), converts2dTo1d(row, col + 1));
			secretUF.union(converts2dTo1d(row, col), converts2dTo1d(row, col + 1));
		}
	}
	
	private void checkSlotBelow(int row, int col) {
		if (isOpen(row + 1, col) == true) {
			uF.union(converts2dTo1d(row, col), converts2dTo1d(row + 1, col));
			secretUF.union(converts2dTo1d(row, col), converts2dTo1d(row + 1, col));
		}
	}
	
	// END HELPER METHODS
	
	public static void main(String[] args) {
		Percolation temp = new Percolation(3);
		int size = temp.grid.length;
		
		System.out.println("Initialized a  " + size + "-by-" + size + " grid");
		System.out.println("Does system percolate? " + temp.percolates() + "\n");
		
		System.out.println("Checking value of all grid slots... ");
		for(int i = 0; i < temp.grid.length; i++){
			for(int j = 0; j < temp.grid.length; j++){
				System.out.println(
						"row: " + i + " column: " + j + " " 
						+ temp.grid[i][j]
						);
			}	
		}
		
		System.out.println("\nNow opening all sites from top to bottom in column 0 ");

		temp.open(0, 0);
		temp.open(1, 0);
		temp.open(2, 0); // System should now percolate
		
		System.out.println("Did site (0, 0) open? " + temp.isOpen(0, 0));
		System.out.println("Is site (0, 0) full? " + temp.isFull(0, 0));
		
		System.out.println("\nCheck union in grid uF " + temp.uF.connected(0, 0));
		System.out.println("Check union in grid secretUf " + temp.secretUF.connected(0, 0));
		
		System.out.println("\nDoes system now percolate? " + temp.percolates());

		System.out.println("\nTesting a site that has NOT been opened... ");
		System.out.println("Is site (1, 1) open? " + temp.isOpen(1, 1));
		System.out.println("Is site (1, 1) full? " + temp.isFull(1, 1));
		
		System.out.println("\nOpening site (2, 2) bottom right corner to test backwash... ");
		temp.open(2, 2); // This site should not be full if backwash solution worked
		System.out.println("Is site (2, 2) open? " + temp.isOpen(2, 2));
		System.out.println("Is site (2, 2) full? " + temp.isFull(2, 2));

		System.out.println("\nChecking value of all grid slots... ");
		for(int i = 0; i < temp.grid.length; i++){
			for(int j = 0; j < temp.grid.length; j++){
				System.out.println(
						"row: " + i + " column: " + j + " " 
						+ temp.grid[i][j]
						);
			}	
		}


	}

}

