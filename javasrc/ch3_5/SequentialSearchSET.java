package javasrc.ch3_5;

import javasrc.ch03_1.SequentialSearchST;

/*
* 3.5.2 Develop a SET implementation SequentialSearchSET by starting with the code
for SequentialSearchST and eliminating all of the code involving values.

*/

import lib.*;

public class SequentialSearchSET<Key> implements ISet<Key>{

    private SequentialSearchST<Key, Integer> st;

    public SequentialSearchSET(){
        st = new SequentialSearchST<>();
    }

    public void add(Key key){
        st.put(key, 1);
    }

    public void delete(Key key){
        st.delete(key);
    }

    public boolean contains(Key key){
        return st.contains(key);
    }

    public boolean isEmpty(){
        return st.size() == 0;
    }

    public int size(){
        return st.size();
    }

    public String toString(){
        return "Not implemented";
    }

    public void print(){
        st.print();
    }

    public static void main(String[] args){
        String[] keys = {"S", "E", "A", "R", "C", "H", "E","X", "A", "M", "P", "L", "E"};
        SequentialSearchSET<String> set = new SequentialSearchSET<>();
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