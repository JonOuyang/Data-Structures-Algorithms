public class Percolation {

    private int[] grid;
    private int[] id;
    private int dim;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        dim = n;
        grid = new int[(int) (Math.pow(n, 2)+2)];
        grid[0] = 1;
        grid[grid.length-1] = 1;
        id = new int[(int) (Math.pow(n, 2)+2)];
        for (int i=0; i<id.length; i++) {
            id[i] = i;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > dim || col > dim) {
            throw new IllegalArgumentException();
        }
        grid[(row-1)*dim+col] = 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > dim || col > dim) {
            throw new IllegalArgumentException();
        }
        return grid[(row-1)*dim+col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > dim || col > dim) {
            throw new IllegalArgumentException();
        }
        percolates();
        return connected((row-1)*dim+col, 0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = -2;
        for (int i : grid) {
            if (i==1) {
                count++;
            }
        }
        return count;
    }

    // union find
    private int root(int i) {
        while (id[i] != i) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }
    private boolean connected(int p, int q) {
        return root(p) == root(q);
    }
    private void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        id[i] = j;
    }
    // does the system percolate?
    public boolean percolates() {
        for (int row = 1; row <= dim; row++) {
            for (int col = 1; col <= dim; col++) {
                if (isOpen(row, col) && id[(row-1)*dim+col] != (row-1)*dim+col) {
                    // grid[(row-1)*dim+col]
                    if (row<dim) {
                        if (isOpen(row+1, col)) {
                            union((row)*dim+col, (row-1)*dim+col);
                        }
                    }
                    if (row>1) {
                        if (isOpen(row-1, col)) {
                            union((row-2)*dim+col, (row-1)*dim+col);
                        }
                    }
                    if (col>1) {
                        if (isOpen(row, col-1)) {
                            union((row-1)*dim+(col-1), (row-1)*dim+col);
                        }
                    }
                    if (col<dim) {
                        if (isOpen(row, col+1)) {
                            union((row-1)*dim+(col+1), (row-1)*dim+col);
                        }
                    }
                }
            }
        }
        for (int col = 1; col <= dim; col++) {
            if (isOpen(1, col)) {union(col, 0);
            }
        }
        for (int col = 1; col <= dim; col++) {
            if (isOpen(dim, col)) {
                union((dim-1)*dim+col, id.length-1);
            }
        }
        return connected(0, id.length-1);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
