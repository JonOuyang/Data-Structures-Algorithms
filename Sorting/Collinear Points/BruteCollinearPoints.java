import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> segments = new ArrayList<>();
    private LineSegment[] finalSegments;
    private double[] slopes;
    private final Point reference = new Point(-1, -1);

    public BruteCollinearPoints(Point[] master) { // finds all line segments containing 4 points
        if (master == null || Arrays.asList(master).contains(null)) { throw new IllegalArgumentException(); }
        int N = master.length;

        // check for duplicate points
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++ ) {
                if (master[i].toString().equals(master[j].toString())) throw new IllegalArgumentException();
            }
        }

        Point[] points = Arrays.copyOf(master, master.length);
        slopes = new double[points.length-1];

        for (Point p : master) {
            Arrays.sort(points, p.slopeOrder());
//            System.out.println();
//            System.out.println("Reference Point: " + p);
//            System.out.println("Sorted Array: " + Arrays.toString(points));
            for (int i = 1; i < N; i++) {
                slopes[i-1] = p.slopeTo(points[i]);
            }

//            System.out.println("Slopes: " + Arrays.toString(slopes));

            int counter = 1;
            for (int left = 1; left < N-1; left++) {

                if (slopes[left] == slopes[left-1]) {
                    counter++;
                } else {
                    if (counter > 2) {
//                        System.out.println("counter limit hit call, counter = " + counter);
                        Point[] temp = new Point[counter+1];
                        for (int i = 0; i < temp.length-1; i++) {
                            temp[i] = points[left - counter + 1 + i];
                        }
                        temp[temp.length-1] = p;
                        addSegment(temp);
                    }
                    counter = 1;
                }

                if (left == N-2) {
                    if (counter > 2) {
//                        System.out.println("limit length hit call, counter = " + counter);
                        Point[] temp = new Point[counter+1];
                        for (int i = 0; i < temp.length-1; i++) {
                            temp[i] = points[left - counter + 2 + i];
                        }
                        temp[temp.length-1] = p;
                        addSegment(temp);
                    }
                }
            }
        }
    }

    private void addSegment(Point[] arg) {
//        System.out.println("arg: " + Arrays.toString(arg));
        Arrays.sort(arg, reference.slopeOrder());
        segments.add(new LineSegment(arg[0], arg[arg.length-1]));
//        System.out.println("Sorted Line Segment: " + Arrays.toString(arg));
    }

    public int numberOfSegments() { // the number of line segments
        segments();
        return finalSegments.length;
    }

    public LineSegment[] segments()  { // the line segments
        ArrayList<LineSegment> filter = new ArrayList<LineSegment>();
        for (LineSegment seg : segments) {
            boolean containsDupe = false;
            for (LineSegment i : filter) {
//                System.out.println(i.toString());
//                System.out.println(seg.toString());
                if (i.toString().equals(seg.toString())) {
                    containsDupe = true;
                }
            }
            if (!containsDupe) {
                filter.add(seg);
            }

        }
        finalSegments = new LineSegment[filter.size()];
        for (int i = 0; i < filter.size(); i++) {
            finalSegments[i] = filter.get(i);
        }
        return Arrays.copyOf(finalSegments, finalSegments.length);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
