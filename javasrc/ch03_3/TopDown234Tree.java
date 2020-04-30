package javasrc.ch03_3;

/*
* 3.3.25 Top-down 2-3-4 trees. Develop an implementation of the basic symbol-table
API that uses balanced 2-3-4 trees as the underlying data structure, using the red-black
representation and the insertion method described in the text:

! where 4-nodes are split by flipping colors on the way down the search path and 
! balancing on the way up.


* 3.3.26 Single top-down pass. Develop a modified version of your solution 
to Exercise 3.3.25 that does not use recursion. 

! Complete all the work splitting and balancing 4-nodes (and balancing 3-nodes) 
! on the way down the tree, finishing with an insertion at the bottom.

* 3.3.27 Allow right-leaning red links. Develop a modified version of your solution to
Exercise 3.3.25 that allows right-leaning red links in the tree.

*/

import lib.*;

public class TopDown234Tree<Key extends Comparable<Key>, Value>{

    // * inner class
    private class Node{
        Key key;
        Value value;
        Node left, right;
        int N;
        boolean color;

        Node(Key key, Value value, int N, boolean color){
            this.key = key;
            this.value = value;
            this.N = N;
            this.color = color;
        }
    }

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;
    
    private boolean isRed(Node x) {
        if (x == null) {
            return false;
        } else {
            return x.color == RED;
        }
    }

    private Node rotateLeft(Node h){
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    private Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    private void flipColor(Node h){
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public int size(Node x){
        if(x == null) {
            return 0;
        }else {
            return x.N;
        }
    }

    public Value get(Key key){
        return get(key, this.root);
    }

    private Value get(Key key, Node x){
        if(x == null){
            return null;
        }
        Value result = null;

        int cmp = key.compareTo(x.key);
        if(cmp < 0){
            result = get(key, x.left);
        }else if( cmp > 0){
            result = get(key, x.right);
        }else{
            return x.value;
        }
        return result;
    }

    // TODO:
    public void put(Key key, Value value){

    }

    // TODO
    private Node put(Node h, Key key, Value value){


        return h;
    }

    // TODO:
    // * 3.3.26
    public void putNoRecursive(Key key, Value value){

    }

    public static void main(String[] args){
    
    }

}