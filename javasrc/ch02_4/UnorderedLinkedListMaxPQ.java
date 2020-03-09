package javasrc.ch02_4;

/*
* 2.4.3 Provide priority-queue implementations that support insert and remove the
maximum, for unordered linked list. 

Give a table of the worst-case bounds for each operation.

*/

import lib.*;

public class UnorderedLinkedListMaxPQ<Key extends Comparable<Key>> {

    private class Node {
        Key item;
        Node next;
    }

    private Node first;
    private int size;

    public UnorderedLinkedListMaxPQ(){
    }

    public boolean isEmpty(){
        return this.first == null;
    }

    public int size(){
        return this.size;
    }

    public Key delMax(){
        // * empty list
        if(this.first == null){
            return null;
        }
        
        // * 1 element list
        Node maxNode = this.first;
        if(this.first.next == null){
            this.first = null;
            this.size--;
            return maxNode.item;
        }

        // * other situations
        // ! maxNode is the PARENT of Node with max key (for easier deletion);
        Node parentMaxNode = null;

        Node cur = this.first;
        while(cur.next != null){
            if(less(maxNode.item, cur.next.item)){
                parentMaxNode = cur;
                maxNode = parentMaxNode.next;
            }
            cur = cur.next;
        }

        if(parentMaxNode == null){
            this.first = this.first.next;
            this.size--;
        } else{
            parentMaxNode.next = maxNode.next;
        }

        maxNode.next = null;
        return maxNode.item;
    }

    public void insert(Key newKey){
        Node newNode = new Node();
        newNode.item = newKey;
        newNode.next = this.first;
        this.first = newNode;
        this.size++;
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

    public static void main(String[] args){
        UnorderedLinkedListMaxPQ<String> pq = new UnorderedLinkedListMaxPQ<>();
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