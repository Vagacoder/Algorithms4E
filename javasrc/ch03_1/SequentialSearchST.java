package javasrc.ch03_1;

/*
* Algorithm 3.1 Sequential Search (in an unordered linked list) P. 375

* Proposition A. Search misses and insertions in an (unordered) linked-list symbol
table having N key-value pairs both require N compares, and search hits N com-
pares in the worst case.

* Corollary. Inserting N distinct keys into an initially empty linked-list symbol table
uses ~N 2/2 compares.

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

        for(Node x = first; x.next != null; x = x.next){
            if(x.next.key.equals(key)){
                Node result = x.next;
                x.next = result.next;
                result.next = null;
                this.size--;
                return result.value;
            }
        }

        return null;
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
    }

    public static void main(String[] args) {
        check();
    }
}