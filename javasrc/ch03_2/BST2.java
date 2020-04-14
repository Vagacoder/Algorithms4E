package javasrc.ch03_2;

import javasrc.ch01_3.LinkedListQueue;

/*
* most methods are copied from BST.java; some of them are modified for exercises

* 3.2.6 Add to BST a method height() that computes the height of the tree. Develop two
implementations: a recursive method (which takes linear time and space proportional
to the height), and a method like size() that adds a field to each node in the tree (and
takes linear space and constant time per query).

* 3.2.7 Add to BST a recursive method avgCompares() that computes the average num-
ber of compares required by a random search hit in a given BST (the internal path
length of the tree divided by its size, plus one). Develop two implementations: a re-
cursive method (which takes linear time and space proportional to the height), and a
method like size() that adds a field to each node in the tree (and takes linear space and
constant time per query).

* 3.2.12 Develop a BST implementation that omits rank() and select() and does not
use a count field in Node.

* 3.2.13 Give nonrecursive implementations of get() and put() for BST.

*/

import lib.*;

public class BST2 <Key extends Comparable<Key>, Value>{

    private class Node{
        private Key key;
        private Value value;
        private Node left, right;
        private int N;
        private int height;

        public Node(Key key, Value value, int N, int height){
            this.key = key;
            this.value = value;
            this.N = N;
            this.height =height;
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

    // * 3.2.12 helper function
    // * size method not using N in Node
    public int sizeOfTree(Node root){
        if(root == null){
            return 0;
        }
        return 1 + sizeOfTree(root.left) + sizeOfTree(root.right);
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

    // * 3.2.6 this method is modified for non-recursive height2()
    private Node put(Node node, Key key, Value value){
        if(node == null){
            return new Node(key, value, 1, 0);
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
        int heightLeft = height3(node.left);
        int heightRight = height3(node.right);
        if(heightLeft > heightRight){
            node.height = heightLeft + 1;
        }else{
            node.height = heightRight + 1;
        }
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

    // * 3.2.12 select() without using N in Node
    // ! Just replace size() with sizeOfTree()
    public Key select2(int k){
        return select2(root, k).key;
    }

    private Node select2(Node x, int k){
        if (x == null){
            return null;
        }
        int sizeOfLeftSubtree = sizeOfTree(x.left);
        if(sizeOfLeftSubtree > k){
            return select2(x.left, k);
        }else if (sizeOfLeftSubtree < k){
            return select2(x.right, k-sizeOfLeftSubtree-1);
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

    // * 3.2.12 rank() without using N in Node
    // ! Just replace size() with sizeOfTree()
    public int rank2(Key key){
        return rank2(root, key);
    }

    private int rank2(Node x, Key key){
        if(x == null){
            return 0;
        }
        int cmp = key.compareTo(x.key);
        if(cmp < 0){
            return rank2(x.left, key);
        }else if(cmp > 0){
            return 1 + sizeOfTree(x.left) + rank2(x.right, key);
        }else{
            return sizeOfTree(x.left);
        }
    }
    
    public void deleteMin(){
        root = deleteMin(root);
        updateHeight(root);
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
        updateHeight(root);
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
        updateHeight(root);
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

    // * 3.2.6 non-recursive method 
    public int height3(){
        return height3(this.root);
    }

    private int height3(Node x){
        if(x == null){return 0;}
        return x.height;
    }

    // * additional method for depth of one specific node
    public int depth(Key key){
        int depth = 0;

        Node cur = this.root;
        while(cur != null){
            int cmp = key.compareTo(cur.key);
            if(cmp == 0){
                break;
            } else if(cmp < 0){
                cur = cur.left;
            } else{
                cur = cur.right;
            }
            depth++;
        }

        // * key is not found in tree
        if (cur == null){
            return -1;
        }else {
            return depth;
        }
    }

    // ! all methods which modify tree structure should call this method to upate
    // ! the field of Node, height. The methods include delMin(), delMax(), delete(),
    // ! put() no need call this method, since put() already implement height updating.
    private void updateHeight(Node x){
        if(x == null){
            return;
        }
        updateHeight(x.left);
        updateHeight(x.right);
        int leftH = -1;
        int rightH = -1;
        if(x.left != null){
            leftH = x.left.height;
        }
        if(x.right != null){
            rightH = x.right.height;
        }
        x.height = 1 + Math.max(leftH, rightH);
    }

    // * 3.2.7
    public int avgCompares(Key key){
        return avgCompares(root, key);
    }

    private int avgCompares(Node cur, Key key){
        int compareNumber = 0;
        if (cur != null){
            int cmp = key.compareTo(cur.key);
            if(cmp < 0){
                compareNumber = 1 + avgCompares(cur.left, key);
            } else if(cmp > 0){
                compareNumber = 1 + avgCompares(cur.right, key);
            } else{
                compareNumber = 1;
            }
        }
        return compareNumber;
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
        BST2<String, Integer> bst = new BST2<>();
        bst.put("F", 6);
        bst.put("C", 3);
        bst.put("I", 9);
        bst.put("B", 2);
        bst.put("A", 1);
        bst.put("J", 10);
        bst.put("K", 11);
        bst.put("E", 5);
        bst.put("D", 4);
        bst.put("H", 8);
        bst.put("G", 7);
        StdOut.println("1. print all tree nodes ...");
        bst.print();
        for(String k : bst.keysEasy()){
            StdOut.println(k);
        }

        StdOut.println("\n1.1. test getValue()");
        StdOut.println(bst.getValue(""));
        StdOut.println(bst.getValue("A"));
        StdOut.println(bst.getValue("B"));
        StdOut.println(bst.getValue("C"));
        StdOut.println(bst.getValue("D"));
        StdOut.println(bst.getValue("E"));
        StdOut.println(bst.getValue("F"));
        StdOut.println(bst.getValue("G"));
        StdOut.println(bst.getValue("H"));
        StdOut.println(bst.getValue("I"));
        StdOut.println(bst.getValue("J"));
        StdOut.println(bst.getValue("K"));

        StdOut.println("\n2. test average compare ...");
        StdOut.println("2.1. search H ...");
        StdOut.println(bst.avgCompares("H"));
        StdOut.println("2.2. search G ...");
        StdOut.println(bst.avgCompares("G"));        
        StdOut.println("2.3. search F ...");
        StdOut.println(bst.avgCompares("F"));
        StdOut.println("2.4. search C ...");
        StdOut.println(bst.avgCompares("C"));
        StdOut.println("2.5. search B ...");
        StdOut.println(bst.avgCompares("B"));
        StdOut.println("2.6. search D ...");
        StdOut.println(bst.avgCompares("D"));


        StdOut.println("\n3. test rank2() ...");
        StdOut.println("rank  of A: " + bst.rank("A"));
        StdOut.println("rank2 of A: " + bst.rank2("A"));
        StdOut.println("rank  of B: " + bst.rank("B"));
        StdOut.println("rank2 of B: " + bst.rank2("B"));
        StdOut.println("rank  of C: " + bst.rank("C"));
        StdOut.println("rank2 of C: " + bst.rank2("C"));
        StdOut.println("rank  of D: " + bst.rank("D"));
        StdOut.println("rank2 of D: " + bst.rank2("D"));
        StdOut.println("rank  of E: " + bst.rank("E"));
        StdOut.println("rank2 of E: " + bst.rank2("E"));
        StdOut.println("rank  of F: " + bst.rank("F"));
        StdOut.println("rank2 of F: " + bst.rank2("F"));

        StdOut.println("\n4. test depth() ...");
        StdOut.println("depth of A: " + bst.depth("A"));
        StdOut.println("depth of B: " + bst.depth("B"));
        StdOut.println("depth of C: " + bst.depth("C"));
        StdOut.println("depth of D: " + bst.depth("D"));
        StdOut.println("depth of E: " + bst.depth("E"));
        StdOut.println("depth of F: " + bst.depth("F"));
        StdOut.println("depth of G: " + bst.depth("G"));
        StdOut.println("depth of H: " + bst.depth("H"));
        StdOut.println("depth of I: " + bst.depth("I"));
        StdOut.println("depth of J: " + bst.depth("J"));
        StdOut.println("depth of K: " + bst.depth("K"));

        // ! Note: in section, some elements are DELETED!
        StdOut.println("\n5.  testing height ...");
        StdOut.println("Height: " + bst.height());
        StdOut.println("Height: " + bst.height2());
        StdOut.println("Height: " + bst.height3());
        bst.deleteMin();
        StdOut.println("After delete min (A)");
        StdOut.println("Height: " + bst.height());
        StdOut.println("Height: " + bst.height2());
        StdOut.println("Height: " + bst.height3());
        bst.delete("D");
        StdOut.println("After delete D");
        StdOut.println("Height: " + bst.height());
        StdOut.println("Height: " + bst.height2());
        StdOut.println("Height: " + bst.height3());
        bst.delete("H");
        StdOut.println("After delete H");
        StdOut.println("Height: " + bst.height());
        StdOut.println("Height: " + bst.height2());
        StdOut.println("Height: " + bst.height3());
        bst.delete("K");
        StdOut.println("After delete K");
        StdOut.println("Height: " + bst.height());
        StdOut.println("Height: " + bst.height2());
        StdOut.println("Height: " + bst.height3());
        bst.delete("J");
        StdOut.println("After delete J");
        StdOut.println("Height: " + bst.height());
        StdOut.println("Height: " + bst.height2());
        StdOut.println("Height: " + bst.height3());

    }

    public static void main(String[] args){
        check();
    }
}