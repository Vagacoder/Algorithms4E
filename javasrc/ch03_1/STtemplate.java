package javasrc.ch03_1;

/*
* Sysmbol table template
*/

import lib.*;

public class STtemplate<Key extends Comparable<Key>, Value>{


    private int size;

    public void put(Key key, Value val){

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

    public Iterable<Key> keys(Key low, Key high){
        return null;
    }

    public Iterable<Key> keys(){
        return keys(min(), max());
    }

    public static void check(int minlen){

        StdOut.println("1. testing short string array ...");

        // TODO: change class here
        STtemplate<String, Integer> st =  new STtemplate<>();
        String[] strs = {"S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E"};
        for(int i = 0; i < strs.length; i++){
            st.put(strs[i], i);
        }
        for(String s: st.keys()){
            StdOut.println(s + " " + st.get(s));
        }

        // * from FrequencyCounter
        StdOut.println("2. testing using frequency counter ... ");

        // TODO: change class here
        st =  new STtemplate<>();
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
        check(Integer.parseInt(args[0]));
    }
}