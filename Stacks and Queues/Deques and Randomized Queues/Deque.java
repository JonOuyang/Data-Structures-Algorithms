import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first;

    private class Node {
        Item item;
        Node next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (first == null);
    }

    // return the number of items on the deque
    public int size() {
        int count = 0;
        if (first == null) { return 0; }
        Node current = first;
        while (current.next != null) {
            count++;
            current = current.next;
        }
        return count+1;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }

        if (isEmpty()) {
            first = new Node();
            first.item = item;
        } else {
            Node newFirst = new Node();
            newFirst.item = item;
            newFirst.next = first;
            first = newFirst;

        }

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }

        if (isEmpty()) {
            first = new Node();
            first.item = item;
        } else {
            Node current = first;
            while (current.next != null) {
                current = current.next;
            }
            Node last = new Node();
            last.item = item;
            current.next = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {

        if (isEmpty()) { throw new java.util.NoSuchElementException(); }
        Item ret = first.item;
        first = first.next;
        return ret;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) { throw new java.util.NoSuchElementException(); }

        if (first.next == null) {
            Item ret = first.item;
            first = null;
            return ret;
        }
        Node current = first;
        while (current.next.next != null) {
            current = current.next;
        }
        Item ret = current.next.item;
        current.next = null;
        return ret;
    }

    // return an iterator over items in order from front to back
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
        Deque<Integer> deq = new Deque<>();
        System.out.println(deq.isEmpty());

        deq.addFirst(1);
        deq.addFirst(2);
        deq.addFirst(3);

        for (Object item : deq) {
            System.out.print(item + " -> ");
        }
    }

}
