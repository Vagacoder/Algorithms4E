package javasrc.ch03_2;

import javasrc.ch01_3.LinkedListQueue;

/*
* Algorithm 3.3 Binary search tree symbol table P. 398

* Proposition C. Search hits in a BST built from N random keys require ~ 2 ln N
(about 1.39 lg N) compares, on the average.

* Proposition D. Insertions and search misses in a BST built from N random keys
require ~ 2 ln N (about 1.39 lg N) compares, on the average.

* Proposition E. In a BST, all operations take time proportional to the height of the
tree, in the worst case.

* 3.2.6 Add to BST a method height() that computes the height of the tree. Develop two
implementations: a recursive method (which takes linear time and space proportional
to the height), and a method like size() that adds a field to each node in the tree (and
takes linear space and constant time per query).

* 3.2.13 Give nonrecursive implementations of get() and put() for BST.
*/

import lib.*;

public class BST <Key extends Comparable<Key>, Value>{

    private class Node{
        private Key key;
        private Value value;
        private Node left, right;
        private int N;

        public Node(Key key, Value value, int N){
            this.key = key;
            this.value = value;
            this.N = N;
        }
    }

    private Node root;
    private int height;

    public int size(){
        return size(root);
    }

    private int size(Node x){
        if(x == null){
            return 0;
        } else {
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

    // * 3.2.13 non-recursive get()
    public Value getValue(Key key){
        Node cur = this.root;
        while(cur != null){
            int cmp = key.compareTo(cur.key);
            if(cmp < 0){
                cur = cur.left;
            }else if(cmp >0){
                cur = cur.right;
            }else{
                break;
            }
        }
        if(cur == null){
            return null;
        }else {
            return cur.value;
        }
    }

    public void put(Key key, Value value){
        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value){
        if(node == null){
            return new Node(key, value, 1);
        }

        int cmp = key.compareTo(node.key);
        if(cmp < 0){
            node.left = put(node.left, key, value);
        } else if(cmp > 0){
            node.right = put(node.right, key, value);
        } else{
            node.value = value;
        }
        // * better to calculate instead of N++, since if key exists, N does not change.
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    // * 3.2.13 non-recursive put()
    public void putKey(Key key, Value value){
        if(this.root == null){
            this.root = new Node(key, value, 1);
            return;
        }

        Node cur = this.root;
        Node parent = null;
        while (cur != null){
            int cmp = key.compareTo(cur.key);
            if(cmp < 0){
                parent = cur;
                cur = cur.left;
            }else if(cmp > 0){
                parent = cur;
                cur = cur.right;
            }else{
                cur.value = value;
                return;
            }
        }
        if(key.compareTo(parent.key) < 0){
            parent.left = new Node(key, value, 1);
        }else{
            parent.right = new Node(key, value, 1);
        }

        // * if new node is inserted, need update N of all nodes, from root to new node
        cur = this.root;
        while (cur != null){
            int cmp = key.compareTo(cur.key);
            if(cmp < 0){
                cur.N++;
                cur = cur.left;
            }else if(cmp > 0){
                cur.N++;
                cur = cur.right;
            }else{
                cur.value = value;
                return;
            }
        }
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

    public void deleteMin(){
        root = deleteMin(root);
    }

    private Node deleteMin(Node x){
        // * no left child, current node is min, should be repalced by right child
        if(x.left == null){
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax(){
        root = deleteMax(root);
    }

    private Node deleteMax(Node x){
        // * no right child, current node is max, should be repalced by left child
        if(x.right == null){
            return x.left;
        }
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key){
        root = delete(root, key);
    }

    // ! Most important method in BST, remember it
    // * the skill of returning node is usefull for linked list
    private Node delete(Node x, Key key){
        if(x == null){
            return null;
        }

        int cmp = key.compareTo(x.key);
        if(cmp < 0){
            x.left = delete(x.left, key);
        }else if(cmp >0){
            x.right = delete(x.right, key);
        }else{
            if(x.right == null){
                return x.left;
            }
            if(x.left == null){
                return x.right;
            }
            Node temp = x;
            x = min(temp.right);
            x.right = deleteMin(temp.right);
            x.left = temp.left;

            temp.left = null;
            temp.right = null;
        }

        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    // * official recursive method
    public int height() {
        return height(root);
    }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    // * 3.2.6 self-write recursive method
    public int height2(){
        this.height = 0; 
        height2(this.root, -1);
        return this.height;
    }

    private void height2(Node cur, int h){
        if(cur == null){
            return;
        }
        h++;
        if(h > this.height){
            this.height = h;
        }
        height2(cur.left, h);
        height2(cur.right, h);
    }

    // * default method
    public Iterable<Key> keys(){
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key low, Key high){
        LinkedListQueue<Key> queue = new LinkedListQueue<Key>();
        keys(root, queue, low, high);
        return queue;
    }

    private void keys(Node x, LinkedListQueue<Key> queue, Key low, Key high){
        if(x == null){
            return;
        }
        int cmpLow = low.compareTo(x.key);
        int cmpHigh = high.compareTo(x.key);
        if(cmpLow < 0){
            keys(x.left, queue, low, high);
        }
        if (cmpLow <= 0 && cmpHigh >= 0){
            queue.enqueue(x.key);
        }
        if (cmpHigh > 0){
            keys(x.right, queue, low, high);
        }
    }

    // ! Self made method basing on in order traversal
    public Iterable<Key> keysEasy(){
        LinkedListQueue<Key> queue = new LinkedListQueue<>();
        keysEasy(root, queue);
        return queue;
    }

    private void keysEasy(Node x, LinkedListQueue<Key> queue){
        if (x == null){return;}
        keysEasy(x.left, queue);
        queue.enqueue(x.key);
        keysEasy(x.right, queue);
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
        BST<String, Integer> bst = new BST<>();
        bst.putKey("C", 3);
        bst.putKey("I", 9);
        bst.putKey("F", 6);
        bst.putKey("B", 2);
        bst.putKey("A", 1);
        bst.putKey("J", 10);
        bst.putKey("K", 11);
        bst.putKey("E", 5);
        bst.putKey("D", 4);
        bst.putKey("H", 8);
        bst.putKey("G", 7);
        bst.print();
        for(String k : bst.keysEasy()){
            StdOut.println(k);
        }
        StdOut.println("Height: " + bst.height2());
    }

    public static void main(String[] args){
        check();
    }
}