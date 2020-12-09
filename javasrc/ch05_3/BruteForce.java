package javasrc.ch05_3;

import java.util.ArrayList;

/*
 * Brute-force substring search. P.760 and P.761
 * 
 * eX 5.3.7 Add to our brute-force implementation of substring search a count() 
 * method to count occurrences and a searchAll() method to print all occurrences.
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

    // * 5.3.7 
    public static int count(String pattern, String txt){
        int N = txt.length();
        int M = pattern.length();
        int count = 0;

        for (int i = 0; i <= N-M; i++){
            int j = 0;
            while (txt.charAt(i+j) == pattern.charAt(j)){
                j++;
                if (j==M){
                    count++;
                    break;
                }
            }
        }

        return count;
    }

    // * 5.3.7
    public static ArrayList<Integer> searchAll(String pattern, String txt){
        int N = txt.length();
        int M = pattern.length();
        ArrayList<Integer> indices = new ArrayList<>();

        for (int i = 0; i <= N-M; i++){
            int j = 0;
            while (txt.charAt(i+j) == pattern.charAt(j)){
                j++;
                if (j==M){
                    StdOut.println(txt.substring(i, i+M));
                    indices.add(i);
                    break;
                }
            }
        }
        
        return indices;
    }

    public static void main(String[] args) {
        String text = "This is a good day to die, a day.";
        String pattern = "a";
        int index = search(pattern, text);
        StdOut.printf("Index of pattern \"%s\" at: %d", pattern, index);
        StdOut.println();

        // * Testers for Ex 5.3.7
        int occurency = count(pattern, text);
        StdOut.println(occurency);

        text = "Nice weekend is joyful.";
        occurency = count(pattern, text);
        StdOut.println(occurency);

        text = "Wonderful daaaay.";
        occurency = count(pattern, text);
        StdOut.println(occurency);

        pattern = "aa";
        occurency = count(pattern, text);
        StdOut.println(occurency);

        text = "We are good readers in this treepart";
        pattern = "re";
        StdOut.println(searchAll(pattern, text));


    }

}
