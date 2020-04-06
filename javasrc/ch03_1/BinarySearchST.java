package javasrc.ch03_1;

import javasrc.ch01_3.LinkedListQueue;

/*
* Algorithm 3.2 Binary Search Symbol Table (in an ordered array) P. 379

* Proposition b. Binary search in an ordered array with N keys uses no more than
lg N + 1 compares for a search (successful or unsuccessful).

* proposition b (continued). Inserting a new key into an ordered array of size N 
uses ~ 2N array accesses in the worst case, so inserting N keys into an initially 
empty table uses ~ N^2 array accesses in the worst case.

* method    O()
! put()     N           this one is too slow for large dataset
get()       logN
delete()    N
contains()  logN
size()      1
min()       1
max()       1
floor()     logN
ceiling()   logN
rank()      logN
select()    1
deleteMin() N
deleteMax() 1

*/

import lib.*;

public class BinarySearchST<Key extends Comparable<Key>, Value>{

    private Key[] keys;
    private Value[] values;
    private int size;

    public BinarySearchST(int capacity){
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }

    public int size(){
        return this.size;
    }

    public boolean isEmpty(){
        return this.size==0;
    }

    public Value get(Key key){
        if(isEmpty()){
            return null;
        }

        int i = rank(key);

        if(i < this.size && keys[i].compareTo(key) == 0) {
            return values[i];
        } else {
            return null;
        }
    }

    public boolean contains(Key key){
        return get(key) != null;
    }

    public void put(Key key, Value value){
        int i = rank(key);
        if(i < this.size && keys[i].compareTo(key) == 0){
            values[i] = value;
            return;
        }
        for(int j = size; j > i; j--){
            keys[j] = keys[j-1];
            values[j] = values[j-1];
        }
        keys[i] = key;
        values[i] = value;
        this.size++;
    }

    // ! Important method, need fully understand it 
    public int rank(Key key){
        int low = 0, high = this.size - 1;
        while( low <= high){
            int mid = (low + high) /2;
            int cmp = key.compareTo(keys[mid]);
            if(cmp < 0){
                high = mid - 1;
            } else if(cmp > 0){
                low = mid + 1;
            } else {
                return mid;
            }
        }
        // * if input key is not found, returned index is the one whose key is 
        // * just larger than input index
        return low;
    }

    public Key min(){
        return keys[0];
    }

    public Key max(){
        return keys[this.size - 1];
    }

    public Key select(int k){
        return keys[k];
    }

    public Key ceiling(Key key){
        int i = rank(key);
        return keys[i];
    }

    public Key floor(Key key){
        int i = rank(key);
        if(keys[i].equals(key)){
            return keys[i];
        } else {
            return keys[i-1];
        }
    }

    public void delete(Key key){

    }

    public Iterable<Key> keys(Key low, Key high){
        LinkedListQueue<Key> q = new LinkedListQueue<>();
        for(int i = rank(low); i< rank(high); i++){
            q.enqueue(keys[i]);
        }
        if(contains(high)){
            q.enqueue(keys[rank(high)]);
        }

        return q;
    }

    public Iterable<Key> keys(){
        return keys(min(), max());
    }

    public void printAll(){
        for(int i = 0 ; i<this.size; i++){
            StdOut.println("(" + this.keys[i] + " : " + this.values[i] + ")");
        }
    }

    public static void main(String[] args){
        BinarySearchST<String, Integer> st = new BinarySearchST<>(10);
        st.put("A", 1);
        st.put("B", 2);

        st.printAll();
    }
}