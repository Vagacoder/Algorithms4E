package javasrc.ch03_4;

import javasrc.ch01_3.LinkedListQueue;

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

* 3.4.19 Implement keys() for SeparateChainingHashST and LinearProbingHashST

* 3.4.20 Add a method to LinearProbingHashST that computes the average cost of a
search hit in the table, assuming that each key in the table is equally likely 
to be sought.

* 3.4.21 Add a method to LinearProbingHashST that computes the average cost of a
search miss in the table, assuming a random hash function. Note : You do not have to
compute any hash functions to solve this problem.

* 3.4.26 Lazy delete for linear probing. Add to LinearProbingHashST a delete()
method that deletes a key-value pair by setting the value to null (but not removing
the key) and later removing the pair from the table in resize(). Your primary chal-
lenge is to decide when to call resize(). Note : You should overwrite the null value if
a subsequent put() operation associates a new value with the key. Make sure that your
program takes into account the number of such tombstone items, as well as the number
of empty positions, in making the decision whether to expand or contract the table.


*/

import lib.*;

public class LinearProbingHashST2<Key, Value> {

    // * Number of key-value pairs in table
    private int N;
    // * size of linear-probing table
    private int M = 28;
    // * the keys
    private Key[] keys;
    // * values
    private Value[] values;

    // * 3.4.20 and 3.4.21
    private int totalSearch = 0;
    private int totalCompare = 0;
    private int totalMiss = 0;


    public LinearProbingHashST2() {
        keys = (Key[]) new Object[M];
        values = (Value[]) new Object[M];
    }

    public LinearProbingHashST2(int n) {
        this.M = n;
        keys = (Key[]) new Object[M];
        values = (Value[]) new Object[M];
    }

    // * return a non-negative int, used as array index
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int n) {
        LinearProbingHashST2<Key, Value> temp;
        temp = new LinearProbingHashST2<>(n);
        for(int i = 0; i < M; i++){
            if(keys[i] != null){
                temp.put(keys[i], values[i]);
            }
        }
        this.keys = temp.keys;
        this.values = temp.values;
        this.M = temp.M;
    }

    // * 3.4.26 
    private void resizeLazy(int n) {
        LinearProbingHashST2<Key, Value> temp;
        temp = new LinearProbingHashST2<>(n);
        for(int i = 0; i < M; i++){
            if(keys[i] != null&& values[i] != null){
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

    // * 3.4.20 and 3.4.21
    public Value get(Key key) {
        this.totalSearch++;

        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            this.totalCompare++;
            this.totalMiss++;

            if (keys[i].equals(key)) {
                this.totalMiss--;
                return values[i];
            }
        }
        return null;
    }

    // * 3.4.29
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
                    found = true;
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

    // * 3.4.26
    public void deleteLazy(Key key) {
        int hash = hash(key);
        int i;
        boolean found = false;
        for (i = hash; keys[i] != null; i = (i + 1) % M) {
            if (!found) {
                if (keys[i].equals(key)) {
                    keys[i] = null;
                    // values[i] = null;
                    this.N--;
                    found = true;
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

    // * 3.4.19
    public Iterable<Key> keys(){
        LinkedListQueue<Key> keys = new LinkedListQueue<>();
        for(int i = 0; i < this.keys.length; i++){
            if(this.keys[i] != null){
                keys.enqueue(this.keys[i]);
            }
        }
        return keys;
    }

    // * 3.4.20
    public int getAverageCost(){
        if(this.totalSearch != 0){
            return this.totalCompare / this.totalSearch;
        }else{
            return 0;
        }
    }

    // * 3.4.21
    public int getAverageMiss(){
        if(this.totalSearch != 0){
            return this.totalMiss / this.totalSearch;
        }else{
            return 0;
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
        LinearProbingHashST2<String, Integer> lpTable = new LinearProbingHashST2<>();
        String[] keys = {"S", "E", "A", "R", "C", "H", "E","X", "A", "M", "P", "L", "E"};
        for(int i =0; i < keys.length; i++){
            lpTable.put(keys[i], i);
        }
        StdOut.println(lpTable.getAverageCost());
        for(String key: lpTable.keys()){
            StdOut.println(key);
        }
        StdOut.println();
        lpTable.print();

        StdOut.println();
        lpTable.delete("L");
        lpTable.print();

        StdOut.println();
        lpTable.get("A");
        StdOut.println(lpTable.getAverageCost());
        StdOut.println(lpTable.getAverageMiss());
        lpTable.get("E");
        StdOut.println(lpTable.getAverageCost());
        StdOut.println(lpTable.getAverageMiss());
        lpTable.get("X");
        StdOut.println(lpTable.getAverageCost());
        StdOut.println(lpTable.getAverageMiss());
        lpTable.get("P");
        StdOut.println(lpTable.getAverageCost());
        StdOut.println(lpTable.getAverageMiss());
        lpTable.get("C");
        StdOut.println(lpTable.getAverageCost());
        StdOut.println(lpTable.getAverageMiss());
    }

    public static void main(String[] args){
        check();
    }

}