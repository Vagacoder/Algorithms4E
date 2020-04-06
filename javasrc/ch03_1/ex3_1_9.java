package javasrc.ch03_1;

/*
* 3.1.9 Add code to FrequencyCounter to keep track of the last call to put(). 
Print the last word inserted and the number of words that were processed in 
the input stream prior to this insertion. Run your program for tale.txt with 
length cutoffs 1, 8, and 10.

*/

import lib.*;

public class ex3_1_9{


    public static void main(String[] args) {

        int minlen = Integer.parseInt(args[0]);   
        
        // ! replace class below
        BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>(50000);
        
        String word = "";
        int count = 0;
        while (!StdIn.isEmpty()) { 
            word = StdIn.readString();
             // * Ignore short keys.
            if (word.length() < minlen)
                continue;          
            if (!st.contains(word))
                st.put(word, 1);
            else
                st.put(word, st.get(word) + 1);
            count++;
        }
        StdOut.println("Last input is: " + word);
        StdOut.println(count + " words have been processed");
        
        // Find a key with the highest frequency count.
        String max = "";
        st.put(max, 0);
        for (String w : st.keys())
            if (st.get(w) > st.get(max))
                max = w;
        StdOut.println(max + " " + st.get(max));
    }

}