package javasrc.ch01_3;

import lib.StdIn;
import lib.StdOut;

public class CircularLinkedListQueue<T>{

    private class Node{
        T item;
        Node next;
    }

    private Node first;
    private Node last;
    private int sizeOfQueue;

    public boolean isEmpty(){
        return this.first == null;
    }

    public int size(){
        return this.sizeOfQueue;
    }

    public void enqueue(T newItem){
        Node newNode = new Node();
        newNode.item = newItem;
        if(!this.isEmpty()){
            this.last.next = newNode;
            this.last = newNode;
            this.last.next = this.first;
        }else {
            this.first = newNode;
            this.last = newNode;
            newNode.next = this.first;
        }
        this.sizeOfQueue++;
    }

    public T dequeue(){
        if(this.isEmpty()){
            return null;
        } else if(this.sizeOfQueue == 1){
            Node result = this.first;
            this.first = null;
            this.last = null;
            this.sizeOfQueue--;
            return result.item;
        } else {
            Node result = this.first;   
            this.first = this.first.next;
            this.last = this.first;
            this.sizeOfQueue--;
            return result.item;
        }
    }

    public static void main(String[] args) {

        LinkedListQueue<String> q = new LinkedListQueue<>();

        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            if (!input.equals("-")) {
                q.enqueue(input);
            } else {
                if (q.isEmpty()) {
                    StdOut.println("(Queue is empty)");
                } else {
                    StdOut.println(q.dequeue());
                }
            }
        }
        StdOut.println("(" + q.size() + " left in Queue)");
    }

}