package javasrc.ch05_2;

/*
 * Algorithm 5.4 Trie Symbol Table. P.737
 * 
 *  
 */

import lib.*;

public class TrieST<Value> {

    // ? Node class
    private static class Node {
        // ? The value in Node has to be an Object because Java does not support 
        // ? arrays of generics; we cast values back to Value in get().
        private Object val;
        private Node[] next = new Node[R];
    }

    // ? radix
    private static int R = 256;
    private Node root = new Node();

    public Value get(String key) {
        Node x = get(this.root, key, 0);
        if (x == null) {
            return null;
        }
        return (Value) x.val;
    }

    private Node get(Node x, String key, int d) {
        // ? return node associated with key in the subtrie rooted at x
        if (x == null) {
            return null;
        }
        if (d >= key.length()){
            return x;
        }
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

    public void put(String key, Value val){
        root = put(this.root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d){
        // ? Change value associated with key if in subtrie rooted at x
        if (x == null){
            x = new Node();
        }
        if ( d == key.length()){
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d+1);
        return x;
    }

    // ! Lazy implementation, SHOULD AVOID due to poor performance
    public int sizeLazy(){
        return sizeLazy(this.root);
    }

    private int sizeLazy(Node x){
        if(x == null){
            return 0;
        }
        int count = 0;
        if(x.val != null){
            count++;
        }
        for(char c= 0; c < R; c++){
            count += sizeLazy(x.next[c]);
        }
        return count;
    }


    public static void main(String[] args){

    }
}