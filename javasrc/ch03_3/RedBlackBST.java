package javasrc.ch03_3;

/*
* Algorithm 3.4 Red-Black BST P.439

* Proposition G. The height of a red-black BST with N nodes is no more than 2 lg N.

* Property H. The average length of a path from the root to a node in a red-black
BST with N nodes is ~1.00 lg N.

* Proposition I. In a red-black BST, the following operations take logarithmic time
in the worst case: search, insertion, finding the minimum, finding the maximum,
floor, ceiling, rank, select, delete the minimum, delete the maximum, delete, and
range count.

* 3.3.39 Delete the minimum. 
Implement the deleteMin() operation for red-black BSTs by maintaining the correspondence 
with the transformations given in the text for moving down the left spine of the 
tree while maintaining the invariant that the current node is not a 2-node.

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

    // ! 4 helpers from official implementation
    private void flipColors(Node h) {
        // h must have opposite color of its two children
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }
    // ! Assuming that h is red and both h.left and h.left.left
    // ! are black, make h.left or one of its children red.
    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) { 
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // ! Assuming that h is red and both h.right and h.right.left
    // ! are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        flipColors(h);
        if (isRed(h.left.left)) { 
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // ! restore red-black tree invariant
    private Node balance(Node h) {
        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
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


    public Key min(){
        return min(root).key;
    }

    private Node min(Node x){
        if(x.left == null){
            return x;
        }else {
            return min(x.left);
        }
    }

    public Key max(){
        return max(root).key;
    }

    private Node max(Node x){
        if(x.right == null){
            return x;
        }else {
            return max(x.right);
        }
    }

    public Key floor(Key key){
        Node x = floor(root, key);
        if(x == null){
            return null;
        }else{
            return x.key;
        }
    }

    private Node floor(Node x, Key key){
        if(x == null){
            return null;
        }
        int cmp = key.compareTo(x.key);
        if(cmp == 0){
            return x;
        }
        if(cmp < 0){
            return floor(x.left, key);
        }
        // ! If key is greater than the key at the root, then the floor of key
        // ! could be in the right subtree, but only if there is a key smaller 
        // ! than or equal to key in the right subtree; if not, the key at the 
        // ! root is the floor of key
        Node temp = floor(x.right, key);
        if(temp != null){
            return temp;
        }else {
            return x;
        }
    }

    public Key ceiling(Key key){
        Node x = ceiling(root, key);
        if(x == null){
            return null;
        }else {
            return x.key;
        }
    }

    private Node ceiling(Node x, Key key){
        if (x == null){
            return null;
        }
        int cmp = key.compareTo(x.key);
        if(cmp == 0) {
            return x;
        }
        if (cmp > 0){
            return ceiling(x.right, key);
        }
        Node temp = ceiling(x.left, key);
        if(temp == null){
            return x;
        }else {
            return temp;
        }
    }

    public Key select(int k){
        return select(root, k).key;
    }

    private Node select(Node x, int k){
        if (x == null){
            return null;
        }
        int t = size(x.left);
        if(t > k){
            return select(x.left, k);
        }else if (t < k){
            return select(x.right, k-t-1);
        }else {
            return x;
        }
    }

    public int rank(Key key){
        return rank(root, key);
    }

    private int rank(Node x, Key key){
        if(x == null){
            return 0;
        }
        int cmp = key.compareTo(x.key);
        if(cmp < 0){
            return rank(x.left, key);
        }else if(cmp > 0){
            return 1 + size(x.left) + rank(x.right, key);
        }else{
            return size(x.left);
        }
    }

    // * 3.3.29 
    // ! to study play check the official method in RedBlackBSTx.java
    // ? check P. 442 for cases
    public void deleteMin(){
        if(root == null){
            return;
        }
        // ? case #1 root with 2x 2-nodes
        if(root.left != null && root.right!=null && is2Node(root.left) && 
        is2Node(root.right) && !isRed(root.left)&& !isRed(root.right)){
            root.left.color = RED;
            root.right.color = RED;
        }

        root = deleteMin(root);
        if(root != null){
            root.color = BLACK;
        }
    }

    private Node deleteMin(Node h){
        // ? case #5 at the bottom
        if(h.left == null){
            return null;
        }

        if(is2Node(h.left)){
            if(isRed(h.right.left)){
                // ? case # 2 and 3
                Node x = h.right;
                Node y = x.left;
                h.right = y.left;
                x.left = y.right;
                y.left = h;
                y.right = x;
                y.N = h.N;
                y.color = h.color;
                h.N = 1 + size(h.left) + size(h.right);
                h.color = BLACK;
                h.left.color = RED;
                x.N = 1 + size(x.left) + size(x.right);
                h = y;
            }
            if(!isRed(h.left) && !isRed(h.right)){
                // ? case #4
                h.color = !h.color;
                h.left.color = !h.left.color;
                h.right.color = !h.right.color;
            }
        }

        // ? recursive call to delete min
        h.left = deleteMin(h.left);

        // ? Re-balance on the way up
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

    // * copied from official implementation
    public void deleteMinx() {
        if (root == null) {
            return;
        }

        // ! Ensure every node on the path is Not a 2-node. Starting at root
        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMinx(root);
        if (root == null) root.color = BLACK;
        // assert check();
    }

    // delete the key-value pair with the minimum key rooted at h
    private Node deleteMinx(Node h) { 
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }

    // * copied from official implementation
    public void deleteMax() {
        if (root == null){
            return;
        }

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right)){
            root.color = RED;
        }

        root = deleteMax(root);
        if (root == null){
            root.color = BLACK;
        }
    }

    // delete the key-value pair with the maximum key rooted at h
    private Node deleteMax(Node h) { 
        if (isRed(h.left))
            h = rotateRight(h);

        if (h.right == null)
            return null;

        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = deleteMax(h.right);

        return balance(h);
    }

    public void delete(Key key) { 
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (root == null) root.color = BLACK;
        // assert check();
    }

    // delete the key-value pair with the given key rooted at h
    private Node delete(Node h, Key key) { 
        // assert get(h, key) != null;

        if (key.compareTo(h.key) < 0)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.value = x.value;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }

    public boolean contains(Key key) {
        return get(key) != null;
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
        return !isRed(h) && !isRed(h.left) && !isRed(h.right);
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
        RedBlackBST<String, Integer> rb = new RedBlackBST<>();
        String[] strs = {"S", "E", "A", "R", "C", "H", "X", "M", "P", "L"};
        for(int i =0; i< strs.length;i++){
            rb.put(strs[i], i);
        }
        rb.print();
        StdOut.println(rb.get("R"));
        StdOut.println(rb.get("Z"));
        StdOut.println("All height: " + rb.pureHeight());

        rb.deleteMin();
        rb.print();
        StdOut.println("All height: " + rb.pureHeight());
        StdOut.println();
        rb.deleteMin();
        StdOut.println("All height: " + rb.pureHeight());
        rb.print();
        StdOut.println();
        rb.deleteMin();
        StdOut.println("All height: " + rb.pureHeight());
        rb.print();
        StdOut.println();
        rb.deleteMin();
        StdOut.println("All height: " + rb.pureHeight());
        rb.print();
        StdOut.println();
        rb.deleteMin();
        StdOut.println("All height: " + rb.pureHeight());
        rb.print();
        StdOut.println();
        rb.deleteMin();
        StdOut.println("All height: " + rb.pureHeight());
        rb.print();
        StdOut.println();
        rb.deleteMin();
        StdOut.println("All height: " + rb.pureHeight());
        rb.print();
        StdOut.println();
        rb.deleteMin();
        StdOut.println("All height: " + rb.pureHeight());
        rb.print();
        StdOut.println();
        rb.deleteMin();
        StdOut.println("All height: " + rb.pureHeight());
        rb.print();
        StdOut.println();
        rb.deleteMin();
        StdOut.println("All height: " + rb.pureHeight());
        rb.print();
        StdOut.println();
        rb.deleteMin();
        StdOut.println("All height: " + rb.pureHeight());
        rb.print();
    }

    public static void main(String[] args){
        check();
    }
}