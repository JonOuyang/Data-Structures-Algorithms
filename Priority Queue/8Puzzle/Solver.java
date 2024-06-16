import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Solver {

    private final Board root;
    private int moves;
    private ArrayList<Board> solutionBoards;
    private final boolean solveable;
    private Node sol;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        root = initial;
        solutionBoards = new ArrayList<>();
        solveable = solve();
        moves = sol.getMove();
        if (solveable) {
            while (sol.getPrevious()!=null) {
                solutionBoards.add(sol.getBoard());
                sol = sol.getPrevious();
            }
            solutionBoards.add(sol.getBoard());
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solveable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        else return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        else return new Iterable<Board>() {
            public Iterator<Board> iterator() {
                return new solutionIterator();
            }
        };
    }

    private class solutionIterator implements Iterator<Board> {

//        public int getMoves() { return solutionBoards.size()-1; }

        public boolean hasNext() {
            return !solutionBoards.isEmpty();
        }

        public Board next() {
            if (!hasNext()) throw new NoSuchElementException();
            Board ret = solutionBoards.get(solutionBoards.size()-1);
            solutionBoards.remove(solutionBoards.size()-1);
            return ret;
        }
    }

 private boolean solve() {
        MinPQ<Node> tree = new MinPQ<>(new BoardComparator());
        MinPQ<Node> parallelTree = new MinPQ<>(new BoardComparator());

        tree.insert(new Node(null, root, 0));
        parallelTree.insert(new Node(null, root.twin(), 0));

        Node deque = tree.delMin();
        Node parallelDeque = parallelTree.delMin();

        for (Board n : deque.getBoard().neighbors()) {
            tree.insert(new Node(deque, n, deque.getMove()+1));
        }
        for (Board n : parallelDeque.getBoard().neighbors()) {
            parallelTree.insert(new Node(parallelDeque, n, parallelDeque.getMove()+1));
        }

        while (!deque.getBoard().isGoal()) {
            deque = tree.delMin();
            parallelDeque = parallelTree.delMin();

            //check if solved
            if (parallelDeque.getBoard().isGoal()) {
                return false;
            }

            for (Board n : deque.getBoard().neighbors()) {
                if (!n.equals(deque.getPrevious().getBoard())) tree.insert(new Node(deque, n, deque.getMove()+1));
            }
            for (Board n : parallelDeque.getBoard().neighbors()) {
                if (!n.equals(parallelDeque.getPrevious().getBoard())) parallelTree.insert(new Node(parallelDeque, n, parallelDeque.getMove()+1));
            }
        }
        sol = deque;
        return true;
    }

    private class Node {
        private final Board current;
        private final int move;
        private final Node previous;

        public Node(Node prev, Board b, int m) {
            previous = prev;
            current = b;
            move = m;
        }

        public Board getBoard() { return current; }
        public int getMove() { return move; }
        public Node getPrevious() { return previous; }
    }

    private class BoardComparator implements Comparator<Node> {
        @Override
        public int compare(Node board1, Node board2) {
            int priority1 = board1.getMove() + board1.getBoard().manhattan();
            int priority2 = board2.getMove() + board2.getBoard().manhattan();
            return Integer.compare(priority1, priority2);
        }
    }

    // test client (see below)
    public static void main(String[] args) {
//        int[][] b = {{1, 2, 3}, {5, 8, 0}, {4, 6, 7}};
//        int[][] b = {{1, 2, 0}, {4, 8, 3}, {7, 6, 5}};
//        int[][] b = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
//        int[][] b = {{0, 3}, {2, 1}};
//        int[][] b = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 0}, {13, 14, 15, 12}};
//        Board bo = new Board(b);
//        Solver solver = new Solver(bo);
//        if (!solver.isSolvable())
//            StdOut.println("No solution possible");
//        else {
//            StdOut.println("Minimum number of moves = " + solver.moves());
//            for (Board board : solver.solution())
//                StdOut.println(board);
//        }

        //Given code
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
