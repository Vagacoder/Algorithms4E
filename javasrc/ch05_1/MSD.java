package javasrc.ch05_1;

/*
* Algorithm 5.2 MSD string sort. P.712 
* 
* 
*/

import javasrc.ch02_1.Insertion;
import javasrc.ch02_1.InsertionRange;
import lib.*;

public class MSD {

    // * radix
    private static int R = 256;
    // * cutoff for small subarrays
    private static final int M = 15;
    // * auxiliary array for distribution
    private static String[] aux;

    // ! Important helper to replace regular string.charAt()
    private static int charAt(String s, int d) {
        if (d < s.length()) {
            return s.charAt(d);
        } else {
            return -1;
        }
    }

    public static void sort(String[] a, int low, int high, int d) {
        if(high <= low + M){
            // TODO check insertion sort(), ensure matching the one in textbook
            InsertionRange.sort(a, low, high);
        }

        // * Compute frequency counts
        int[] count = new int[R + 2];
        
        for(int i = low; i <= high; i++){
            count[charAt(a[i], d) + 2]++;
        }

        // * Transform counts to indeces
        for(int r = 0; r < R + 1; r++){
            count[r + 1] += count[r];
        }

        // * Distribute
        for(int i = low; i <= high; i++){
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        }

        // * Copy back
        for(int i = low; i <= high; i++){
            a[i] = aux[i - low];
        }

        // ! Recursively sort for each character value
        for(int r = 0; r < R; r++){
            sort(a, low + count[r], low + count[r+1] - 1, d+1);
        }
    }

    public static void main(String[] args){

    }
}