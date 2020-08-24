package javasrc.ch05_1;

/*
 * 5.1.9 Develop an implementation of LSD string sort that works for variable-length 
 * strings.
 * 
 
 ? Idea #1: find max length, append extra char to strings whose length is shorter
 ? than max length.

 ! Idea #2: use home made charAt(int d) which return -1 when d is out of string. 
 ? any string return -1 will be sort to top of array. Therefore, the count[] is 
 ? like MSD, which is R+2. Still need find max length.


 */

import lib.*;

public class LSDvarLength {

    public static void sort(String[] a) {

        int N = a.length;
        int R = 256;
        String[] aux = new String[N];

        // * find max length in a
        int W = 0;
        for (int i = 0; i < N; i++) {
            if (a[i].length() > W) {
                W = a[i].length();
            }
        }

        for (int d = W - 1; d >= 0; d--) {

            // * sort the digit at dth char
            int[] count = new int[R + 2];

            // * 1. Computer frequency counts
            for (int i = 0; i < N; i++) {
                count[charAt(a[i], d) + 2]++;
            }

            // * 2. Transform counts to indices
            for (int r = 0; r < R; r++) {
                count[r + 1] += count[r];
            }

            // * 3 Distribute
            for (int i = 0; i < N; i++) {
                aux[count[charAt(a[i], d) + 1]++] = a[i];
            }

            // * 4. Copy back
            for (int i = 0; i < N; i++) {
                a[i] = aux[i];
            }
        }
    }

    private static int charAt(String s, int d) {
        if (d < s.length()) {
            return s.charAt(d);
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        // * test #1
        StdOut.println("1. test words3.txt");
        String filename = "data/words3.txt";
        String[] input = new In(filename).readAllStrings();
        StdOut.println("Before sort");
        for (String s : input) {
            StdOut.println(s);
        }

        LSDvarLength.sort(input);
        StdOut.println("\nAfter sort");
        for (String s : input) {
            StdOut.println(s);
        }

        // * test #2
        StdOut.println("\n2. test shells.txt");
        filename = "data/shells.txt";
        input = new In(filename).readAllStrings();

        // ? test empty string, charAt() return -1
        // input[0] = "";

        StdOut.println("Before sort:");
        for (String s : input) {
            StdOut.println(s);
        }

        LSDvarLength.sort(input);
        StdOut.println("\nAfter sort:");
        for (String s : input) {
            StdOut.println(s);
        }
    }

}