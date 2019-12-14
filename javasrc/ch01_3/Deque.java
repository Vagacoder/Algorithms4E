package javasrc.ch01_3;

/*
Excercise 1.3.33. I

Deque, a double-ended queue, (pronounced "deck") is like a stack or
a queue but supports adding and removing items at both ends.

This class implements Deque using Double Linked List

boolean isEmpty(): is the deque empty?

int size(): number of items in the deque

pushLeft(T item): add an item to the first

pushRight(T item): add an item to the last

popLeft(): remove an item from the first

popRight(): remove an item from the last

print(): print values of all nodes in Deque.

*/

import lib.StdOut;
import lib.StdIn;

public class Deque<T> {

    private class Node {
        T item;
        Node next;
        Node previous;
    }

    private Node first;
    private Node last;
    private int sizeOfDeque;

    public boolean isEmpty() {
        return this.sizeOfDeque == 0;
    }

    public int size() {
        return this.sizeOfDeque;
    }

    // add an item to the first
    public void pushLeft(T newItem) {
        Node newNode = new Node();
        newNode.item = newItem;
        if (this.isEmpty()) {
            this.first = newNode;
            this.last = newNode;
        } else {
            newNode.next = this.first;
            this.first.previous = newNode;
            this.first = newNode;
        }
        this.sizeOfDeque++;
    }

    // remove an item from the first
    public T popLeft() {
        if (this.isEmpty()) {
            return null;
        }
        Node result = this.first;
        this.first = this.first.next;
        if (this.last == result) {
            this.last = null;
        } else {
            this.first.previous = null;
        }
        this.sizeOfDeque--;
        result.next = null;
        return result.item;
    }

    // add an item to the last
    public void pushRight(T newItem) {
        Node newNode = new Node();
        newNode.item = newItem;
        if (this.isEmpty()) {
            this.first = newNode;
            this.last = newNode;
        } else {
            this.last.next = newNode;
            newNode.previous = this.last;
            this.last = newNode;
        }
        this.sizeOfDeque++;
    }

    // remove an item from the last
    public T popRight() {
        if (this.isEmpty()) {
            return null;
        }

        Node result = this.last;
        this.last = this.last.previous;
        if (this.first == result) {
            this.first = null;
        } else {
            this.last.next = null;
        }
        this.sizeOfDeque--;
        result.previous = null;
        return result.item;
    }

    // print the values of all nodes from first to last
    public void print() {
        Node cursor = first;
        while (cursor != null) {
            StdOut.println(cursor.item.toString());
            cursor = cursor.next;
        }
    }

    public static void main(String[] args) {

        Deque<String> d = new Deque<>();
        StdOut.println("1. Testing the pop and push left ...");
        d.pushLeft("They");
        d.pushLeft("are");
        d.pushLeft("good");
        d.pushLeft("men");
        d.pushLeft("too");
        d.print();
        StdOut.println(d.popLeft().toString());
        StdOut.println(d.popLeft().toString());
        StdOut.println(d.popLeft().toString());
        StdOut.println(d.popLeft().toString());
        StdOut.println(d.popLeft().toString());
        d.print();

        StdOut.println("2. Testing the pop and push right ...");
        d.pushRight("They");
        d.pushRight("are");
        d.pushRight("good");
        d.pushRight("men");
        d.pushRight("too");
        d.print();
        StdOut.println(d.popRight().toString());
        StdOut.println(d.popRight().toString());
        StdOut.println(d.popRight().toString());
        StdOut.println(d.popRight().toString());
        StdOut.println(d.popRight().toString());
        d.print();
    }

}