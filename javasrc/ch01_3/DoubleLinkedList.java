package javasrc.ch01_3;

/*
Exercise 1.3.31
Implement a nested class DoubleNode for building doubly-linked lists.

Where each node contains a reference to the item preceding it and the item 
following it in thelist (null if there is no such item). 

Then implement static methods for the following tasks: 
    insert at the beginning, 
    insert at the end, 
    remove from the beginning, 
    remove from the end, 
    insert before a given node, 
    insert after a given node, 
    and remove a given node.
*/

import lib.StdOut;

import java.util.Iterator;

import lib.StdIn;

public class DoubleLinkedList<T> implements Iterable<T> {

    private class Node {
        T item;
        Node next;
        Node previous;
    }

    private Node first;
    private Node last;
    private int sizeOfList;

    public boolean isEmpty() {
        return this.sizeOfList == 0;
    }

    public int size() {
        return this.sizeOfList;
    }

    public void addBeginning(T newItem) {
        Node newNode = new Node();
        newNode.item = newItem;
        newNode.next = this.first;
        if (this.first == null) {
            this.first = newNode;
            this.last = newNode;
        }
        this.first.previous = newNode;
        this.first = newNode;
        this.sizeOfList++;
    }

    public void addEnd(T newItem) {
        Node newNode = new Node();
        newNode.item = newItem;
        if (this.first == null) {
            this.first = newNode;
            this.last = newNode;
        } else {
            this.last.next = newNode;
            newNode.previous = this.last;
            this.last = newNode;
        }
        this.sizeOfList++;
    }

    public T removeBeginning() {
        if (this.first == null) {
            return null;
        } else if (this.sizeOfList == 1) {
            T result = this.first.item;
            this.first = null;
            this.last = null;
            this.sizeOfList--;
            return result;
        } else {
            Node temp = this.first;
            this.first = this.first.next;
            this.first.previous = null;
            this.sizeOfList--;
            temp.next = null;
            T result = temp.item;
            temp = null;
            return result;
        }
    }

    public T removeEnd() {
        if (this.first == null) {
            return null;
        } else if (this.sizeOfList == 1) {
            T result = this.first.item;
            this.first = null;
            this.last = null;
            this.sizeOfList--;
            return result;
        } else {
            Node temp = this.last;
            this.last = this.last.previous;
            this.last.next = null;
            this.sizeOfList--;
            temp.previous = null;
            T result = temp.item;
            return result;
        }
    }

    public boolean insertBefore(T beforeThis, T newItem) {
        if (this.first == null) {
            return false;
        }

        Node cursor = this.first;
        while (cursor != null) {
            if (cursor.item.equals(beforeThis)) {
                Node newNode = new Node();
                newNode.item = newItem;
                if (cursor.previous != null) {
                    newNode.previous = cursor.previous;
                    cursor.previous.next = newNode;
                } else {
                    this.first = newNode;
                }
                newNode.next = cursor;
                cursor.previous = newNode;
                this.sizeOfList++;
                return true;
            }
            cursor = cursor.next;
        }
        return false;
    }

    public boolean insertAfter(T afterThis, T newItem) {
        if (this.first == null) {
            return false;
        }

        Node cursor = this.first;
        while (cursor != null) {
            if (cursor.item.equals(afterThis)) {
                Node newNode = new Node();
                newNode.item = newItem;
                if (cursor.next != null) {
                    newNode.next = cursor.next;
                    cursor.next.previous = newNode;
                } else {
                    this.last = newNode;
                }
                newNode.previous = cursor;
                cursor.next = newNode;
                this.sizeOfList++;
                return true;
            }
            cursor = cursor.next;
        }
        return false;
    }

    public boolean remove(T item) {
        if (this.first == null) {
            return false;
        }

        Node cursor = this.first;
        while (cursor != null) {
            if (cursor.item.equals(item)) {
                if (this.sizeOfList == 1) {
                    this.first = null;
                    this.last = null;
                    sizeOfList = 0;
                    cursor = null;
                    return true;
                } else if (cursor.previous == null) {
                    this.first = cursor.next;
                    this.sizeOfList--;
                    cursor.next = null;
                    return true;
                } else if (cursor.next == null) {
                    this.last = cursor.previous;
                    this.sizeOfList--;
                    cursor.previous = null;
                    return true;
                } else {
                    cursor.previous.next = cursor.next;
                    cursor.next.previous = cursor.previous;
                    this.sizeOfList--;
                    cursor.next = null;
                    cursor.previous = null;
                    return true;
                }
            }
            cursor = cursor.next;
        }
        return false;
    }


    @Override
    public Iterator<T> iterator() {
        return new LinkListIterator();
    }

    private class LinkListIterator implements Iterator<T>{

        private Node cursor = first;

        @Override
        public boolean hasNext() {
            return this.cursor != null;
        }

        @Override
        public T next() {
            T result = this.cursor.item;
            this.cursor = this.cursor.next;
            return result;
        }
    }

    public void print() {
        Node cursor = first;
        while (cursor != null) {
            StdOut.println(cursor.item);
            cursor = cursor.next;
        }
    }

    public static void main(String[] args) {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();

        // StdOut.println("1. Testing addBeginning and removeBeginning...");
        // while(!StdIn.isEmpty()){
        // String input = StdIn.readString();
        // if (!input.equals("-")){
        // list.addBeginning(input);
        // } else {
        // StdOut.println(list.removeBeginning());
        // }
        // }

        // StdOut.println("2. Testing addEnd and removeEnd...");
        // while (!StdIn.isEmpty()) {
        // String input = StdIn.readString();
        // if (!input.equals("-")) {
        // list.addEnd(input);
        // } else {
        // StdOut.println(list.removeEnd());
        // }
        // }

        // StdOut.println("3. Testing insertBefore and insertAfter...");
        // list.addBeginning("they");
        // list.addBeginning("are");
        // list.addBeginning("good");
        // list.addBeginning("men");
        // list.addBeginning("too");
        // list.insertBefore("good", "not");
        // list.insertBefore("they", "It");
        // list.insertAfter("men", "and");
        // list.insertAfter("too", ".");
        // list.print();

        StdOut.println("4. Testing insertBefore and insertAfter...");
        list.addEnd("they");
        list.addEnd("are");
        list.addEnd("good");
        list.addEnd("men");
        list.addEnd("too");
        list.insertBefore("good", "not");
        list.insertBefore("they", "It");
        list.insertAfter("men", "and");
        list.insertAfter("too", ".");
        list.print();
    }

}