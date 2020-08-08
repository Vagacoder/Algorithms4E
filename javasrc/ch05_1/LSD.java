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
 * Proposition B. LSD string sort STABLY sorts fixed-length strings.
 * 
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
            int[] count = new int[R+1];

            // * 1. Compute freqiency counts
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
    
    }
}