package javasrc.ch03_1;

import javasrc.ch01_3.LinkedListQueue;

/*
* 3.1.12 Modify BinarySearchST to maintain one array of Item objects that contain
keys and values, rather than two parallel arrays. Add a constructor that takes an 
array of Item values as argument and uses mergesort to sort the array.

! Problem: for instance variable items, if I declare it as Item[], I cannot cast
! Object [] to Item[]. So that I have to keep items as Object[], and do casting 
! for every operation on elements of items. 
! There must some better way to do this.
*/

import lib.*;

public class BinarySearchObjectST<Key extends Comparable<Key>, Value>{

    class Item {
        Key key;
        Value value;

        Item(Key key, Value value){
            this.key = key;
            this.value = value;
        }

        // @Override
        // public int compareTo(BinarySearchObjectST<Key, Value>.Item that) {
        //     return this.key.compareTo(that.key);
        // }
    }

    private Object[] items;
    private int size;

    public BinarySearchObjectST(int capacity){
        this.size = 0;
        this.items = new Object[capacity];
    }

    public void put(Key key, Value val){
        Item newItem = new Item(key, val);
        int i = rank(key);
        if(i < this.size && ((Item) this.items[i]).key.compareTo(key) == 0){
            items[i] = newItem;
            return;
        }
        for (int j = this.size; j > i ; j--){
            items[j] = items[j-1];
        }
        items[i] = newItem;
        this.size++;
    }

    public Value get(Key key){
        return null;
    }

    public void delete(Key key){

    }
    
    public boolean contains(Key key){
        return false;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public int size(){
        return this.size;
    }

    public Key min(){
        return ((Item)items[0]).key;
    }

    public Key max(){
        return ((Item) items[this.size - 1]).key;
    }

    public Key floor(Key key){
        return null;
    }

    public Key ceiling(Key key){
        return null;
    }

    public int rank(Key key){
        int low = 0, high = this.size -1;
        while (low <= high){
            int mid = (low + high)/2;
            int cmp = key.compareTo(((Item) items[mid]).key);
            if(cmp < 0){
                high = mid - 1;
            }else if (cmp > 0){
                low = mid + 1;
            }else {
                return mid;
            }
        }
        return low;
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
            return rank(high) - rank(low);  // ! ??
        }
    }

    public Iterable<Item> keys(Key low, Key high){
        LinkedListQueue<Item> q = new LinkedListQueue<>();
        for(int i = rank(low); i< rank(high); i++){
            q.enqueue((Item) items[i]);
        }
        if(contains(high)){
            q.enqueue((Item)items[rank(high)]);
        }

        return q;
    }

    public Iterable<Item> keys(){
        return keys(min(), max());
    }

    public static void check(int minlen){

        StdOut.println("1. testing short string array ...");

        // TODO: change class here
        BinarySearchObjectST<String, Integer> st =  new BinarySearchObjectST<>(15);
        String[] strs = {"S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E"};
        for(int i = 0; i < strs.length; i++){
            st.put(strs[i], i);
        }
        for(BinarySearchObjectST.Item s: st.keys()){
            StdOut.println(s.key + " " + s.value);
        }

        // * from FrequencyCounter
        StdOut.println("2. testing using frequency counter ... ");

        // TODO: change class here
        st =  new BinarySearchObjectST<>(15);
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
        for(BinarySearchObjectST<String, Integer>.Item item: st.keys()){
            if(item.value > st.get(max)){
                max = item.key;
            }
        }
        StdOut.println(max + " " + st.get(max));
    }

    public static void main(String[] args){
        // check(Integer.parseInt(args[0]));
        check(1);
    }
}