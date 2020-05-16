package javasrc.ch03_5;

/*
* 3.5.6 Develop classes HashSETint and HashSETdouble for maintaining sets of keys of
primitive int and double types, respectively. (Eliminate code involving values in your
solution to Exercise 3.5.4.)

*/

import lib.*;

public class HashSETint{

    private HashSET<Integer> set;

    public HashSETint(){
        set = new HashSET<>();
    }

    public void add(int key){
        set.add(key);
    }

    public void delete(int key){
        set.delete(key);
    }

    public boolean contains(int key){
        return set.contains(key);
    }

    public boolean isEmpty(){
        return set.isEmpty();
    }

    public int size(){
        return set.size();
    }

    public void print(){
        set.print();
    }

    public static void main(String[] args){
        int[] keys = {6, 5, 2, 7, 1, 4, 0, 8, 9, 3};
        HashSETint set = new HashSETint();
        for(int i =0; i < keys.length; i++){
            set.add(keys[i]);
        }
        set.print();
        set.delete(2);
        set.print();
        set.delete(6);
        set.print();
        set.delete(3);
        set.print();
        set.delete(0);
        set.print();
    }
}