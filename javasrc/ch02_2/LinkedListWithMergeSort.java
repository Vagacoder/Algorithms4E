package javasrc.ch02_2;

/*
* 2.2.17 Linked-list sort. 
Implement a natural mergesort for linked lists. (This is the method of choice for 
sorting linked lists because it uses no extra space and is guaranteed to be 
linearithmic.)

*/

/*
* 2.2.18 Shuffling a linked list. 
Develop and implement a divide-and-conquer algorithm that randomly shuffles a 
linked list in linearithmic time and logarithmic extra space.

*/

import lib.In;
import lib.StdIn;
import lib.StdOut;
import lib.StdRandom;

public class LinkedListWithMergeSort<T extends Comparable> {

    private class Node {
        T item;
        Node next;
    }

    private Node first;
    private int sizeOfList;

    public boolean isEmpty() {
        return this.first == null;
    }

    public int size() {
        return this.sizeOfList;
    }

    public void add(T item) {
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = this.first;
        this.first = newNode;
        this.sizeOfList++;
    }

    public void print() {
        Node cursor = first;
        while (cursor != null) {
            StdOut.print(cursor.item + " ");
            cursor = cursor.next;
        }
        StdOut.println();
    }

    public void sort() {
        if (this.first == null || this.first.next == null) {
            return;
        }

        boolean sorted = false;

        while (!sorted) {
            sorted = true;
            boolean secondHalf = false;
            Node cursor = this.first;
            Node low = cursor;
            Node mid = cursor;
            Node high = cursor;

            while (cursor.next != null) {
                // Search first half and second half
                if (less(cursor.next, cursor)) {
                    if (secondHalf) {
                        high = cursor;
                        secondHalf = false;
                    } else {
                        mid = cursor;
                        secondHalf = true;
                    }
                }

                // Handle special case: second half is at end of list
                if (secondHalf && cursor.next.next == null) {
                    high = cursor.next;
                    secondHalf = false;
                }

                cursor = cursor.next;
                // merge first and second halves
                if (high != low) {
                    merge(this.first, low, mid, high);
                    sorted = false;
                    low = cursor;
                    mid = cursor;
                    high = cursor;
                }
            }
        }
    }

    private void merge(Node first, Node low, Node mid, Node high) {
        Node cur = first;
        Node beforeLow = new Node();

        if (first != low) {
            while (cur.next != low) {
                cur = cur.next;
            }
            beforeLow = cur;
        }

        Node afterHigh = high.next;
        Node afterMid = mid.next;

        // break 2 halves from list, easy to determine borders
        mid.next = null;
        high.next = null;

        while (!(low == null && afterMid == null)) {
            // first half exhausted
            if (low == null) {
                beforeLow.next = afterMid;
                afterMid = afterMid.next;
            }
            // second half exhausted
            else if (afterMid == null) {
                beforeLow.next = low;
                low = low.next;
            }
            // compare 2 halves
            else if (less(afterMid, low)) {
                beforeLow.next = afterMid;
                afterMid = afterMid.next;
            } else {
                beforeLow.next = low;
                low = low.next;
            }

            // handle speical case: low is first
            if (beforeLow.item == null){
                this.first = beforeLow.next;
            }

            beforeLow = beforeLow.next;
        }

        // connect broken list
        beforeLow.next = afterHigh;
    }

    public void shuffle(){
        if (this.first == null || this.first.next == null) {
            return;
        }

        




    }

    private boolean less(Node v, Node w) {
        return v.item.compareTo(w.item) < 0;
    }

    // Test whether the array entries are in order.
    public boolean isSorted() {
        Node cur = this.first;
        while (cur.next != null) {
            if (!less(cur, cur.next)) {
                return false;
            }
            cur = cur.next;
        }

        return true;
    }

    public static void main(String[] args) {
        // Test 1: for short list
        // LinkedListWithMergeSort<Integer> l = new LinkedListWithMergeSort<>();
        // l.add(2);
        // l.add(4);
        // l.add(3);
        // l.add(1);
        // l.add(5);
        // StdOut.println("Test 1, Array of 5 elements ... \nOriginal Array:");
        // StdOut.println("(" + l.size() + " in list)");
        // StdOut.println("Sorted? " + l.isSorted());
        // l.print();

        // l.sort();
        // StdOut.println("\nAfter sorting\nSorted? " + l.isSorted());
        // l.print();

        // Test 2: for long list
        LinkedListWithMergeSort<Double> l = new LinkedListWithMergeSort<>();

        for(int i = 0; i < 100000; i++){
            l.add(StdRandom.uniform());
        }

        StdOut.println("Test 2, Array of many elements ... \nOriginal Array:");
        StdOut.println("(" + l.size() + " in list)");
        StdOut.println("Sorted? " + l.isSorted());
        // l.print();

        StdOut.println("sorting ...");
        l.sort();
        StdOut.println("\nAfter sorting\nSorted? " + l.isSorted());
        // l.print();
    }
}