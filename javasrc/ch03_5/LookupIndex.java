package javasrc.ch03_5;

import javasrc.ch03_4.SeparateChainingHashST;
import javasrc.ch01_3.LinkedListQueue;

/*
* Index (and inverted index) lookup
*/

import lib.*;

public class LookupIndex {
    
    public static void main(String[] args){
        In in = new In(args[0]);
        String sp = args[1];

        SeparateChainingHashST<String, LinkedListQueue<String>> st = new SeparateChainingHashST<>();
        SeparateChainingHashST<String, LinkedListQueue<String>> ts = new SeparateChainingHashST<>();

        // * build up index table and inverted index table
        while (in.hasNextLine()){
            String[] a = in.readLine().split(sp);
            String key = a[0];
            
            for(int i = 1; i < a.length; i++){
                String value = a[i];
                if(st.get(key) == null){
                    st.put(key, new LinkedListQueue<String>());
                }
                if(ts.get(value) == null){
                    ts.put(value, new LinkedListQueue<String>());
                }
                st.get(key).enqueue(value);
                ts.get(value).enqueue(key);
            }
        }

        // * search
        while(!StdIn.isEmpty()){
            String query = StdIn.readLine();
            if(st.get(query) != null){
                for(String s: st.get(query)){
                    StdOut.println(" " + s);
                }
            }

            if(ts.get(query) != null){
                for(String s: ts.get(query)){
                    StdOut.println(" " + s);
                }
            }
        }

    }


}