package javasrc.ch05_2;

/*
 * Algorithm 5.5 TST (Ternary Search Trie) Symbol Table. P.747 
 * 
 * Proposition J. The number of links in a TST built from N string keys of average
 * length w is between 3N and 3Nw.
 * 
 * Proposition K. A search miss in a TST built from N random string keys requires
 * ~ln N character compares, on the average. A search hit or an insertion in a 
 * TST uses ~ln N + L character compares, where L is the length of the search key.
 * 
 * Proposition L. A search or an insertion in a TST built from N random string 
 * keys with no external one-way branching and R t-way branching at the root 
 * requires roughly lnN - t*lnR character compares, on the average.
 * 
 */

import lib.*;

public class TST<Value> {

    private class Node {
        char c;
        Node left, mid, right;
        Value val;
    }

    private Node root;

    public Value get(String key) {
        Node x = get(this.root, key, 0);
        if (x == null) {
            return null;
        }
        return (Value) x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        char c = key.charAt(d);
        if (c < x.c) {
            return get(x.left, key, d);
        } else if (c > x.c) {
            return get(x.right, key, d);
        } else if (d < key.length() - 1) {
            return get(x.mid, key, d + 1);
        } else {
            return x;
        }
    }

    public void put(String key, Value val){
        this.root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d){
        char c = key.charAt(d);
        if(x == null){
            x = new Node();
            x.c = c;
        }
        if (c < x.c){
            x.left = put(x.left, key, val, d);
        } else if( c > x.c){
            x.right = put(x.right, key, val, d);
        } else if (d < key.length() - 1){
            x.mid = put(x.mid, key, val, d+1);
        } else {
            x.val = val;
        }
        return x;
    }

    public static void main(String[] args){
        TST<Integer> tst = new TST<>();
        String[] strs = {"this", "is", "a", "good", "day", "to", "die"};

        for (int i = 0; i < strs.length; i++){
            tst.put(strs[i], i);
        }

        for (int i =0; i < strs.length; i++){
            StdOut.println(tst.get(strs[i]));
        }

    }
}
