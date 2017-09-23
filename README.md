# Percolation implementing weighted union find algorithm
Implementation by Jordan Ainlsie and Teako Warfield-Graham

> Write a program to estimate the value of the percolation threshold via Monte Carlo simulation. Percolation. Given a porous landscape with water on the surface (or oil below), under what conditions will the water be able to drain through to the bottom (or the oil to gush through to the surface)? Scientists have defined an abstract process known as percolation to model such situations.

> We model a percolation system using an N­ x N grid of sites. Each site is either open or blocked. A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites. We say the system "percolates" if there is a full site in the bottom row.

To implement a percolation system, create a data type Percolation with the following API:
> public class Percolation {
> - public Percolation(int N) 
> - public void open(int i, int j)
> - public boolean isOpen(int i, int j) 
> - public boolean isFull(int i, int j) 
> - public boolean percolates()
>-}

To perform a series of computational experiments, create a data type PercolationStats with the following API:
> public class PercolationStats {
> - public PercolationStats(int N, int T)
> - public double mean()
> - public double stddev()
> - public double confidenceLow()
> - public double confidenceHigh()
>-}

*This challenge was developed by Bob Sedgewick and Kevin Wayne. Copyright © 2008.*