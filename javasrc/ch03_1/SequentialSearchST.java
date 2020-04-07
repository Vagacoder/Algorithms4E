package javasrc.ch03_1;

import javasrc.ch01_3.LinkedListQueue;

/*
* Algorithm 3.1 Sequential Search Symbol Table (in an unordered linked list) P. 375

* Proposition A. Search misses and insertions in an (unordered) linked-list symbol
table having N key-value pairs both require N compares, and search hits N com-
pares in the worst case.

* Corollary. Inserting N distinct keys into an initially empty linked-list symbol table
uses ~N 2/2 compares.

* method    O()
! put()     N   (worst N)      this one is too slow for large dataset
! get()     N/2 (worst N)

* 3.1.5 Implement size(), delete(), and keys() for SequentialSearchST.

*/

import lib.*;

public class SequentialSearchST<Key, Value> {

    private class Node {
        Key key;
        Value value;
        Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Node first;
    private int size;

    public int size(){
        return this.size;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public Value get(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return x.value;
            }
        }
        return null;
    }

    public void put(Key key, Value value) {
        for (Node x = first; x != null; x = x.next) {
            if(key.equals(x.key)){
                x.value = value;
                return;
            }
        }
        this.first = new Node(key, value, this.first);
        this.size++;
    }

    // * 3.1.5 
    public Value delete(Key key){
        if(this.first == null){
            return null;
        }

        if(this.first.key.equals(key)){
            Node result = this.first;
            this.first = first.next;
            result.next = null;
            this.size--;
            return result.value;
        }

        for(Node cur = first; cur.next != null; cur = cur.next){
            if(cur.next.key.equals(key)){
                Node result = cur.next;
                cur.next = result.next;
                result.next = null;
                this.size--;
                return result.value;
            }
        }

        return null;
    }

    // * 3.1.5 
    public Iterable<Key> keys(){
        LinkedListQueue<Key> lq = new LinkedListQueue<>();
        Node cur = this.first;
        while(cur != null){
            lq.enqueue(cur.key);
            cur = cur.next;
        }
        return lq;
    }

    public void print(){
        Node cur = this.first;
        while(cur != null){
            StdOut.println("(" + cur.key + " : " + cur.value + ")");
            cur = cur.next;
        }
    }

    public static void check(){
        SequentialSearchST<String, Integer> st = new SequentialSearchST<>();
        st.put("S", 0);
        st.put("E", 1);
        st.put("A", 2);
        st.put("R", 3);
        st.put("C", 4);
        st.put("H", 5);
        st.put("E", 6);
        st.put("X", 7);
        st.put("A", 8);
        st.put("M", 9);
        st.put("P", 10);
        st.put("L", 11);
        st.put("E", 12);
        st.print();
        for(String key : st.keys()){
            StdOut.println(key + " : " + st.get(key));
        }
    }

    public static void main(String[] args) {
        check();
    }
}