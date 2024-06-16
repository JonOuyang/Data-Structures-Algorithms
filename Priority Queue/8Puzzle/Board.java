import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {

    final private int n; // board dimensions
    private final int[][] board; //board

    public Board(int[][] tiles) {
        n = tiles.length;
        board = tiles;
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i< n-1; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != i*n+j+1) hamming++;
                }
            }
        for (int j = 0; j < n-1; j++) {
            if (board[n-1][j] != (n-1)*n+j+1) hamming++;
        }
        //if (board[n-1][n-1] != 0) hamming++;
        return hamming;
        }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i< n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != i*n+j+1 && board[i][j] != 0) {
                    // item is supposed to be in position[(x/n)][(x%n)-1]
                    int x = board[i][j];
                    if (x==0) x = n*n;
                    manhattan += Math.abs(i-(x-1)/n) + Math.abs(j-(x-1)%n);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        return toString().equals(y.toString());

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            public Iterator<Board> iterator() {
                return new boardIterator(Board.this);
            }
        };
    }

    private class boardIterator implements Iterator<Board> {

        private final Board iteratorBoard;
        private ArrayList<int[]> possibleMoves = new ArrayList<>(); //possible moves
        private int y;
        private int x;

        public boardIterator(Board b) {
            iteratorBoard = b;
            int dim = iteratorBoard.dimension();
            for (y = 0; y < dim; y++) {
                boolean found = false;
                for (x = 0; x < dim; x++) {
                    if (b.board[y][x] == 0) {
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }
//            System.out.println(y + ", " + x);
            if (y < b.dimension()-1) possibleMoves.add(new int[]{1, 0}); //down
            if (y > 0) possibleMoves.add(new int[]{-1, 0}); //up
            if (x < b.dimension()-1) possibleMoves.add(new int[]{0, 1}); //right
            if (x > 0) possibleMoves.add(new int[]{0, -1}); //left
        }

        public boolean hasNext() {
            return !possibleMoves.isEmpty();
        }

        public Board next() {
            if (!hasNext()) throw new NoSuchElementException();
            int dim = iteratorBoard.dimension();
            int[][] ret = new int[dim][dim];
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    ret[i][j] = iteratorBoard.board[i][j];
                }
            }

            //swap
            int temp = ret[y][x];
            ret[y][x] = ret[y + possibleMoves.get(0)[0]][x + possibleMoves.get(0)[1]];
            ret[y + possibleMoves.get(0)[0]][x + possibleMoves.get(0)[1]] = temp;
            possibleMoves.remove(0);
            return new Board(ret);
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int x;
        int y = 0;
        int dim = this.dimension();
        int[][] ret = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                ret[i][j] = this.board[i][j];
            }
        }
        if (ret[0][0] != 0 && ret[0][1] != 0) {
            x = 0;
        } else {
            x = 1;
        }
        //swap
        int temp = ret[x][0];
        ret[x][0] = ret[x][1];
        ret[x][1] = temp;
        return new Board(ret);
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}
