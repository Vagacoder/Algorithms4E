package javasrc.ch03_2;

/*
* Algorithm 3.3 Binary search tree symbol table P. 398


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




    public static void main(String[] args){

    }
}