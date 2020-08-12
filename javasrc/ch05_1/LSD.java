package javasrc.ch05_1;

/*
 * Algorithm 5.1 LSD (Least Significant Digit) string sort. P.707
 * 
 * Explain:
 * The strings are each of length W, sort the strings W times with key-indexed 
 * counting, using each of the positions as the key, proceeding from right to 
 * left.
 * 
 * Proposition A. Key-indexed counting uses 11N + 4 R + 1 array accesses to stably 
 * sort N items whose keys are integers between 0 and R - 1. P.705
 * 
 * Proposition A ext. LSD use d * (11N + 4R +1) array accesses, still linear (in term of N).
 * 
 * Proposition B. LSD string sort STABLY sorts fixed-length strings.
 * 
 * Proposition B (continued). LSD string sort uses ~7WN + 3WR array accesses and 
 * extra space proportional to N + R to sort N items whose keys are W-character 
 * strings taken from an R-character alphabet.
 * 
 * Proposition B implies that the total running time is proportional to WN. An 
 * input array of N strings that each have W characters has a total of WN characters, 
 * so the running time of LSD string sort is linear in the size of the input.
 * 
  
 ? Sample file:
 ? data/plates.txt
 ? data/words3.txt 

*/

import lib.*;

public class LSD{

    public static void sort(String[] a, int W){

        // * Sort a[] on leading W characters.
        int N = a.length;
        int R = 256;
        String[] aux = new String[N];

        // * sort the strings W times
        for(int d = W-1; d >= 0; d--){

            // * Sort by key-indexed counting on dth char
            // * 4 steps, P.703 - P.705
            // ! Note the size of count is R + 1
            int[] count = new int[R+1];

            // * 1. Compute frequency counts
            for(int i = 0; i < N; i++){
                count[a[i].charAt(d) + 1]++;
            }

            // * 2. Transform counts to indices
            for(int r = 0; r < R; r++){
                count[r+1] += count[r];
            }

            // * 3. Distribute
            for(int i = 0; i < N; i++){
                aux[count[a[i].charAt(d)]++] = a[i];
            }

            // * 4. Copy back
            for(int i = 0; i < N; i++){
                a[i] = aux[i];
            }
        }
    }

    public static void main(String[] args){
        // * test #1
        StdOut.println("1. test words3.txt");
        String filename = "data/words3.txt";
        String[] input = new In(filename).readAllStrings();
        StdOut.println("Before sort");
        for(String s: input){
            StdOut.println(s);
        }

        LSD.sort(input, input[0].length());
        StdOut.println("\nAfter sort");
        for(String s: input){
            StdOut.println(s);
        }

        // * test #2
        StdOut.println("\n2. test plates.txt");
        filename = "data/plates.txt";
        input = new In(filename).readAllStrings();
        StdOut.println("Before sort");
        for(String s: input){
            StdOut.println(s);
        }

        LSD.sort(input, input[0].length());
        StdOut.println("\nAfter sort");
        for(String s: input){
            StdOut.println(s);
        }
    }
}