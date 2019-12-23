package javasrc.ch01_3;

/*
1.3.40 Move-to-front. 
Read in a sequence of characters from standard input and maintain the characters 
in a linked list with no duplicates. When you read in a previously unseen character,
insert it at the front of the list. When you read in a duplicate character, delete 
it from the list and reinsert it at the beginning.

API:
boolean isEmpty(): is list empty
int size(): return the number of elements in list
void add(T newItem): add a new item into list
void print(): print all elements in list, from front to end
*/

import lib.StdOut;

public class MoveToFront<T> {

    private class Node {
        T item;
        Node next;
    }

    private Node first;

    public MoveToFront() {
        this.first = null;
    }

    public MoveToFront(T item) {
        this.first = new Node();
        this.first.item = item;
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public int size() {
        int size = 0;
        Node cursor = this.first;
        while (cursor != null) {
            size++;
            cursor = cursor.next;
        }
        return size;
    }

    public void print() {
        Node cursor = this.first;
        while (cursor != null) {
            StdOut.print(cursor.item.toString() + " ");
            cursor = cursor.next;
        }
        StdOut.println();
    }

    public void add(T newItem) {
        Node newNode = new Node();
        newNode.item = newItem;

        Node cursor = this.first;
        if (cursor != null && cursor.item.equals(newItem)) {
            return;
        }

        while (cursor != null && cursor.next != null) {
            if (cursor.next.item.equals(newItem)) {
                this.deleteAfter(cursor);
                this.addAtBeginning(newNode);
                return;
            }
            cursor = cursor.next;
        }
        this.addAtBeginning(newNode);
    }

    private void addAtBeginning(Node newNode) {
        if (this.first == null) {
            this.first = newNode;
            newNode.next = null;
        } else {
            newNode.next = this.first;
            this.first = newNode;
        }
    }

    private void deleteAfter(Node currentNode) {
        if (currentNode.next == null) {
            return;
        } else {
            Node temp = currentNode.next;
            currentNode.next = temp.next;
            temp.next = null;
        }
    }

    public static void main(String[] args) {
        MoveToFront<String> mf = new MoveToFront<>();
        mf.print();
        StdOut.println("size is: " + mf.size());
        mf.add("we");
        mf.print();
        StdOut.println("size is: " + mf.size());
        mf.add("are");
        mf.print();
        StdOut.println("size is: " + mf.size());
        mf.add("good");
        mf.print();
        StdOut.println("size is: " + mf.size());
        mf.add("we");
        mf.print();
        StdOut.println("size is: " + mf.size());
        mf.add("we");
        mf.print();
        StdOut.println("size is: " + mf.size());
    }
}