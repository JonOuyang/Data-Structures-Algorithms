import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node first;

    private class Node {
        Item item;
        Node next;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (first == null);
    }

    // return the number of items on the randomized queue
    public int size() {
        if (first == null) { return 0; }
        int count = 0;
        Node current = first;
        while (current.next != null) {
            count++;
            current = current.next;
        }
        return count + 1;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }

        if (isEmpty()) {
            first = new Node();
            first.item = item;
        } else {

            Node newFirst = new Node();
            newFirst.item = item;
            newFirst.next = first;
            first = newFirst;


//            Node current = first;
//            while (current.next != null) {
//                current = current.next;
//            }
//            Node last = new Node();
//            last.item = item;
//            current.next = last;
        }

    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) { throw new java.util.NoSuchElementException(); }
        Node current = first;

        if (StdRandom.bernoulli(1.0 / size())) {
            first = first.next;
            return current.item;
        }
        current = current.next;
        if (size() == 2) {
            Item ret = current.item;
            first.next = null;
            return ret;
        }

        while (current.next.next != null) {
            if (StdRandom.bernoulli(1.0 / size())) {
                Item ret = current.next.item;
                current.next = current.next.next;
                return ret;
            }
            current = current.next;
        }

        Item ret = current.next.item;
        current.next = null;
        return ret;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        Node current = first;
        while (true) {
            if (StdRandom.bernoulli(1.0 / size())) {
                return current.item;
            } else {
                if (current.next != null) {
                    current = current.next;
                } else {
                    current = first;
                }
            }
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (!hasNext()) { throw new java.util.NoSuchElementException(); }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> r = new RandomizedQueue<>();
        r.enqueue(1);
        r.enqueue(2);
        r.enqueue(3);
        for (int i = 0; i < 10; i++) {
            System.out.println(r.sample());
        }
    }
}
