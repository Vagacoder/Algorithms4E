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

public class BestCase{


    public static void getBestCase(Comparable[] a){
        if(!isSorted(a)){
            StdOut.println("Input array must be sorted!");
            return;
        }
        getBestCase(a, 0, a.length - 1);
    }

    private static void getBestCase(Comparable[] a, int low, int high){
        if( high <= low){
            return;
        }
        int mid = (low + high) /2;
        Comparable pivot = a[mid];
        for(int i = mid - 1; i >= low; i --){
            a[i+1] = a[i];
        }
        a[low] = pivot;
        getBestCase(a, low+1, mid);
        getBestCase(a, mid+1, high);
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

    public static void main(String[] args){

        Integer[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        getBestCase(a);
        StdOut.println("The best case of a is:");
        show(a);
        ex2_3_13.sort(a);
        StdOut.println(ex2_3_13.getCN());
    }   

}