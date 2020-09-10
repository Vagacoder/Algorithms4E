package javasrc.ch05_2;

/*
* 5.2.6 Implement the following API, for a StringSET data type:
*/

import javasrc.ch01_3.LinkedListQueue;
import lib.*;

public class StringSet {

    private TrieST<Integer> set;
    private int size = 0;
    private Iterable<String> keys;
    
    public StringSet(){
        this.set = new TrieST<>();
        this.keys = new LinkedListQueue<String>();
    }

    public void add(String key){
        if(!this.contains(key)){
            this.set.put(key, this.size++);
        }
    }

    public void delete(String key){
        if(this.contains(key)){
            this.set.delete(key);
            this.size--;
        }
    }

    public boolean contains(String key){
        this.keys = this.set.keys();
        for(String k: this.keys){
            if (k.equals(key)){
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

    public String toString(){
        this.keys = this.set.keys();
        return this.keys.toString();
    }

    public static void main(String[] args){

        StringSet set = new StringSet();
        String[] strs = {"this", "is", "a", "good", "day", "to", "die"};

        StdOut.println("Size: " + set.size());

        for (int i = 0; i < strs.length; i++){
            set.add(strs[i]);
            StdOut.println("Add: \"" + strs[i] + "\"");
            StdOut.println("Size: " + set.size());
        }

        set.add("good");
        StdOut.println("Add: \"good\"");
        StdOut.println("Size: " + set.size());

        set.delete("iss");
        StdOut.println("Delete: \"iss\"");
        StdOut.println("Size: " + set.size());

        for (int i = 0; i < strs.length; i++){
            set.delete(strs[i]);
            StdOut.println("Delete: \"" + strs[i] + "\"");
            StdOut.println("Size: " + set.size());
        }

        set.delete("this");
        StdOut.println("Delete: \"this\"");
        StdOut.println("Size: " + set.size());
    }
}
