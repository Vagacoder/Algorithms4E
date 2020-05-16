package javasrc.ch03_5;

import javasrc.ch03_3.RedBlackBST;

/*
* * 3.5.1 Implement SET and HashSET as “wrapper class” clients of ST and HashST, 
respectively (provide dummy values and ignore them).
*/

import lib.*;

public class SET<Key extends Comparable<Key>> implements ISet<Key>{

    private RedBlackBST<Key, Integer> tree;
    
    public SET(){
        tree = new RedBlackBST<>();
    }   

    public void add(Key key){
        tree.put(key, 1);
    }

    public void delete(Key key){
        tree.delete(key);
    }

    public boolean contains(Key key){
        return tree.contains(key);
    }

    public boolean isEmpty(){
        return tree.size() == 0;
    }

    public int size(){
        return tree.size();
    }

    public void print(){
        tree.print();
    }

    public static void main(String[] args){
        String[] keys = {"S", "E", "A", "R", "C", "H", "E","X", "A", "M", "P", "L", "E"};
        SET<String> set = new SET<>();
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