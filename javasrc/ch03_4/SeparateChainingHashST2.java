package javasrc.ch03_4;

/*
! copy from SeparateChainingHashST.java
! for ex3.4.18

* Algorithm 3.5 Hashing with separate chaining, P. 465

* Proposition K. In a separate-chaining hash table with M lists and N keys, the 
probability (under Assumption J) that the number of keys in a list is within a 
small constant factor of N/M is extremely close to 1.

* Property L. In a separate-chaining hash table with M lists and N keys, the number
of compares (equality tests) for search miss and insert is ~N/M.

* Proposition N. Suppose a hash table is built with array resizing, starting with
an empty table. Under Assumption J, any sequence of t search, insert, and delete
symbol-table operations is executed in expected time proportional to t and with
memory usage always within a constant factor of the number of keys in the table.

* 3.4.9 Implement an eager delete() method for SeparateChainingHashST.

* 3.4.18 Add a constructor to SeparateChainingHashST that gives the client the ability
to specify the average number of probes to be tolerated for searches. Use array resizing
to keep the average list size less than the specified value, and use the technique described
on page 478 to ensure that the modulus for hash() is prime.

* 3.4.19 Implement keys() for SeparateChainingHashST and LinearProbingHashST

* 3.4.27 Double probing. Modify SeparateChainingHashST to use a second hash func-
tion and pick the shorter of the two lists. Give a trace of the process of inserting the keys
E A S Y Q U T I O N in that order into an initially empty table of size M =3 using
the function 11 k % M (for the kth letter) as the first hash function and the function
17 k % M (for the kth letter) as the second hash function. Give the average number of
probes for random search hit and search miss in this table.

* 3.4.30 Chi-square statistic. Add a method to SeparateChainingHashST to compute
the Chi 2 statistic for the hash table. With N keys and table size M, this number 
is defined by the equation: ... 




*/

import lib.*;
import javasrc.ch01_3.LinkedListQueue;
import javasrc.ch03_1.SequentialSearchST;

public class SeparateChainingHashST2<Key, Value> {

    public static final int INITIAL_SIZE = 10;
    public static final int NUMBER_OF_PROBING = 5;

    // * hash table size
    private int M;
    
    // *  
    private int lgM;
    private int[] primes = {
        1, 2, 3, 5, 7,
        31, 61, 127, 251, 509,
        1021, 2039, 4093, 8191, 16381,
        32749, 65521, 131071, 262139, 524287,
        1048573, 2097143, 4194301, 8388593, 16777213,
        33554393, 67108859, 134217689, 268435399, 536870909,
        1073741789, 2147483647
    };

    // * element number in table
    private int N = 0;

    // * number of probing tolerated
    private int T;

    private SequentialSearchST<Key, Value>[] table;        
    
    public SeparateChainingHashST2(){
        this(INITIAL_SIZE, NUMBER_OF_PROBING);
    }

    public SeparateChainingHashST2(int M, int numberOfProbing){
        this.M = M;
        this.lgM = (int)Math.log(this.M);
        this.T = numberOfProbing;

        // * each entry of table is essentially a linked list
        table = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for(int i = 0; i < M; i++){
            table[i] = new SequentialSearchST();
        }
    }

    // * 3.4.18 new hash from P.487, using prime for hash first
    private int hash(Key key){
        int t = key.hashCode() & 0x7fffffff;
        if(lgM < 26) {
            t = t % primes[lgM+5];
        }
        return t % M;
    }

    // * 3.4.27
    private int hash11(char c){
        int k = c - 'a';
        int t = 11 * k % M;
        return t;
    }

    // * 3.4.27
    private int hash17(char c){
        int k = c - 'a';
        int t = 17 * k % M;
        return t;
    }

    // * return a non-negative int, used as array index
    private int hashOld(Key key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Value get(Key key){
        return table[hash(key)].get(key);
    }

    // * 3.4.18
    public void put(Key key, Value value){
        if(this.N / this.M > T){
            resize(2 * M);
        }
        int i = hash(key);
        if(!table[i].contains(key)){
            this.N++;
        }
        table[i].put(key, value);
    }

    // * 3.4.9
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
        SeparateChainingHashST2<Key, Value> temp = new SeparateChainingHashST2<>(n, this.T);
        for(int i = 0; i < M; i++){
            for(Key key : this.table[i].keys()){
                temp.put(key, this.table[i].get(key));
            }
        }
        this.M = temp.M;
        this.lgM = (int)Math.log(this.M);
        this.table = temp.table;
    }

    // * 3.4.19
    public Iterable<Key> keys(){
        LinkedListQueue<Key> keys = new LinkedListQueue<>();
        for(int i = 0; i < this.table.length; i ++){
            for(Key key: this.table[i].keys()){
                keys.enqueue(key);
            }
        }
        return keys;
    }

    // * 3.4.30
    public double getChiSquare(){
        double sumOfSq = 0;

        for(int i = 0; i < this.M; i++){
            int temp = table[i].size();
            sumOfSq += Math.pow((temp - (this.N * 1.0/this.M)), 2);
        }

        return sumOfSq * this.M / this.N;
    }

    public void print(){
        for(int i = 0; i < this.M; i++){
            this.table[i].print();
        }
    }

    public static void check(){
        SeparateChainingHashST2<String, Integer> scTable = new SeparateChainingHashST2<>();
        String[] keys = {"S", "E", "A", "R", "C", "H", "E","X", "A", "M", "P", "L", "E"};
        for(int i =0; i < keys.length; i++){
            scTable.put(keys[i], i);
        }
        for(String key: scTable.keys()){
            StdOut.println(key);
        }
        
        StdOut.println();
        StdOut.println(scTable.getChiSquare());
        scTable.print();

        StdOut.println();
        scTable.delete("C");
        scTable.print();
    }

    public static void main(String[] args){
        check();
    }
}