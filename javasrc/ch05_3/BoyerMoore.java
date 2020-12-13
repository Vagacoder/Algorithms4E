package javasrc.ch05_3;

import java.util.ArrayList;

/*
 * Algorithm 5.7 Boyer-Moore Substring Search (mismatched character heuristic). P.772
 * 
 * Property O. On typical inputs, substring search with the Boyer-Moore mismatched
 * character heuristic uses ~N/M character compares to search for a pattern of length
 * M in a text of length N.
 * 
 * Ex 5.3.9 Add to BoyerMoore a count() method to count occurrences and a searchAll()
 * method to print all occurrences.
 * 
 * Ex 5.3.11 Construct a worst-case example for the Boyer-Moore implementation in 
 * Algorithm 5.7 (which demonstrates that it is not linear-time).
 * 
 * Ex 5.3.13 In the Boyer-Moore implementation in Algorithm 5.7, show that you 
 * can set right[c] to the penultimate occurrence of c when c is the last character 
 * in the pattern.
 * 
 */

import lib.*;

public class BoyerMoore {

    private int[] right;
    private String pattern;

    BoyerMoore(String pattern) {
        this.pattern = pattern;
        int M = pattern.length();
        int R = 256;
        right = new int[R];

        for (int c = 0; c < R; c++) {
            right[c] = -1;
        }
        for (int j = 0; j < M; j++) {
            right[pattern.charAt(j)] = j;
        }
    }

    public int search(String txt) {
        int N = txt.length();
        int M = this.pattern.length();
        int skip;

        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M - 1; j >= 0; j--) {
                if (this.pattern.charAt(j) != txt.charAt(i + j)) {
                    skip = j - right[txt.charAt(i + j)];
                    if (skip < 1) {
                        skip = 1;
                    }
                    break;
                }
            }
            if (skip == 0) {
                return i;
            }
        }
        return N;
    }

    // * 5.3.9
    public int count(String txt){
        int count = 0;
        int N = txt.length();
        int M = this.pattern.length();
        int i = 0;

        while (i <= N-M){
            int skip = 0;

            for (int j = M -1; j >=0; j--){
                if (this.pattern.charAt(j) != txt.charAt(i+j)){
                    skip = j - right[txt.charAt(i+j)];
                    if (skip < 1){
                        skip = 1;
                    }
                    break;
                }
            }
            if(skip == 0){
                count ++;
                skip = 1;
            }
            i += skip;
        }
        return count;
    }

    //  * 5.3.9.
    public ArrayList<Integer> searchAll(String txt){
        ArrayList<Integer> result = new ArrayList<>();
        int N = txt.length();
        int M = this.pattern.length();
        int i = 0;

        while (i <= N-M){
            int skip = 0;

            for (int j = M -1; j >=0; j--){
                if (txt.charAt(i+j) != this.pattern.charAt(j)){
                    skip = j - right[txt.charAt(i+j)];
                    if (skip < 1){
                        skip = 1;
                    }
                    break;
                }
            }

            if (skip == 0){
                result.add(i);
                skip = 1;
            }
            i += skip;
        }
        return result;
    }


    public static void main(String[] args) {
        // String pattern = "AACAA";
        // String pattern = "ABABAC";
        // String pattern = "AABAABAABCDACAB";
        String pattern = "ABRACADABRA";
        String txt = "AAVRAACADABRAACAADABRA";
        BoyerMoore bm = new BoyerMoore(pattern);
        StdOut.println("text:    " + txt);
        int offset = bm.search(txt);
        StdOut.print("pattern: ");
        for (int i = 0; i < offset; i++) {
            StdOut.print(" ");
        }
        StdOut.println(pattern);
        StdOut.println();

        txt = "This is a good day to die, a day.";
        pattern = "a";
        bm = new BoyerMoore(pattern);
        int occurency = bm.count(txt);
        StdOut.println(occurency);

        txt = "Nice weekend is joyful.";
        pattern = "a";
        bm = new BoyerMoore(pattern);
        occurency = bm.count(txt);
        StdOut.println(occurency);

        txt = "Wonderful daaaay.";
        pattern = "a";
        bm = new BoyerMoore(pattern);
        occurency = bm.count(txt);
        StdOut.println(occurency);
        StdOut.println(bm.searchAll(txt));

        pattern = "aa";
        bm = new BoyerMoore(pattern);
        occurency = bm.count(txt);
        StdOut.println(occurency);
        StdOut.println(bm.searchAll(txt));

        pattern = "aaa";
        bm = new BoyerMoore(pattern);
        occurency = bm.count(txt);
        StdOut.println(occurency);

        pattern = "aaaa";
        bm = new BoyerMoore(pattern);
        occurency = bm.count(txt);
        StdOut.println(occurency);

        pattern = "aaaaa";
        bm = new BoyerMoore(pattern);
        occurency = bm.count(txt);
        StdOut.println(occurency);
        StdOut.println(bm.searchAll(txt));

        // * ex 5.3.11 Worst case
        txt = "AAAAAAA";
        pattern = "CAA";
        bm = new BoyerMoore(pattern);
        int index = bm.search(txt);
        StdOut.println("Worst Case:");
        StdOut.println(index);
    }
    

}
