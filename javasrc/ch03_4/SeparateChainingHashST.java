package javasrc.ch03_4;

/*
* Algorithm 3.5 Hashing with separate chaining, P. 465

* Proposition K. In a separate-chaining hash table with M lists and N keys, the 
probability (under Assumption J) that the number of keys in a list is within a 
small constant factor of N/M is extremely close to 1.

*/

import lib.*;
import javasrc.ch03_1.SequentialSearchST;

public class SeparateChainingHashST<Key, Value> {

    // * hash table size
    private int M;
    private SequentialSearchST<Key, Value>[] st;        
    
    public SeparateChainingHashST(){
        this(997);
    }

    public SeparateChainingHashST(int M){
        this.M = M;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for(int i = 0; i < M; i++){
            st[i] = new SequentialSearchST();
        }
    }

    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Value get(Key key){
        return (Value) st[hash(key)].get(key);
    }

    public void put(Key key, Value value){
        st[hash(key)].put(key, value);
    }

    public Iterable<Key> keys(){
        // TODO
        return null;
    }

}