package javasrc.ch05_1;

/*
* 5.1.1 Develop a sort implementation that counts the number of different key 
* values, then uses a symbol table to apply key-indexed counting to sort the 
* array. (This method is not for use when the number of different key values is 
* large.)
* 
* Use Symbol table, SequentialSearchST (ch3.1)

*/

import javasrc.ch03_1.SequentialSearchST;
import lib.*;

public class STSD {
    
    public static void sort(String[] a){

        int n = a.length;
        SequentialSearchST<String, Integer> st = new SequentialSearchST<>();

        // * 1. compute frequency counts
        for(int i =0; i<n; i++){
            String s = a[i];
            if(st.contains(s)){
                st.put(s, st.get(s) + 1);
            }else{
                st.put(s, 1);
            }
        }

        int[] indices = new int[n];
        // * 2. transform counts to indices
        
    }

}