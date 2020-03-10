package javasrc.ch02_4;

/* 
* 2.4.3 Provide priority-queue implementations that support insert and remove the
maximum, for ordered linked list. 

Give a table of the worst-case bounds for each operation.
* insert: O(n)
* delMax: O(1) for compare
*/

import lib.*;

public class OrderedLinkedListMaxPQ<Key extends Comparable<Key>> {

    private class Node {
        Key item;
        Node next;
    }

    private Node first;
    private int size;

    public OrderedLinkedListMaxPQ() {
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public int size() {
        return this.size;
    }

    public void insert(Key newKey) {
        Node newNode = new Node();
        newNode.item = newKey;

        // * empty list
        if (this.first == null) {
            this.first = newNode;
            this.size++;
            return;
        }

        // * 1 element list
        if (this.first.next == null) {
            if (less(this.first.item, newKey)) {
                newNode.next = this.first;
                this.first = newNode;
            } else {
                this.first.next = newNode;
            }
            this.size++;
            return;
        }

        // * other situations
        Node parentCur = null;
        Node cur = this.first;

        while (cur != null && less(newNode.item, cur.item)) {
            parentCur = cur;
            cur = cur.next;
        }

        if (parentCur == null) {
            newNode.next = this.first;
            this.first = newNode;
        } else if (parentCur.next == null) {
            parentCur.next = newNode;
        } else {
            newNode.next = parentCur.next;
            parentCur.next = newNode;
        }
        this.size++;

    }

    public Key delMax() {
        // * empty list
        if (this.first == null) {
            return null;
        }

        // * other situations
        Node maxNode = this.first;
        this.first = this.first.next;
        maxNode.next = null;
        this.size--;
        return maxNode.item;
    }

    public void print() {
        Node cur = this.first;
        while (cur != null) {
            StdOut.println(cur.item.toString());
            cur = cur.next;
        }
    }

    private boolean less(Key v, Key w) {
        return v.compareTo(w) < 0;
    }

    public static void main(String[] args) {
        OrderedLinkedListMaxPQ<String> pq = new OrderedLinkedListMaxPQ<>();
        StdOut.println("1. Testing delete empty pq ... ");
        StdOut.println("printing list ... ");
        pq.print();
        StdOut.println("deleting ... ");
        StdOut.println(pq.delMax());
        StdOut.println();

        StdOut.println("2. Testing delete 1 element pq ... ");
        pq.insert("this");
        StdOut.println("printing list before ... ");
        pq.print();
        StdOut.println("deleting ... ");
        StdOut.println(pq.delMax());
        StdOut.println("printing list after ... ");
        pq.print();
        StdOut.println();

        StdOut.println("3. Testing delete 2 elements pq ... ");
        pq.insert("this");
        pq.insert("is");
        StdOut.println("printing list before ... ");
        pq.print();
        StdOut.println("deleting ... ");
        StdOut.println(pq.delMax());
        StdOut.println("printing list after ... ");
        pq.print();
        StdOut.println();

        StdOut.println("4. Testing delete 2 elements pq reversed order... ");
        pq.insert("this");
        StdOut.println("printing list before ... ");
        pq.print();
        StdOut.println("deleting ... ");
        StdOut.println(pq.delMax());
        StdOut.println("printing list after ... ");
        pq.print();
        StdOut.println();

        StdOut.println("5. Testing delete multiple elements pq ... ");
        pq.insert("this");
        pq.insert("a");
        pq.insert("interesting");
        pq.insert("test");
        pq.insert("for");
        pq.insert("unsorted");
        pq.insert("linked");
        pq.insert("list");
        pq.insert("priority");
        pq.insert("queue");

        StdOut.println("printing list before ... ");
        pq.print();
        StdOut.println("deleting ... ");
        StdOut.println(pq.delMax());
        StdOut.println("printing list after ... ");
        pq.print();
        StdOut.println("deleting ... ");
        StdOut.println(pq.delMax());
        StdOut.println("printing list after ... ");
        pq.print();
        StdOut.println("deleting ... ");
        StdOut.println(pq.delMax());
        StdOut.println("printing list after ... ");
        pq.print();
        StdOut.println("deleting ... ");
        StdOut.println(pq.delMax());
        StdOut.println("printing list after ... ");
        pq.print();
        StdOut.println();
    }
}