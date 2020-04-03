package javasrc.ch03_1;

/*
* FrequencyCounter is a symbol-table client that finds the number of occurrences 
of each string (having at least as many characters as a given threshold length) 
in a sequence of strings from standard input, then iterates through the keys to 
find the one that occurs the most frequently.

* Throughout this chapter, we measure performance of this client with three reference 
inputs: 

* 1) The first five lines of C. Dickens’s Tale of Two Cities (tinyTale.txt), 
* 2) The full text of the book (tale.txt), 
* 3) A popular database of 1 million sentences taken at random from the web that 
is known as the Leipzig Corpora Collection (leipzig1M.txt).
! use leipzig100k.txt instead of leipzig1M.txt
*/

import lib.*;

public class FrequencyCounter {

    public static void main(String[] args) {

        // ? first argument is minimum length of words which are counted.
        // key-length cutoff
        int minlen = Integer.parseInt(args[0]);         
        STtemplate<String, Integer> st = new STtemplate<String, Integer>();
        
        while (!StdIn.isEmpty()) { 
            // Build symbol table and count frequencies.
            String word = StdIn.readString();
            if (word.length() < minlen)
                continue;           // ! Ignore short keys.
            if (!st.contains(word))
                st.put(word, 1);
            else
                st.put(word, st.get(word) + 1);
        }
        
        // Find a key with the highest frequency count.
        String max = "";
        st.put(max, 0);
        for (String word : st.keys())
            if (st.get(word) > st.get(max))
                max = word;
        StdOut.println(max + " " + st.get(max));
    }

}