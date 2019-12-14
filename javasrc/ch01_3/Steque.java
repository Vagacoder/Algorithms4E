package javasrc.ch01_3;

/*
Excerise 1.3.32. 
Steque, or a stack-ended queue, is a data type that supports push, pop, and
enqueue. 

Develop a (Single of Double?) linked-list-based implementation. Articulate an API for this ADT.

isEmpty(): return a boolean showing whether the steque is empty

size(): return int of size of steque

enqueue(T newItem): add new item to the last Node

push(T newItem): add new item to the first Node

pop(): remove and return the first Node

peek(): return the value of the first Node

print(): print items of each Node, from first to last

implements interface <Iterable>

*/

import lib.StdOut;
import lib.StdIn;

public class Steque<T> {

    // try the single linked list
    private class Node {
        T item;
        Node next;
    }

    private Node first;
    private Node last;
    private int sizeOfSteque;

    public boolean isEmpty() {
        return this.sizeOfSteque == 0;
    }

    public int size() {
        return this.sizeOfSteque;
    }

    // enqueue() will add new node after the last
    public void enqueue(T newItem) {
        Node newNode = new Node();
        newNode.item = newItem;
        if (!this.isEmpty()) {
            this.last.next = newNode;
            this.last = newNode;
        } else {
            this.first = newNode;
            this.last = newNode;
        }
        this.sizeOfSteque++;
    }

    // push() add new node before the first
    public void push(T newItem) {
        Node newNode = new Node();
        newNode.item = newItem;
        if (!this.isEmpty()) {
            newNode.next = this.first;
            this.first = newNode;
        } else {
            this.first = newNode;
            this.last = newNode;
        }
        this.sizeOfSteque++;
    }

    // pop() remove first node and return the value of first node
    public T pop() {
        if (this.isEmpty()) {
            return null;
        }
        Node result = this.first;
        this.first = this.first.next;
        this.sizeOfSteque--;
        if (this.isEmpty()) {
            this.last = this.first;
        }
        result.next = null;
        return result.item;
    }

    // peek() return the value of first node
    public T peek() {
        return this.first.item;
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
        Steque<String> st = new Steque<>();

        StdOut.println("1. Testing the push and pop ....");
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            if (input.equals("-")) {
                StdOut.println(st.pop());
            } else {
                st.push(input);
            }
        }

        st.print();

        StdOut.println("2. Testing the enqueue ...");
        st.enqueue("We");
        st.enqueue("like");
        st.enqueue("computer");
        st.enqueue("science");
        st.print();
    }
}