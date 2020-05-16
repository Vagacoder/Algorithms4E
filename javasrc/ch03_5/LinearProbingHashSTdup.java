package javasrc.ch03_5;

import java.util.ArrayList;

import javasrc.ch01_3.LinkedListQueue;

/*
* Algorithm 3.6 Hashing with linear probing. P.470

* 3.4.19 Implement keys() for SeparateChainingHashST and LinearProbingHashST

* 3.4.20 Add a method to LinearProbingHashST that computes the average cost of a
search hit in the table, assuming that each key in the table is equally likely 
to be sought.

* 3.4.21 Add a method to LinearProbingHashST that computes the average cost of a
search miss in the table, assuming a random hash function. Note : You do not have to
compute any hash functions to solve this problem.

* 3.5.8 Modify LinearProbingHashST to keep duplicate keys in the table. Return any
value associated with the given key for get(), and remove all items in the table 
that have keys equal to the given key for delete().

*/

import lib.*;

public class LinearProbingHashSTdup<Key, Value> {

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

    public LinearProbingHashSTdup() {
        keys = (Key[]) new Object[M];
        values = (Value[]) new Object[M];
    }

    public LinearProbingHashSTdup(int n) {
        this.M = n;
        keys = (Key[]) new Object[M];
        values = (Value[]) new Object[M];
    }

    // * return a non-negative int, used as array index
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int n) {
        LinearProbingHashSTdup<Key, Value> temp;
        temp = new LinearProbingHashSTdup<>(n);
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], values[i]);
            }
        }
        this.keys = temp.keys;
        this.values = temp.values;
        this.M = temp.M;
    }

    // * 3.5.8
    public void put(Key key, Value value) {
        if (N >= M / 2) {
            resize(2 * M);
        }

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            // if (keys[i].equals(key)) {
            // values[i] = value;
            // return;
            // }
        }
        this.keys[i] = key;
        this.values[i] = value;
        this.N++;
    }

    // * 3.4.20 and 3.4.21
    public ArrayList<Value> get(Key key) {
        this.totalSearch++;
        ArrayList<Value> result = new ArrayList<>();

        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            this.totalCompare++;
            this.totalMiss++;

            if (keys[i].equals(key)) {
                this.totalMiss--;
                result.add(values[i]);
            }
        }
        return result;
    }

    // * 3.5.8
    public void delete(Key key) {
        int hash = hash(key);
        int i;
        for (i = hash; keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                keys[i] = null;
                values[i] = null;
                this.N--;
            } else {
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
    public void deleteX(Key key) {
        if (!contains(key)) {
            return;
        }
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % M;
        }
        keys[i] = null;
        values[i] = null;
        i = (i + 1) % M;
        while (keys[i] != null) {
            Key keyToRedo = keys[i];
            Value valToRedo = values[i];
            keys[i] = null;
            values[i] = null;
            N--;
            put(keyToRedo, valToRedo);
            i = (i + 1) % M;
        }
        N--;
        if (N > 0 && N == M / 8) {
            resize(M / 2);
        }
    }

    // * 3.4.19
    public Iterable<Key> keys() {
        LinkedListQueue<Key> keys = new LinkedListQueue<>();
        for (int i = 0; i < this.keys.length; i++) {
            if (this.keys[i] != null) {
                keys.enqueue(this.keys[i]);
            }
        }
        return keys;
    }

    // * 3.4.20
    public int getAverageCost() {
        if (this.totalSearch != 0) {
            return this.totalCompare / this.totalSearch;
        } else {
            return 0;
        }
    }

    // * 3.4.21
    public int getAverageMiss() {
        if (this.totalSearch != 0) {
            return this.totalMiss / this.totalSearch;
        } else {
            return 0;
        }
    }

    public boolean contains(Key key) {
        if (key == null)
            throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public void print() {
        for (int i = 0; i < M; i++) {
            StdOut.println(i + ": " + keys[i] + ", " + values[i]);
        }
    }

    public static void check() {
        LinearProbingHashSTdup<String, Integer> lpTable = new LinearProbingHashSTdup<>();
        String[] keys = { "S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E" };
        lpTable.put("E", 99);
        lpTable.put("E", 98);
        for (int i = 0; i < keys.length; i++) {
            lpTable.put(keys[i], i);
        }


        for (String key : lpTable.keys()) {
            StdOut.println(key);
        }
        StdOut.println();

        StdOut.println(lpTable.get("E"));
        StdOut.println("\n");

        lpTable.print();
        StdOut.println();

        lpTable.delete("E");
        lpTable.print();
        StdOut.println();

        lpTable.delete("A");
        lpTable.print();
        StdOut.println();

    }

    public static void main(String[] args) {
        check();
    }

}