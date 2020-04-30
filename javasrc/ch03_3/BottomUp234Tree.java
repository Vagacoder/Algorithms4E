package javasrc.ch03_3;

/*
* 3.3.28 Bottom-up 2-3-4 trees. Develop an implementation of the basic symbol-table
API that uses balanced 2-3-4 trees as the underlying data structure, 

! using the red-black representation and a bottom-up insertion method based on 
! the same recursive approach as Algorithm 3.4. 

! Your insertion method should split only the sequence of 4-nodes (if any) on the 
! bottom of the search path.

? Implementattion of Left-lean Bottom-up 2-3-4 Tree
? Diagram of inserting a new node, and followed transformation/splitting
?                                                                                                                      
?  2-node      3-node                       4-node               balanced 4-node              5-node                    2x 2-nodes and 1x 3-node
?                                                                                                                                /red
?   h  ===> 1.  h           ==========> 1.    h                         i       ========> 1.    h                               h
?  / \  |   red/ \                  |     red/ \                    red/ \red         |     red/ \red         split            / \
?       |     i                     |       i           =========>    j   h           |       i   j        ===========>       i   j
?       |                           |   red/ \       by rotate right                  |   red/ \          by color flip   red/
?       |       ^                   |     j                                           |     k                               k
?       |       | by rotate left    |                                                 |
?       |         (h, i switched)   |         ^                                       |         ^
?       |=> 2.  h                   |         | by rotate left                        |         | by rotate left
?              / \red               |           (note: i and j are switched)          |           (note: i and k are switched)
?                i                  |=> 2.    h                                       |=> 2.    h
?                                         red/ \                                      |     red/ \red
?                                           i                                         |       i   j
?                                          / \red                                     |      / \red
?                                             j                                       |         k
?                                                                                     |-------------------------------------------------
?                                                                                     |                                          /red
?                                                                                     |=> 3.    h                               h
?                                                                                     |     red/ \red         split            / \
?                                                                                     |       i   j        ==========>        i   j
?                                                                                     |       red/ \      by color flip       red/
?                                                                                     |         k                               k
?                                                                                     |
?                                                                                     |         ^
?                                                                                     |         | by rotate left
?                                                                                     |           (note: j and k are switched)
?                                                                                     |=> 4.    h
?                                                                                           red/ \red
?                                                                                             i   j
?                                                                                                / \red
?                                                                                                   k
?

*/

import lib.*;

public class BottomUp234Tree<Key extends Comparable<Key>, Value> {

    // * inner class
    private class Node {
        Key key;
        Value value;
        Node left, right;
        int N;
        boolean color;

        Node(Key key, Value value, int N, boolean color) {
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

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    private void flipColor(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.N;
        }
    }

    public Value get(Key key) {
        return get(key, root);
    }

    private Value get(Key key, Node x) {
        if (x == null) {
            return null;
        }
        Value result = null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            result = get(key, x.left);
        } else if (cmp > 0) {
            result = get(key, x.right);
        } else {
            result = x.value;
        }
        return result;
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
        root.color = BLACK;
    }

    private Node put(Node h, Key key, Value value) {
        // todo
        if (h == null) {
            return new Node(key, value, 1, RED);
        }

        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            h.left = put(h.left, key, value);
        } else if (cmp > 0) {
            h.right = put(h.right, key, value);
        } else {
            h.value = value;
        }

        // * check 3-node
        if (isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }
        // * check 4-node
        // ! NOTE: need ensure h.right is BLACK (!isRed(h.right)).
        // ! If skiping this check, 5-node case 1 will be transformed,
        // ! Reason: 5-node case 1 is included in 4-node case 1 (see diagram above)
        if (isRed(h.left) && isRed(h.left.left) && !isRed(h.right)) {
            h = rotateRight(h);
        }
        // * check 5-node
        if ((isRed(h.left) && isRed(h.right))
                && (isRed(h.left.left) || isRed(h.left.right) || isRed(h.right.left) || isRed(h.right.right))) {
            flipColor(h);
        }
        h.N = 1 + size(h.left) + size(h.right);
        return h;
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
        BottomUp234Tree<String, Integer> t234 = new BottomUp234Tree<>();
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