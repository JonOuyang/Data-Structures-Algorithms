import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;
    public Point(int x, int y) { // constructs the point (x, y)
        this.x = x;
        this.y = y;
    }

    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }                   // draws the line segment from this point to that point

    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) { // compare two points by y-coordinates, breaking ties by x-coordinates
        // Formally, the invoking point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
        if (this.y < that.y || ((this.y == that.y) && this.x < that.x)) return -1;
        else if (this.x == that.x && this.y == that.y) return 0;
        else return 1;
    }

    public double slopeTo(Point that) { // the slope between this point and that point
        // return the slope between the invoking point (x0, y0) and the argument point (x1, y1)
        if (that.x == this.x && that.y == this.y) {
            return Double.NEGATIVE_INFINITY;
        } else if (that.x == this.x) {
            return Double.POSITIVE_INFINITY;
        } else {
            double ret = ((double)that.y-this.y)/(that.x-this.x);
            if (ret == -0.0) return 0.0;
            else return ret;
        }
    }

    // public static final Comparator<Double> compareBySlope = new compareSlope();

    public Comparator<Point> slopeOrder() { // compare two points by slopes they make with this point
        // Formally, the point (x1, y1) is less than the point (x2, y2) if and only if
        // the slope (y1 − y0) / (x1 − x0) is less than the slope (y2 − y0) / (x2 − x0)
        return new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                double slope1 = slopeTo(p1);
                double slope2 = slopeTo(p2);
                return Double.compare(slope1, slope2);
            }
        };
    }

    public static void main(String[] args) {
        Point p = new Point(2, 9);
        Point q = new Point(2, 9);
        Point r = new Point(9, 4);
        System.out.println(p.slopeTo(q));
        System.out.println(p.slopeTo(r));
        System.out.println(-0.0 == 0.0);
    }

}
