package javasrc.ch02_3;

/*
* 2.3.16 Best case. Official solution
Write a program that produces a best-case array (with no duplicates) for sort() 
in Algorithm 2.5: 
    an array of N items with distinct keys having the property that every partition 
    will produce subarrays that differ in size by at most 1 (the same subarray 
    sizes that would happen for an array of N equal keys). 
    
    For the purposes of this exercise, ignore the initial shuffle.
    
*/


/******************************************************************************
 *  Compilation:  javac QuickBest.java
 *  Execution:    java QuickBest n
 *  Dependencies: StdOut.java
 *  
 *  Generate a best-case input of size n for standard quicksort.
 *
 *  % java QuickBest 3
 *  BAC
 *
 *  % java QuickBest 7
 *  DACBFEG
 *
 *  % java QuickBest 15
 *  HACBFEGDLIKJNMO
 *
 ******************************************************************************/

 import lib.*;

public class BestCaseQuick {

    // postcondition: a[lo..hi] is best-case input for quicksorting that subarray
    private static void best(int[] a, int lo, int hi) {

        // precondition:  a[lo..hi] contains keys lo to hi, in order
        for (int i = lo; i <= hi; i++)
            assert a[i] == i;

        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        best(a, lo, mid-1);
        best(a, mid+1, hi);
        exch(a, lo, mid);
    }

    public static int[] best(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = i;
        best(a, 0, n-1);
        return a;
    }

    // exchange a[i] and a[j]
    private static void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    public static void main(String[] args) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int n = Integer.parseInt(args[0]);
        int[] a = best(n);
        for (int i = 0; i < n; i++)
            // StdOut.println(a[i]);
            StdOut.print(alphabet.charAt(a[i]));
        StdOut.println();
    }
}


