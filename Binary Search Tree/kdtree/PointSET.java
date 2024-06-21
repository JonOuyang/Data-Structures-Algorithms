import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Iterator;


public class PointSET {

    private SET<Point2D> set;

    public PointSET() { // construct an empty set of points
        set = new SET<>();
    }

    public boolean isEmpty() { // is the set empty?
        return set.size() == 0;
    }

    public int size() { // number of points in the set
        return set.size();
    }

    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException();
        set.add(p);
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        if (p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }

    public void draw() { // draw all points to standard draw

        for (Point2D point : set) {
            StdDraw.point(point.x(), point.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException();
        return new Iterable<Point2D>() {
            public Iterator<Point2D> iterator() {
                return new pointIterator(set, rect);
            }
        };
    }

    //Iterable
    private class pointIterator implements Iterator<Point2D> {

        private ArrayList<Point2D> tempList = new ArrayList<>();

        public pointIterator(SET<Point2D> points, RectHV range) {
            for (Point2D point : points) {
                if (range.contains(point)) tempList.add(point);
            }
        }

        public boolean hasNext() {
            return !tempList.isEmpty();
        }

        public Point2D next() {
            Point2D ret = tempList.get(0);
            tempList.remove(0);
            return ret;
        }
    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty
        if (set.isEmpty()) return null;
        if (p == null) throw new IllegalArgumentException();
        Point2D champion = set.max();
        double championLength = p.distanceTo(champion);
        for (Point2D point : set) {
            double length = p.distanceTo(point);
            if (length > championLength) {
                champion = point;
                championLength = p.distanceTo(point);
            }
        }
        return champion;
    }

    public static void main(String[] args) { // unit testing of the methods (optional)

    }
}
