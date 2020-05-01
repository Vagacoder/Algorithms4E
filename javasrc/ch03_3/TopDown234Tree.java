package javasrc.ch03_3;

/*
* 3.3.25 Top-down 2-3-4 trees. Develop an implementation of the basic symbol-table
API that uses balanced 2-3-4 trees as the underlying data structure, using the red-black
representation and the insertion method described in the text:

! where 4-nodes are split by flipping colors on the way down the search path and 
! balancing on the way up.
? Check textbook P.441, left panel for all cases


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

    public void put(Key key, Value value){
        root = put(root, key, value);
        root.color = BLACK;
    }

    private Node put(Node h, Key key, Value value){

        if(h == null){
            return new Node(key, value, 1, RED);
        }
        // ? Cases are list on P.441, left panel, from top to bottom
        // ? case 1, at the root
        if(h == this.root && isRed(h.left) && isRed(h.right)){
            h.left.color = BLACK;
            h.right.color = BLACK;
        }

        int cmp = key.compareTo(h.key);
        if(cmp < 0){
            if(is4Node(h.left)){
                // ? case 2
                flipColor(h.left);
            }else if(is3Node(h) && h.left!=null && key.compareTo(h.left.key) <0){
                if(is4Node(h.left.left)){
                    // ? case4
                    flipColor(h.left.left);
                    // Node x = h.left;
                    // h.left = x.right;
                    // x.right = h;
                    // x.color = h.color;
                    // h.color = RED;
                    // x.N = h.N;
                    // h.N = 1 + size(h.left) + size(h.right);
                    // h = x;
                    h = rotateRight(h);
                }
            }else if(is3Node(h) && h.left!=null && key.compareTo(h.left.key) >0){
                if(is4Node(h.left.right)){
                    // ? case 5
                    flipColor(h.left.right);
                    Node x = h.left.right;
                    h.left.right = x.left;
                    x.left = h.left;
                    h.left = x.right;
                    x.right = h;
                    x.color = h.color;
                    h.color = RED;
                    x.N = h.N;
                    h.N = 1 + size(h.left) + size(h.right);
                    x.left.N = 1 + size(x.left.left) + size(x.left.right);
                    h = x;
                }
            }
            h.left = put(h.left, key, value);
        } else if (cmp > 0){
            if(is2Node(h) && is4Node(h.right)){
                // ? case 3
                flipColor(h.right);
                // Node x = h.right;
                // h.right = x.left;
                // x.left = h;
                // x.N = h.N;
                // h.N = 1 + size(h.left) + size(h.right);
                // x.color = h.color;
                // h.color = RED;
                // h = x;
                h = rotateLeft(h);
            }else if (is3Node(h)&& is4Node(h.right)){
                // ? case 6
                flipColor(h.right);
            }
            h.right = put(h.right, key, value);
        } else{
            h.value = value;
        }

        // * check 3-node
        if (isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }

        // * check 4-node
        if (isRed(h.left) && isRed(h.left.left) && !isRed(h.right)) {
            h = rotateRight(h);
        }
        h.N = 1 + size(h.left) + size(h.right);
        return h;
    }

    // TODO:
    // * 3.3.26
    public void putNoRecursive(Key key, Value value){
        Node cur = this.root;
        if(cur == null){
            this.root = new Node(key, value, 1, BLACK);
        }

        while (cur.left != null && cur.right != null){

        }


    }

    private boolean is4Node(Node h){
        if(h == null){
            return false;
        }
        return isRed(h.left) && isRed(h.right);
    }

    private boolean is3Node(Node h){
        if(h == null){
            return false;
        }
        return !isRed(h.right) && isRed(h.left);
    }

    private boolean is2Node(Node h){
        if(h == null){
            return false;
        }
        return !isRed(h.left) && !isRed(h.right);
    }

    public int pureHeight() {
        return pureHeight(root);
    }

    private int pureHeight(Node x) {
        if (x == null)
            return -1;
        return 1 + Math.max(pureHeight(x.left), pureHeight(x.right));
    }

    public void print() {
        print(this.root);
    }

    private void print(Node x) {
        if (x == null) {
            return;
        }
        print(x.left);
        StdOut.println(x.key + " : " + x.value + " : " + (x.color ? "RED" : "BLACK") + " : " + x.N);
        print(x.right);
    }

    public static void check() {
        TopDown234Tree<String, Integer> t234 = new TopDown234Tree<>();
        String[] strs = {"S", "E", "A", "R", "C", "H", "X", "M", "P", "L"};
        for(int i =0; i< strs.length;i++){
            t234.put(strs[i], i);
        }
        t234.print();
        StdOut.println(t234.get("R"));
        StdOut.println(t234.get("Z"));
        StdOut.println("All height: " + t234.pureHeight());
    }

    public static void main(String[] args){
        check();
    }

}