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
! NOT DONE YET
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
        root = put1(root, key, value);
        root.color = BLACK;
    }

    // !!! Very smart implementation for 2-3-4 tree!!!
    private Node put0(Node h, Key key, Value value){
        // ! whenever insert a new Noed, it is always RED!
        if(h == null){
            return new Node(key, value, 1, RED);
        }

        // ! move flip color here IS the trick of this implementation
        if(isRed(h.left) && isRed(h.right)){
            flipColor(h);
        }

        int cmp = key.compareTo(h.key);
        if(cmp < 0){
            h.left = put0(h.left, key, value);
        } else if (cmp > 0){
            h.right = put0(h.right, key, value);
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


        h.N = 1 + size(h.left) + size(h.right);
        return h;
    }

    private Node put1(Node h, Key key, Value value){

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
            h.left = put1(h.left, key, value);
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
            h.right = put1(h.right, key, value);
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
        Node parent = null;
        // ? Cases are list on P.441, left panel, from top to bottom
        // ? empty tree
        if(cur == null){
            this.root = new Node(key, value, 1, BLACK);
            return;
        }
        // ? case 1, at root
        if(isRed(root.left) && isRed(root.right)){
            root.left.color = BLACK;
            root.right.color = BLACK;
        }

        // ? keep going down until ??? 
        // ? since this is left-lean, bottom node right must be null.
        while (cur.right != null){
            int cmp = key.compareTo(cur.key);
            if(cmp < 0){
                if(is4Node(cur.left)){
                    // ? case 2
                    flipColor(cur.left);
                }else if(is3Node(cur) && cur.left != null && key.compareTo(cur.left.key)<0){
                    // ? case 4
                    if(is4Node(cur.left.left)){
                        flipColor(cur.left.left);
                        if(parent.left == cur){
                            parent.left = rotateRight(cur);
                            cur = parent.left;
                        } else {
                            parent.right = rotateRight(cur);
                            cur = parent.right;
                        }
                    }
                }else if(is3Node(cur) && cur.left != null && key.compareTo(cur.left.key)>0){
                    // ? case 5
                    if(is4Node(cur.left.right)){
                        flipColor(cur.left.right);
                        if(parent.left == cur){
                            Node x = cur.left.right;
                            cur.left.right = x.left;
                            x.left = cur.left;
                            cur.left = x.right;
                            x.right = cur;
                            x.color = cur.color;
                            cur.color = RED;
                            x.N = cur.N;
                            cur.N = 1 + size(cur.left) + size(cur.right);
                            x.left.N = 1 + size(x.left.left) + size(x.left.right);
                            parent.left = x;
                            cur = parent.left;
                        }else {
                            Node x = cur.left.right;
                            cur.left.right = x.left;
                            x.left = cur.left;
                            cur.left = x.right;
                            x.right = cur;
                            x.color = cur.color;
                            cur.color = RED;
                            x.N = cur.N;
                            cur.N = 1 + size(cur.left) + size(cur.right);
                            x.left.N = 1 + size(x.left.left) + size(x.left.right);
                            parent.right = x;
                            cur = parent.right;
                        }
                        
                    }
                }
                parent = cur;
                cur = cur.left;
            } else if(cmp > 0){
                if(is2Node(cur) && is4Node(cur.right)){
                    // ? case 3
                    flipColor(cur.right);
                    if(parent != null && parent.left != null && parent.left == cur){
                        parent.left = rotateLeft(cur);
                        cur = parent.left;
                    }else if (parent != null){
                        parent.right = rotateLeft(cur);
                        cur = parent.right;
                    }
                }else if(is3Node(cur) && is4Node(cur.right)){
                    // ? case 6
                    flipColor(cur.right);
                }
                parent = cur;
                cur = cur.right;
            }else {
                cur.value = value;
                return; // no need update N
            }
        }

        // ? Insert at bottom
        int cmp = key.compareTo(cur.key);
        if(cur.left == null){
            // ? case 7, insert to 2 node at the bottom
            if(cmp < 0){
                cur.left = new Node(key, value, 1, RED);
                cur.N++;
            }else if(cmp > 0){
                cur.right = new Node(key, value, 1, RED);
                if(parent.left == cur){
                    parent.left = rotateLeft(cur); 
                }else {
                    parent.right = rotateLeft(cur); 
                }
            }else {
                cur.value = value;
                return; // no need update N
            }
        }else {
            // ? case 8, insert to 3 node at the bottom
            if(cmp < 0){
                int cmp2 = key.compareTo(cur.left.key);
                if(cmp2 < 0){
                    cur.left.left = new Node(key, value, 1, RED);
                    cur.N++;
                    if(cur == root){
                        root = rotateRight(cur);  
                    } else{
                        if(parent.left == cur){
                            parent.left = rotateRight(cur); 
                        }else {
                            parent.right = rotateRight(cur); 
                        }
                    }
                }else if(cmp>0){
                    cur.left.right = new Node(key, value, 1, RED);
                    cur.left = rotateLeft(cur.left);
                    if(cur == root){
                        root = rotateRight(cur);  
                    }else {
                        if(parent.left == cur){
                            parent.left = rotateRight(cur); 
                        }else {
                            parent.right = rotateRight(cur); 
                        }
                    }
                }else{
                    cur.left.value = value;
                    return;     // no need update N
                }
            }else if(cmp > 0){
                cur.right = new Node(key, value, 1, RED);
                cur.N++;
            }else{
                cur.value = value;
                return;     // no need update N
            }
        }

        // ! call recursiveUpdateN(root)
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
            t234.putNoRecursive(strs[i], i);
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