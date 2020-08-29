package javasrc.ch05_1;

/*
 * 5.1.17 In-place key-indexed counting. Develop a version of key-indexed counting 
 * that uses only a constant amount of extra space. Prove that your version is 
 * stable or provide a counterexample.
 
 ? Idea:
 ? move count out of for loop, and each loop reset count to all 0s.

 */

import lib.*;

public class LSDconstantSpace {

    public static void sort(String[] a, int W) {

        // * Sort a[] on leading W characters.
        int N = a.length;
        int R = 256;
        String[] aux = new String[N];

        // ! Move count[] out of for loop
        int[] count = new int[R + 1];

        // * sort the strings W times
        for (int d = W - 1; d >= 0; d--) {

            // ! reset count[] to all 0s
            for(int i = 0; i < R+1; i++){
                count[i] = 0;
            }

            // * Sort by key-indexed counting on dth char
            // * 4 steps, P.703 - P.705

            // * 1. Compute frequency counts
            for (int i = 0; i < N; i++) {
                count[a[i].charAt(d) + 1]++;
            }

            // * 2. Transform counts to indices
            for (int r = 0; r < R; r++) {
                count[r + 1] += count[r];
            }

            // * 3. Distribute
            for (int i = 0; i < N; i++) {
                aux[count[a[i].charAt(d)]++] = a[i];
            }

            // * 4. Copy back
            for (int i = 0; i < N; i++) {
                a[i] = aux[i];
            }
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

        LSDconstantSpace.sort(input, input[0].length());
        StdOut.println("\nAfter sort");
        for (String s : input) {
            StdOut.println(s);
        }

        // * test #2
        StdOut.println("\n2. test plates.txt");
        filename = "data/plates.txt";
        input = new In(filename).readAllStrings();
        StdOut.println("Before sort");
        for (String s : input) {
            StdOut.println(s);
        }

        LSDconstantSpace.sort(input, input[0].length());
        StdOut.println("\nAfter sort");
        for (String s : input) {
            StdOut.println(s);
        }
    }
}