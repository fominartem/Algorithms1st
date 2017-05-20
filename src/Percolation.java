import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by artemfomin on 5/17/17.
 */

public class Percolation {

    private boolean[] grid;
    private WeightedQuickUnionUF quickFind;
    private WeightedQuickUnionUF quickFindWithoutBottom;
    private int size;
    private int size1D;
    private int openSites;

    public Percolation(int n) { // create n-by-n grid, with all sites blocked
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        size = n;
        size1D = n * n;
        grid = new boolean[size1D + 2]; //+ top[0] and bottom [N*N+1]
        quickFind = new WeightedQuickUnionUF(size1D + 2);
        quickFindWithoutBottom = new WeightedQuickUnionUF(size1D + 1);
        openSites = 0;
    }

    public static void main(String[] args) {   // test client (optional)

    }

    public void open(int row, int col) {    // open site (row, col) if it is not open already
        checkIndexes(row, col);
        int index = getFlatIndex(row, col);

        if (grid[index] != true) {
            grid[index] = true;
            openSites++;
        }

        if (col != size) {
            int rightIndex = getFlatIndex(row, col + 1);
            if (grid[rightIndex] == true) {
                quickFind.union(index, rightIndex);
                quickFindWithoutBottom.union(index, rightIndex);
            }
        }
        if (col != 1) {
            int leftIndex = getFlatIndex(row, col - 1);
            if (grid[leftIndex] == true) {
                quickFind.union(index, leftIndex);
                quickFindWithoutBottom.union(index, leftIndex);
            }
        }

        if (row == 1) {
            quickFind.union(0, index);
            quickFindWithoutBottom.union(0, index);
        } else {
            int topIndex = getFlatIndex(row - 1, col);
            if (grid[topIndex] == true) {
                quickFind.union(index, topIndex);
                quickFindWithoutBottom.union(index, topIndex);
            }
        }

        if (row != size) {
            int bottomIndex = getFlatIndex(row + 1, col);
            if (grid[bottomIndex] == true) {
                quickFind.union(index, bottomIndex);
                quickFindWithoutBottom.union(index, bottomIndex);
            }
        } else {
            quickFind.union(size1D + 1, index);
        }

    }

    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        checkIndexes(row, col);
        return grid[getFlatIndex(row, col)] == true;

    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        checkIndexes(row, col);
        int index = getFlatIndex(row, col);
        return quickFindWithoutBottom.connected(0, index);
    }

    public int numberOfOpenSites() {      // number of open sites
        return openSites;
    }

    public boolean percolates() {             // does the system percolate?

        return quickFind.connected(0, size1D + 1);
    }

    private int getFlatIndex(int row, int col) {

        return size * (row - 1) + col;

    }

    private void checkIndexes(int row, int col) {

        if (row <= 0 || col <= 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        if (row > size || col > size) {
            throw new java.lang.IndexOutOfBoundsException();
        }

    }
}