package javasrc.ch05_3;

/*
 * Brute-force substring search. P.760 and P.761
 * 
 * 
 */

import lib.*;

public class BruteForce {

    public static int search(String pattern, String txt) {
        int M = pattern.length();
        int N = txt.length();

        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++) {
                if (txt.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }
            if (j == M) {
                return i;
            }
        }
        return N;
    }

    // * this method is a bridge to KMP algorithm, P.761
    public static int search2(String pattern, String txt) {
        int i;
        int j;
        int M = pattern.length();
        int N = txt.length();

        for (i = 0, j = 0; i < N && j < M; i++) {
            if (txt.charAt(i) == pattern.charAt(j)) {
                j++;
            } else {
                i -= j; // ! important ! i points to the end of sequence already matched.
                j = 0;
            }
        }
        if (j == M) {
            return i - M;
        } else {
            return N;
        }
    }

    public static void main(String[] args) {
        String text = "This is a good day to die";
        String pattern = "die";
        int index = search(pattern, text);
        StdOut.printf("Index of pattern \"%s\" at: %d", pattern, index);
        StdOut.println();
    }

}
