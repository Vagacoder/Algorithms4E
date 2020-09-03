package javasrc.ch05_2;

import javasrc.ch01_3.LinkedListQueue;

/*
 * Algorithm 5.4 Trie Symbol Table. P.737
 * 
 * Proposition F. The linked structure (shape) of a trie is independent of the 
 * key insertion/deletion order: there is a unique trie for any given set of keys.
 * 
 * Proposition G. The number of array accesses when searching in a trie or inserting
 * a key into a trie is at most 1 plus the length of the key.
 * 
 * Proposition H. The average number of nodes examined for search miss in a trie
 * built from N random keys over an alphabet of size R is ~log R N .
 * 
 * Proposition i. The number of links in a trie is between RN and RNw, where w is
 * the average key length.
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
        if (d == key.length()){
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

    // * 3 methods for collecting keys
    public Iterable<String> keys(){
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String pre){
        LinkedListQueue<String> q = new LinkedListQueue<>();
        collect(get(root, pre, 0), pre, q);
        return q;
    }

    private void collect(Node x, String pre, LinkedListQueue<String> q){
        if(x == null){
            return;
        }
        if(x.val != null){
            q.enqueue(pre);
        }
        for(char c = 0; c < R; c++){
            collect(x.next[c], pre + c, q);
        }
    }

    // * Wildcard match
    public Iterable<String> keysThatMatch(String pat){
        LinkedListQueue<String> q = new LinkedListQueue<>();
        collect(root, "", pat, q);
        return q;
    }

    private void collect(Node x, String pre, String pat, LinkedListQueue q){
        int d = pre.length();
        if(x == null){
            return;
        }
        if(d == pat.length() && x.val != null){
            q.enqueue(pre);
        }

        // ! Note, we do not need consider keys longer than the pattern
        if(d == pat.length()){
            return;
        }

        char next = pat.charAt(d);
        for(char c = 0; c < R; c++){
            if(next == '.' || next == c){
                collect(x.next[c], pre + c, pat, q);
            }
        }
    }

    // * Longest prefix
    public String longestPrefixOf(String s){
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    private int search(Node x, String s, int d, int length){
        if(x == null) {
            return length;
        }
        if(x.val != null){
            length = d;
        }
        if(d == s.length()){
            return length;
        }
        char c = s.charAt(d);
        return search(x.next[c], s, d+1, length);
    }

    // * Delete
    public void delete(String key){
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d){
        if(x == null){
            return null;
        }
        if(d == key.length()){
            x.val = null;
        }else{
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d+1);
        }

        if(x.val != null){
            return x;
        }
        
        // * check if all links are null, if yes, return null for parent node checking
        for(char c = 0; c < R; c++){
            if(x.next[c] != null){
                return x;
            }
        }
        return null;
    }

    public static void main(String[] args){
        TrieST<Integer> trie = new TrieST<>();
        String[] strs = {"this", "is", "a", "good", "day", "to", "die"};

        for (int i = 0; i < strs.length; i++){
            trie.put(strs[i], i);
        }

        for (int i =0; i < strs.length; i++){
            StdOut.println(trie.get(strs[i]));
        }
    }
}