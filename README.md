# Percolation
Implementation by Jordan Ainlsie and Teako Warfield-Graham

> Write a program to estimate the value of the percolation threshold via Monte Carlo simulation. Percolation. Given a composite systems comprised of randomly distributed insulating and metallic materials: what fraction of the materials need to be metallic so that the composite system is an electrical conductor? Given a porous landscape with water on the surface (or oil below), under what conditions will the water be able to drain through to the bottom (or the oil to gush through to the surface)? Scientists have defined an abstract process known as percolation to model such situations.

To model a percolation system, create a data type Percolation with the following API:
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