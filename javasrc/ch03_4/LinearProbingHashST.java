package javasrc.ch03_4;

/*
* Algorithm 3.6 Hashing with linear probing. P.470

* Proposition M. In a linear-probing hash table of size M and N = a M keys, the
average number of probes (under Assumption J) required is

    ~ ( 1 + 1/(1 - a) ) / 2   and   ~ ( 1 + 1/(1 - a)^2 ) / 2

for search hits and search misses (or inserts), respectively. In particular, when a
is about 1/2, the average number of probes for a search hit is about 3/2 and for a
search miss is about 5/2.

* pro M tells that  we can expect a search to require a huge number of probes in 
a nearly full table; but that the expected number of probes is between 1.5 and 
2.5 if we can ensure that the load factor a is less than 1/2.

* Proposition N. Suppose a hash table is built with array resizing, starting with
an empty table. Under Assumption J, any sequence of t search, insert, and delete
symbol-table operations is executed in expected time proportional to t and with
memory usage always within a constant factor of the number of keys in the table.


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

    public LinearProbingHashST() {
        keys = (Key[]) new Object[M];
        values = (Value[]) new Object[M];
    }

    public LinearProbingHashST(int n) {
        this.M = n;
        keys = (Key[]) new Object[M];
        values = (Value[]) new Object[M];
    }

    // * return a non-negative int, used as array index
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int n) {
        LinearProbingHashST<Key, Value> temp;
        temp = new LinearProbingHashST<>(n);
        for(int i = 0; i < M; i++){
            if(keys[i] != null){
                temp.put(keys[i], values[i]);
            }
        }
        this.keys = temp.keys;
        this.values = temp.values;
        this.M = temp.M;
    }

    public void put(Key key, Value value) {
        if (N >= M / 2) {
            resize(2 * M);
        }

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                values[i] = value;
                return;
            }
        }
        this.keys[i] = key;
        this.values[i] = value;
        this.N++;
    }

    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                return values[i];
            }
        }
        return null;
    }

    public void delete(Key key) {
        int hash = hash(key);
        int i;
        boolean found = false;
        for (i = hash; keys[i] != null; i = (i + 1) % M) {
            if (!found) {
                if (keys[i].equals(key)) {
                    keys[i] = null;
                    values[i] = null;
                    this.N--;
                }
            }else{
                Key tempKey = keys[i];
                Value tempValue = values[i];
                keys[i] = null;
                values[i] = null;
                N--;
                put(tempKey, tempValue);
            }
        }
    }

    // * offical delete(), P.471
    public void deleteX(Key key){
        if(!contains(key)){
            return;
        }
        int i = hash(key);
        while(!key.equals(keys[i])){
            i = (i+1)%M;
        }
        keys[i] = null;
        values[i] = null;
        i = (i+1)%M;
        while(keys[i] != null){
            Key keyToRedo = keys[i];
            Value valToRedo = values[i];
            keys[i] = null;
            values[i]= null;
            N--;
            put(keyToRedo, valToRedo);
            i = (i+1)%M;
        }
        N--;
        if(N > 0 && N == M/8){
            resize(M/2);
        }
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public void print(){
        for(int i = 0; i < M; i++){
            StdOut.println(i + ": " + keys[i] + ", " + values[i]);
        }
    }

    public static void check(){
        LinearProbingHashST<String, Integer> lpTable = new LinearProbingHashST<>();
        String[] keys = {"S", "E", "A", "R", "C", "H", "E","X", "A", "M", "P", "L", "E"};
        for(int i =0; i < keys.length; i++){
            lpTable.put(keys[i], i);
        }
        lpTable.print();
        lpTable.delete("C");
        lpTable.print();
    }

    public static void main(String[] args){
        check();
    }

}