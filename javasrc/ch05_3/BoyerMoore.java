package javasrc.ch05_3;

/*
 * Algorithm 5.7 Boyer-Moore Substring Search (mismatched character heuristic). P.772
 * 
 * Property O. On typical inputs, substring search with the Boyer-Moore mismatched
 * character heuristic uses ~N/M character compares to search for a pattern of length
 * M in a text of length N.

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

    public static void main(String[] args) {
        String pattern = "AACAA";
        // String pattern = "ABABAC";
        String txt = "AAVRAACADABRAACAADABRA";
        BoyerMoore bm = new BoyerMoore(pattern);
        StdOut.println("text:    " + txt);
        int offset = bm.search(txt);
        StdOut.print("pattern: ");
        for (int i = 0; i < offset; i++) {
            StdOut.print(" ");
        }
        StdOut.println(pattern);
    }

}
