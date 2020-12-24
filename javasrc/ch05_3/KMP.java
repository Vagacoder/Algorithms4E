package javasrc.ch05_3;

import java.util.ArrayList;

import javasrc.ch01_3.LinkedListQueue;

/*
 * Algorithm 5.6 Knuth-Morris-Pratt substring search. P.768
 * 
 * Proposition N. Knuth-Morris-Pratt substring search accesses no more than M + N 
 * characters to search for a pattern of length M in a text of length N.
 * 
 * Ex 5.3.8 Add to KMP a count() method to count occurrences and a searchAll() 
 * method to print all occurrences.
 * 
 * Ex 5.3.24 Find all occurrences. Add a method findAll() to each of the four 
 * substring search algorithms given in the text that returns an Iterable<Integer> 
 * that allows clients to iterate through all offsets of the pattern in the text.
 * 
 * Ex 5.3.25 Streaming. Add a search() method to KMP that takes variable of type 
 * In as argument, and searches for the pattern in the specified input stream 
 * without using any extra instance variables. Then do the same for RabinKarp.
 * 
 * 
*/

 import lib.*;

public class KMP {

    private String pattern;
    private int[][] dfa;

    public KMP(String pattern){
        this.pattern = pattern;
        int M = pattern.length();
        int R = 256;
        this.dfa = new int[R][M];
        this.dfa[pattern.charAt(0)][0] = 1;

        int X = 0;
        for (int j = 1; j < M; j++){
            for (int c = 0; c < R; c++){
                dfa[c][j] = dfa[c][X];
            }
            dfa[pattern.charAt(j)][j] = j+1;
            X = dfa[pattern.charAt(j)][X];
        }
    }

    public int search(String txt){
        int i = 0;
        int j = 0;
        int N = txt.length();
        int M = this.pattern.length();

        for (; i < N && j < M; i++){
            j = dfa[txt.charAt(i)][j];
        }

        if (j == M){
            return i - M;
        }else{
            return N;
        }
    }

    // * 5.3.25
    public int search(In in){
        // TODO

        return -1;
    }

    // * 5.3.8.
    public int count(String txt){
        int count = 0;
        int i = 0;
        int j = 0;
        int N = txt.length();
        int M = this.pattern.length();

        while (i < N){
            j = dfa[txt.charAt(i)][j];
            if (j == M){
                count ++;
                j--;
            }
            i++;
        }
        return count;
    }

    // * 5.3.8.
    public ArrayList<Integer> searchAll(String txt){ 
        ArrayList<Integer> result = new ArrayList<>();
        int i = 0;
        int j = 0;
        int N = txt.length();
        int M = this.pattern.length();

        while (i < N){
            j = dfa[txt.charAt(i)][j];
            if (j == M){
                result.add(i-M+1);
                j--;
            }
            i++;
        }

        return result;
    }

    // * Ex 5.3.24
    public Iterable<Integer> findAll(String txt){
        LinkedListQueue<Integer> result = new LinkedListQueue<>();
        int n = txt.length();
        int m = this.pattern.length();
        int i = 0;
        int j = 0;

        while (i < n) {
            j = this.dfa[txt.charAt(i)][j];
            if (j == m){
                result.enqueue(i-m+1);
                j--;
            }
            i++;
        }
        return result;
    }
    
    public static void main(String[] args){
        // String pattern = "AACAA";
        // String pattern = "ABABAC";
        String pattern = "ABAABCABAABCB";
        String txt = "AAVRAACADABRAACAADABRA";
        KMP kmp = new KMP(pattern);
        StdOut.println("text:    " + txt);
        int offset = kmp.search(txt);
        StdOut.print("pattern: ");
        for(int i = 0; i < offset; i++){
            StdOut.print(" ");
        }
        StdOut.println(pattern);
        StdOut.println();

        txt = "This is a good day to die, a day.";
        pattern = "a";
        kmp = new KMP(pattern);
        int occurency = kmp.count(txt);
        StdOut.println(occurency);

        txt = "Nice weekend is joyful.";
        pattern = "a";
        kmp = new KMP(pattern);
        occurency = kmp.count(txt);
        StdOut.println(occurency);

        txt = "Wonderful daaaay.";
        pattern = "a";
        kmp = new KMP(pattern);
        occurency = kmp.count(txt);
        StdOut.println(occurency);
        StdOut.println(kmp.searchAll(txt));

        pattern = "aa";
        kmp = new KMP(pattern);
        occurency = kmp.count(txt);
        StdOut.println(occurency);

        pattern = "aaa";
        kmp = new KMP(pattern);
        occurency = kmp.count(txt);
        StdOut.println(occurency);

        pattern = "aaaa";
        kmp = new KMP(pattern);
        occurency = kmp.count(txt);
        StdOut.println(occurency);
        StdOut.println(kmp.searchAll(txt));

        pattern = "aaaaa";
        kmp = new KMP(pattern);
        occurency = kmp.count(txt);
        StdOut.println(occurency);
        StdOut.println(kmp.searchAll(txt));
    }
}
