package javasrc.ch03_3;

/*
* 3.3.23 2-3 trees without balance restriction. Develop an implementation of the basic
symbol-table API that uses 2-3 trees that are not necessarily balanced as the underlying
data structure. Allow 3-nodes to lean either way. Hook the new node onto the bottom
with a black link when inserting into a 3-node at the bottom. Run experiments to de-
velop a hypothesis estimating the average path length in a tree built from N random
insertions.

*/

import lib.*;

public class RedBlackBSTless<Key extends Comparable<Key>, Value>{

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

    private boolean isRed(Node x){
        if(x == null){
            return false;
        }else{
            return x.color == RED;
        }
    }

    // ! Below are 4 reform operations
    // ! all reforms' result is:
    // !        O
    // !    red/ \red
    // !      O   O
    // !     / \ / \

    // ! one of major 4 operations
    // ?         h             i
    // ?     red/ \        red/ \red
    // ?      i        =>    j   h
    // ?  red/ \            / \ / \
    // ?    j
    private Node reformLeftLeft(Node h){
        Node i = h.left;
        // Node j = i.left;

        h.left = i.right;
        i.right = h;
        i.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        i.color = h.color;
        h.color = RED;
        return i;
    }

    // ! one of major 4 operations
    // ?        h             j
    // ?    red/ \        red/ \red
    // ?      i       =>    i   h
    // ?     / \red        / \ / \
    // ?       j
    private Node reformLeftRight(Node h){
        Node i = h.left;
        Node j = i.right;

        i.right = j.left;
        h.left = j.right;
        j.left = i;
        j.right = h;
        j.N = h.N;
        i.N = 1 + size(i.left) + size(i.right);
        h.N = 1 + size(h.left) + size(h.right);
        j.color = h.color;
        i.color = RED;
        h.color = RED;
        return j;
    }

    // ! one of major 4 operations
    // ?        h               j
    // ?       / \red       red/ \red
    // ?         i      =>    h   i
    // ?     red/ \          / \ / \
    // ?       j
    private Node reformRightLeft(Node h){
        Node i = h.right;
        Node j = i.left;

        h.right = j.left;
        i.left = j.right;
        j.left = h;
        j.right = i;
        j.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        i.N = 1 + size(i.left) + size(i.right);
        j.color = h.color;
        h.color = RED;
        i.color = RED;
        return j;
    }

    // ! one of major 4 operations
    // ?        h               i
    // ?       / \red       red/ \red
    // ?         i      =>    h   j
    // ?        / \red       / \ / \
    // ?          j
    private Node reformRightRight(Node h){
        Node i = h.right;
        // Node j = i.right;

        h.right = i.left;
        i.left = h;
        i.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        i.color = h.color;
        h.color = RED;
        return i;
    }

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
        root = put(root, key, value, BLACK);
        root.color = BLACK;
    }

    private Node put(Node h, Key key, Value value, boolean color){
        if(h == null){
            return new Node(key, value, 1, RED);
        }

        int cmp = key.compareTo(h.key);
        if(cmp < 0){
            h.left = put(h.left, key, value, h.color);
        }else if (cmp > 0){
            h.right = put(h.right, key, value, h.color);
        }else{
            h.value = value;
        }

        if(isRed(h.left)){
            if(isRed(h.left.left)){
                h = reformLeftLeft(h);
            }else if(isRed(h.left.right)){
                h = reformLeftRight(h);
            }
        }else if(isRed(h.right)){
            if(isRed(h.right.left)){
                h = reformRightLeft(h);
            }else if(isRed(h.right.right)){
                h = reformRightRight(h);
            }
        }

        if(isRed(h.left) && isRed(h.right)){
            flipColor(h);
        }
        h.N = 1 + size(h.left) + size(h.right);
        return h;
    }

    // * official recursive method
    public int pureHeight() {
        return pureHeight(root);
    }
    private int pureHeight(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(pureHeight(x.left), pureHeight(x.right));
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
        RedBlackBSTless<String, Integer> rb = new RedBlackBSTless<>();
        String[] strs = {"S", "E", "A", "R", "C", "H", "X", "M", "P", "L"};
        for(int i =0; i< strs.length;i++){
            rb.put(strs[i], i);
        }
        rb.print();
        StdOut.println(rb.get("R"));
        StdOut.println(rb.get("Z"));
        StdOut.println("All height: " + rb.pureHeight());
    }

    public static void main(String[] args){
        check();
    }
}