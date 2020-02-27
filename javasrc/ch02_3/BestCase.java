package javasrc.ch02_3;

/*
* 2.3.16 Best case. 
Write a program that produces a best-case array (with no duplicates) for sort() 
in Algorithm 2.5: 
    an array of N items with distinct keys having the property that every partition 
    will produce subarrays that differ in size by at most 1 (the same subarray 
    sizes that would happen for an array of N equal keys). 
    
    For the purposes of this exercise, ignore the initial shuffle.
    
    
*/

import lib.*;
import java.util.Arrays;

public class BestCase {

    public static void getBestCase(Comparable[] a) {
        if (!isSorted(a)) {
            StdOut.println("Input array must be sorted!");
            return;
        }

        getBestCase(a, 0, a.length - 1);
    }

    private static void getBestCase(Comparable[] a, int low, int high) {
        if (high <= low) {
            return;
        }
        int i = low;
        int mid = (low + high) / 2;
        exch(a, i, mid);
        int j = mid -1;
        int k = high;

        // ! while loop is dealing with lift half, since pivot inside left half,
        // ! we cannot use recursive;
        // ! recursive call on getBestCase() is dealing with right half.
        while (i < j) {
            // * find middle of range, set it to pivot
            int prevMid = mid;
            mid = (i + j)/2;

            // * if mid <= i, it suggests 2 elements range, no need change pivot
            if (mid <= i){
                // * but still need deal with right half;
                getBestCase(a, prevMid+1, k);
                break;
            }

            exch(a, prevMid, mid);
            j = mid -1;
            // * dealing with right half
            getBestCase(a, prevMid+1, k);
            // * update high boundary of right half
            k = prevMid - 1;
        }
    }

    // Test whether the array entries are in order.
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1]))
                return false;
        return true;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    // Print the array, on a single line.
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++)
            StdOut.print(a[i] + " ");
        StdOut.println();
    }

    public static void main(String[] args) {
        // ! manual input
        // Integer[] a = {5, 1, 0, 3, 4, 2, 8, 7, 6, 10, 9, 11};
        // getBestCase(a);
        // show(a);
        // ex2_3_13.sort(a);
        // StdOut.println("Stack level # : " + ex2_3_13.getCN());

        // ! Auto input
        int N = 13;
        Integer[] b = new Integer[N];
        for(int i = 0; i < N; i++){
            b[i] = i;
        }
        getBestCase(b);
        StdOut.println("The best case of a is:");
        show(b);
        ex2_3_13.sort(b);
        StdOut.println("Stack level # : " + ex2_3_13.getCN());
        StdOut.println("after sorting ... ");
        show(b);
        StdOut.println();

        // ! Auto test
        StdOut.println("Testing difference size of arrays ... ");
        for (int n = 4; n < 10000; n*=2){
            Integer[] c = new Integer[n];
            for(int i = 0; i < n; i++){
                c[i] = i;
            }
            getBestCase(c);
            ex2_3_13.sort(c);
            StdOut.println("For best case of array with " + c.length + " elements");
            StdOut.println("Stack level # : " + ex2_3_13.getCN());
            StdOut.println("log2(" + n + ") is " + Math.log(n)/Math.log(2));
        }
    }

}