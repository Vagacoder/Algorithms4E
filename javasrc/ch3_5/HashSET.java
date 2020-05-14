package javasrc.ch3_5;

import javasrc.ch03_4.SeparateChainingHashST;

/*
* 3.5.1 Implement SET and HashSET as “wrapper class” clients of ST and HashST, 
respectively (provide dummy values and ignore them).

*/

import lib.*;

public class HashSET<Key> implements ISet<Key>{

    private int INITIAL_SIZE = 21;
    private SeparateChainingHashST<Key, Integer> table;


    public HashSET(){
        table = new SeparateChainingHashST<>(INITIAL_SIZE);
    }

    public void add(Key key){
        table.put(key, 1);
    }

    public void delete(Key key){
        table.delete(key);
    }

    public boolean contains(Key key){
        if(table.get(key) == 1){
            return true;
        }else{
            return false;
        }
    }

    public boolean isEmpty(){
        return table.size() == 0;
    }

    public int size(){
        return table.size();
    }

    public String toString(){
        String result = "";
        for(Key k : table.keys()){
            result = result + k.toString() + ", ";
        }
        return "[" + result.trim() + "]";
    }

    public void print(){
        StdOut.println(toString());
    }


    public static void main(String[] args){
        String[] keys = {"S", "E", "A", "R", "C", "H", "E","X", "A", "M", "P", "L", "E"};
        HashSET<String> set = new HashSET<>();
        for(int i =0; i < keys.length; i++){
            set.add(keys[i]);
        }
        StdOut.println(set.toString());
        set.delete("X");
        set.print();
        set.delete("A");
        set.print();
        set.delete("S");
        set.print();
        set.delete("X");
        set.print();
    }
}
