package javasrc.ch3_5;

import javasrc.ch03_1.BinarySearchST;

/*
* 3.5.3 Develop a SET implementation BinarySearchSET by starting with the code for
BinarySearchST and eliminating all of the code involving values.

*/

import lib.*;

public class BinarySearchSET<Key extends Comparable<Key>> implements ISet<Key>{
    private int INITIAL_SIZE = 21;
    private BinarySearchST<Key, Integer> st;

    public BinarySearchSET(){
        st = new BinarySearchST<>(INITIAL_SIZE);
    }

    @Override
    public void add(Key key) {
        st.put(key, 1);
    }

    @Override
    public void delete(Key key) {
        st.delete(key);
    }

    @Override
    public boolean contains(Key key) {
        return st.contains(key);
    }

    @Override
    public boolean isEmpty() {
        return st.isEmpty();
    }

    @Override
    public int size() {
        return st.size();
    }

    @Override
    public void print() {
        st.printAll();
    }


    public static void main(String[] args){
        String[] keys = {"S", "E", "A", "R", "C", "H", "E","X", "A", "M", "P", "L", "E"};
        BinarySearchSET<String> set = new BinarySearchSET<>();
        for(int i =0; i < keys.length; i++){
            set.add(keys[i]);
        }
        set.print();
        StdOut.println();
        set.delete("X");
        set.print();
        StdOut.println();
        set.delete("A");
        set.print();
        StdOut.println();
        set.delete("S");
        set.print();
        StdOut.println();
        set.delete("X");
        set.print();
    }
}