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
        StdOut.println(x.key + " : " + x.value);
        print(x.right);
    }

    public static void check(){
        BST<String, Integer> bst = new BST<>();
        bst.put("K", 11);
        bst.put("B", 2);
        bst.put("E", 5);
        bst.put("A", 1);
        bst.put("C", 3);
        bst.put("D", 4);
        bst.put("H", 8);
        bst.put("J", 10);
        bst.put("F", 6);
        bst.put("I", 9);
        bst.put("G", 7);
        bst.print();
        for(String k : bst.keysEasy()){
            StdOut.println(k);
        }
    }

    public static void main(String[] args){
        check();
    }
}