package javasrc.ch3_5;

import javasrc.ch03_3.RedBlackBST;

/*
* 3.5.5 Develop classes STint and STdouble for maintaining ordered symbol ta-
bles where keys are primitive int and double types, respectively. (Convert generics
to primitive types in the code of RedBlackBST.) Test your solution with a version of
SparseVector as a client.

*/

import lib.*;

public class STint<Value>{

    private RedBlackBST<Integer, Value> tree;

    public STint(){
        tree = new RedBlackBST<>();
    }
    
    public void put(int key, Value value) {
        tree.put(key, value);
    }

    public Value get(int key) {
        return tree.get(key);
    }

    public void delete(int key) {
        tree.delete(key);
    }

    public boolean contains(int key){
        return tree.contains(key);
    }

    public boolean isEmpty(){
        return tree.size() == 0;
    }

    public int size(){
        return tree.size();
    }

    public static void main(String[] args){
    
    }
}