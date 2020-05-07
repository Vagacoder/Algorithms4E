package javasrc.ch03_4;

/*
* Algorithm 3.5 Hashing with separate chaining, P. 465

* Proposition K. In a separate-chaining hash table with M lists and N keys, the 
probability (under Assumption J) that the number of keys in a list is within a 
small constant factor of N/M is extremely close to 1.

* Property l. In a separate-chaining hash table with M lists and N keys, the number
of compares (equality tests) for search miss and insert is ~N/M.

* Proposition N. Suppose a hash table is built with array resizing, starting with
an empty table. Under Assumption J, any sequence of t search, insert, and delete
symbol-table operations is executed in expected time proportional to t and with
memory usage always within a constant factor of the number of keys in the table.

*/

import lib.*;
import javasrc.ch03_1.SequentialSearchST;

public class SeparateChainingHashST<Key, Value> {

    public static final int INITIAL_SIZE = 997;
    // * hash table size
    private int M;
    // * element number in table
    private int N = 0;
    private SequentialSearchST<Key, Value>[] table;        
    
    public SeparateChainingHashST(){
        this(INITIAL_SIZE);
    }

    public SeparateChainingHashST(int M){
        this.M = M;
        // * each entry of table is essentially a linked list
        table = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for(int i = 0; i < M; i++){
            table[i] = new SequentialSearchST();
        }
    }

    // * return a non-negative int, used as array index
    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Value get(Key key){
        return table[hash(key)].get(key);
    }

    public void put(Key key, Value value){
        if (N >= M * 3) {
            resize(2 * M);
        }
        int i = hash(key);
        if(!table[i].contains(key)){
            this.N++;
        }
        table[i].put(key, value);
    }

    public boolean delete(Key key){
        boolean isDeleted = false;
        int hash = hash(key);
        if(table[hash].contains(key)){
            this.N--;
            table[hash].delete(key);
            isDeleted = true;
        }

        if( (M > INITIAL_SIZE && N <= M)){
            resize(M/2);
        }
        return isDeleted;
    }

    private void resize(int n) {
        SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<>(n);
        for(int i = 0; i < M; i++){
            for(Key key : this.table[i].keys()){
                temp.put(key, this.table[i].get(key));
            }
        }
        this.M = temp.M;
        this.table = temp.table;
    }

    public Iterable<Key> keys(){
        // TODO
        return null;
    }

    public void print(){
        for(int i = 0; i < this.M; i++){
            this.table[i].print();
        }
    }

    public static void check(){
        SeparateChainingHashST<String, Integer> scTable = new SeparateChainingHashST<>();
        String[] keys = {"S", "E", "A", "R", "C", "H", "E","X", "A", "M", "P", "L", "E"};
        for(int i =0; i < keys.length; i++){
            scTable.put(keys[i], i);
        }
        scTable.print();
        StdOut.println();
        scTable.delete("C");
        scTable.print();
    }

    public static void main(String[] args){
        check();
    }
}