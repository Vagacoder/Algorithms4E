package javasrc.ch03_3;

/*
* Algorithm 3.4 Red-Black BST P.439

! Remarkably, you can implement top-down 2-3-4 trees by moving one line of code in
put() in Algorithm 3.4: move the colorFlip() call (and accompanying test) to be-
fore the recursive calls (between the test for null and the comparison). 
*/

import lib.*;

public class RedBlackBST<Key extends Comparable<Key>, Value> {

    // * inner class of Node
    private class Node {
        Key key;
        Value value;
        Node left, right;
        int N;
        boolean color;

        Node (Key key, Value value, int N, boolean color){
            this.key = key;
            this.value = value;
            this.N = N;
            this.color = color;
        }
    }

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;
    
    private boolean isRed(Node x){
        if(x == null){
            return false;
        }else{
            return x.color == RED;
        }
    }

    // * one of 3 major operations
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

    // * one of 3 major operations
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

    // * one of 3 major operations
    private void flipColor(Node h){
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public int size(Node x){
        if(x == null){
            return 0;
        }else {
            return x.N;
        }
    }

    public Value get(Key key){
        return get(key, this.root);
    }

    private Value get(Key key, Node node){
        Value result = null;
        if(node == null){
            return result;
        }

        int cmp = key.compareTo(node.key);
        if(cmp < 0){
            result = get(key, node.left);
        } else if(cmp > 0){
            result = get(key, node.right);
        } else{
            result = node.value;
        }
        return result;
    }

    public void put(Key key, Value value){
        root = put(root, key, value);
        root.color = BLACK;
    }

    private Node put(Node h, Key key, Value value){
        // ! whenever insert a new Noed, it is always RED!
        if(h == null){
            return new Node(key, value, 1, RED);
        }

        int cmp = key.compareTo(h.key);
        if(cmp < 0){
            h.left = put(h.left, key, value);
        } else if (cmp > 0){
            h.right = put(h.right, key, value);
        }else{
            h.value = value;
        }

        // ! Red-Black BST specific operations
        if (isRed(h.right) && !isRed(h.left)){
            h = rotateLeft(h);
        }
        if (isRed(h.left)&& isRed(h.left.left)){
            h = rotateRight(h);
        }
        if(isRed(h.left) && isRed(h.right)){
            flipColor(h);
        }

        h.N = 1 + size(h.left) + size(h.right);
        return h;
    }


    public void print(){
        print(this.root);
    }

    private void print(Node x){
        if(x == null){
            return;
        }
        print(x.left);
        StdOut.println(x.key + " : " + x.value + " : " + x.N);
        print(x.right);
    }

    public static void check(){
        RedBlackBST<String, Integer> rb = new RedBlackBST<>();
        String[] strs = {"S", "E", "A", "R", "C", "H", "X", "M", "P", "L"};
        for(int i =0; i< strs.length;i++){
            rb.put(strs[i], i);
        }
        rb.print();
        StdOut.println(rb.get("R"));
        StdOut.println(rb.get("Z"));
    }

    public static void main(String[] args){
        check();
    }
}