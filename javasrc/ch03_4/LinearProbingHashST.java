package javasrc.ch03_4;

/*
* Algorithm 3.6 Hashing with linear probing. P.470


*/

import lib.*;

public class LinearProbingHashST<Key, Value> {
    
    // * Number of key-value pairs in table
    private int N;
    // * size of linear-probing table
    private int M = 16;
    // * the keys
    private Key[] keys;
    // * values
    private Value[] values;

    public LinearProbingHashST(){
        keys = (Key[]) new Object[M];
        values = (Value[]) new Object[M];
    }

    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int n){

    }

    public void put(Key key, Value value){
        if(N >= M/2){
            resize(2*M);
        }

        int i;
        for(i = hash(key); keys[i] != null; i = (i+1)%M){
            if(keys[i].equals(key)){
                values[i] = value;
                return;
            }
        }
        this.keys[i] = key;
        this.values[i] = value;
        this.N++;
    }

    public Value get(Key key){
        for(int i = hash(key); keys[i] != null; i = (i+1) %M){
            if(keys[i].equals(key)){
                return values[i];
            }
        }
        return null;
    }

}