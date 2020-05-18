package javasrc.ch03_5;

import java.util.ArrayList;

import javasrc.ch03_4.SeparateChainingHashST;

/*
* 3.5.17 Finite mathematical sets. Your goal is to develop an implementation of the fol-
lowing API for processing finite mathematical sets:

*/

public class MathSET<Key> {
    
    private int size;
    private SeparateChainingHashST<Key, Boolean> table;
    private int tableSize;

    public MathSET(Key[] universe){
        this.size = 0;
        this.tableSize = universe.length;
        for(Key key : universe){
            table.put(key, false);
        }
    }

    public void add(Key key){
        if(table.get(key)!= null){
            table.put(key, true);
            this.size++;
        } else{
            throw new IllegalArgumentException("Key is not in universe");
        }
    }

    public MathSET<Key> complement(){
        ArrayList<Key> results = new ArrayList<>();
        
        for(Key key: table.keys()){
            if(!table.get(key)){
                results.add(key);
            }
        }

        Key[] r = (Key[])results.toArray();
        return new MathSET<Key>(r);
    }

    public void union(MathSET<Key> a){
        for(Key key: a.table.keys()){
            if(a.table.get(key)){
                this.add(key);
            }
        }
    }

    public void intersection(MathSET<Key> a){
        for(Key key: this.table.keys()){
            if(!a.contains(key)){
                delete(key);
            }
        }
    }

    public void delete(Key key){
        if(table.get(key)!= null){
            table.put(key, false);
            this.size--;
        } else{
            throw new IllegalArgumentException("Key is not in universe");
        }
    }   

    public boolean contains(Key key){
        if(table.get(key) == null){
            return false;       // * universe has no this key
        } else{
            return table.get(key);
        }
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public int size(){
        return this.size;
    }


}