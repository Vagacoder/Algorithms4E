package javasrc.ch03_1;

/*
* 3.1.2 Develop a symbol-table implementation ArrayST that uses an (unordered) array
as the underlying data structure to implement our basic symbol-table API.

* 3.1.22 Self-organizing search. A self-organizing search algorithm is one that 
rearranges items to make those that are accessed frequently likely to be found 
early in the search.

* Modify your search implementation for Exercise 3.1.2 to perform the following 
action on every search hit: move the key-value pair found to the beginning of the 
list, moving all pairs between the beginning of the list and the vacated position 
to the right one position. This procedure is called the move-to-front heuristic.

*/


import javasrc.ch01_3.LinkedListQueue;
import lib.*;

public class ArrayST<Key extends Comparable<Key>, Value>{

    private Key[] keys;
    private Value[] values;
    private int size;

    public ArrayST(int capacity){
        this.keys = (Key[]) new Comparable[capacity];
        this.values = (Value[]) new Object[capacity];
        this.size = 0;
    }

    public void put(Key key, Value val){
        for(int i = 0; i < this.size; i++){
            if(keys[i].equals(key)){
                values[i] = val;
                return;
            }
        }
        this.keys[size] = key;
        this.values[size] = val;
        this.size++;
    }

    public Value get(Key key){

        for(int i = 0; i < this.size; i++){
            if(keys[i].equals(key)){
                return values[i];
            }
        }
        return null;
    }

    // * 3.1.22
    public Value getFaster(Key key){
        for(int i = 0; i < this.size; i++){
            if(keys[i].equals(key)){
                Value resultValue = values[i];
                Key tempKey = keys[i];
                for(int j = 0; j < i; j++){
                    keys[j+1] = keys[j];
                    values[j+1] = values[j];
                }
                keys[0] = tempKey;
                values[0] = resultValue;
                return resultValue;
            }
        }
        return null;
    }

    public void delete(Key key){
        for(int i = 0; i < this.size; i++){
            if(keys[i].equals(key)){
                this.size--;
                this.keys[i] = keys[this.size];
                this.values[i] = values[this.size];
                this.keys[this.size] = null;
                this.values[this.size] = null;
                return;
            }
        }
    }
    
    public boolean contains(Key key){
        for(int i = 0; i < this.size; i++){
            if(keys[i].equals(key)){
                return true;
            }
        }

        return false;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public int size(){
        return this.size;
    }

    public Key min(){
        return null;
    }

    public Key max(){
        return null;
    }

    public Key floor(Key key){
        return null;
    }

    public Key ceiling(Key key){
        return null;
    }

    public int rank(Key key){
        return -1;
    }

    public Key select(int k){
        return null;
    }

    public void deleteMin(){
        delete(min());
    }

    public void deleteMax(){
        delete(max());
    }

    public int size(Key low, Key high){
        if(high.compareTo(low)<0){
            return 0;
        } else if(contains(high)){
            return rank(high) - rank(low) + 1;
        } else{
            return rank(high) - rank(low);
        }
    }

    // public Iterable<Key> keys(Key low, Key high){
    //     return null;
    // }

    public Iterable<Key> keys(){
        LinkedListQueue<Key> ikeys = new LinkedListQueue<>();
        for(int i = 0; i<this.size; i++){
            ikeys.enqueue(this.keys[i]);
        }
        return ikeys;
    }

    public static void check(int minlen){

        StdOut.println("1. testing short string array ...");
        
        // ! change class here!
        String[] strs = {"S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E"};
        ArrayST<String, Integer> st =  new ArrayST<>(strs.length);
        for(int i = 0; i < strs.length; i++){
            st.put(strs[i], i);
        }
        for(String s: st.keys()){
            StdOut.println(s + " " + st.getFaster(s));
        }

        // * from FrequencyCounter
        StdOut.println("2. testing using frequency counter ... ");
        
        // ! change class here!
        st =  new ArrayST<>(100);
        while(!StdIn.isEmpty()){
            String word = StdIn.readString();
            if(word.length() < minlen){
                continue;
            }
            if(!st.contains(word)){
                st.put(word,1);
            }else{
                st.put(word, st.get(word) + 1);
            }
        }
        String max ="";
        st.put(max, 0);
        for(String word: st.keys()){
            if(st.get(word)> st.get(max)){
                max = word;
            }
        }
        StdOut.println(max + " " + st.get(max));
    }

    public static void main(String[] args){
        check(1);
    }
}