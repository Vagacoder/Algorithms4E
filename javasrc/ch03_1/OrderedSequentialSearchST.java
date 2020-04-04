package javasrc.ch03_1;

/*
* 3.1.3 Develop a symbol-table implementation OrderedSequentialSearchST that
uses an ordered linked list as the underlying data structure to implement our ordered
symbol-table API.

*/

import javasrc.ch01_3.LinkedListQueue;
import lib.*;

public class OrderedSequentialSearchST<Key extends Comparable<Key>, Value>{

    private class Node{
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

    // TODO: debug this method
    public void put(Key key, Value val){
        if(this.first == null){
            this.first = new Node(key, val, null);
            return;
        }

        for (Node cur = first; cur != null; cur = cur.next) {
            if (key.equals(cur.key)) {
                cur.value = val;
                return;
            }
            if(cur.next != null && cur.next.key.compareTo(key) > 0){
                Node newNode = new Node(key, val, cur.next);
                cur.next = newNode;
                this.size++;
                return;
            }
            if(cur.next == null){
                cur.next= new Node(key, val, null);
                return;
            }
        }
    }

    public Value get(Key key){
        for (Node cur = first; cur != null; cur = cur.next) {
            if (key.equals(cur.key)) {
                return cur.value;
            }
        }
        return null;
    }

    public void delete(Key key){
        if(this.first == null){
            return;
        }

        if(this.first.key.equals(key)){
            Node result = this.first;
            this.first = first.next;
            result.next = null;
            this.size--;
            return;
        }

        for(Node cur = first; cur.next != null; cur = cur.next){
            if(cur.next.key.equals(key)){
                Node result = cur.next;
                cur.next = result.next;
                result.next = null;
                this.size--;
                return;
            }
        }
    }
    
    public boolean contains(Key key){
        return false;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public int size(){
        return this.size;
    }

    public Key min(){
        return null;
    }

    public Key max(){
        return null;
    }

    public Key floor(Key key){
        return null;
    }

    public Key ceiling(Key key){
        return null;
    }

    public int rank(Key key){
        return -1;
    }

    public Key select(int k){
        Node cur = this.first;
        int count = 1;
        while(count < k){
            cur= cur.next;
            count++;
        }
        return cur.key;
    }

    public void deleteMin(){
        delete(min());
    }

    public void deleteMax(){
        delete(max());
    }

    public int size(Key low, Key high){
        if(high.compareTo(low)<0){
            return 0;
        } else if(contains(high)){
            return rank(high) - rank(low) + 1;
        } else{
            return rank(high) - rank(low);  // ! ??
        }
    }

    public Iterable<Key> keys(Key low, Key high){
        return null;
    }

    public Iterable<Key> keys(){
        LinkedListQueue<Key> ikeys = new LinkedListQueue<>();
        for(int i = 0; i<this.size; i++){
            ikeys.enqueue(this.select(i));
        }
        return ikeys;
    }

    public static void check(int minlen){

        StdOut.println("1. testing short string array ...");
        OrderedSequentialSearchST<String, Integer> st =  new OrderedSequentialSearchST<>();
        String[] strs = {"S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E"};
        for(int i = 0; i < strs.length; i++){
            st.put(strs[i], i);
        }
        for(String s: st.keys()){
            StdOut.println(s + " " + st.get(s));
        }

        // * from FrequencyCounter
        StdOut.println("2. testing using frequency counter ... ");
        st =  new OrderedSequentialSearchST<>();
        while(!StdIn.isEmpty()){
            String word = StdIn.readString();
            if(word.length() < minlen){
                continue;
            }
            if(!st.contains(word)){
                st.put(word,1);
            }else{
                st.put(word, st.get(word) + 1);
            }
        }
        String max ="";
        st.put(max, 0);
        for(String word: st.keys()){
            if(st.get(word)> st.get(max)){
                max = word;
            }
        }
        StdOut.println(max + " " + st.get(max));
    }

    public static void main(String[] args){
        check(1);
    }
}