package javasrc.ch01_3;
/*
Exercise 1.3.30 Write a function that takes the first Node in a linked list as 
argument and (destructively) reverses the list, returning the first Node in 
the result.
*/
import lib.StdIn;
import lib.StdOut;

public class LinkedList<T>{

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

    public void add(T newItem){
        Node newNode = new Node();
        newNode.item = newItem;
        newNode.next = this.first;
        this.first = newNode;
        this.sizeOfList++;
    }

    public void print(){
        Node cursor = first;
        while(cursor != null){
            StdOut.println(cursor.item);
            cursor = cursor.next;
        }
    }

    public void reverse(){
        if (this.first == null){
            return;
        } else if (this.sizeOfList == 2){
            this.first.next.next = this.first;
            this.first = this.first.next;
            this.first.next.next = null;
        } else {
            Node cursor = this.first;
            while(cursor.next != null){
                Node temp = cursor.next;
                cursor.next = temp.next;
                temp.next = this.first;
                this.first = temp;
            }
        }
    }

    public static void main(String[] args) {
        LinkedList<String> l = new LinkedList<>();
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
                l.add(input);
        }

        StdOut.println("(" + l.size() + " in list)");
        l.print();
        l.reverse();
        l.print();
    }

}