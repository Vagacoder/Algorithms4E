package javasrc.ch03_5;

import javasrc.ch03_3.RedBlackBST;
import lib.*;


/*
* 3.5.7 Develop classes SETint and SETdouble for maintaining ordered sets of keys of
primitive int and double types, respectively. (Eliminate code involving values in your
solution to Exercise 3.5.5.)

*/


public class SETint {
    
    private RedBlackBST<Integer, Integer> set;


    public SETint(){
        set = new RedBlackBST<>();
    }

    public void add(int key){
        set.put(key, 1);
    }

    public void delete(int key){
        set.delete(key);
    }

    public boolean contains(int key){
        return set.contains(key);
    }

    public boolean isEmpty(){
        return set.size() == 0;
    }

    public int size(){
        return set.size();
    }

    public void print(){
        set.print();
    }

    public static void main(String[] args){
        SETint set = new SETint();
        int[] keys = {6, 5, 2, 7, 1, 4, 0, 8, 9, 3};
        for(int i =0; i < keys.length; i++){
            set.add(keys[i]);
        }
        set.print();
        StdOut.println();
        set.delete(2);
        set.print();
        StdOut.println();
        set.delete(6);
        set.print();
        StdOut.println();
        set.delete(3);
        set.print();
        StdOut.println();
        set.delete(0);
        set.print();
    }

}