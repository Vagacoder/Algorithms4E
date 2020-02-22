package javasrc.ch02_3;

/*
* Algorithm 2.5 Quicksort  P.289

* Proposition k. 
Quicksort uses ~ 2N ln N compares (and one-sixth that many exchanges) on the 
average to sort an array of length N with distinct keys.

* Proposition l. 
Quicksort uses ~ N^2/2 compares in the worst case, but random shuffling protects 
against this case.

*/

import lib.In;
import lib.StdOut;
import lib.StdRandom;

public class Quick {

    public static void sort(Comparable[] a) { 
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int low, int high){
        if ( high <= low) {
            return;
        }
        int j = partition(a, low, high);
        sort(a, low, j - 1);
        sort(a, j + 1, high);
    }

    private static int partition(Comparable[] a, int low, int high){
        int i = low, j = high + 1;
        Comparable pivot = a[low];
        while(true){
            while(less(a[++i], pivot)){
                if(i == high) {
                    break;
                }
            }
            while(less(pivot, a[--j])){
                if(j == low){
                    break;
                }
            }
            if(i >= j){
                break;
            } 
            exch(a, i, j);
        }
        exch(a, low, j);
        return j;
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

    // Test whether the array entries are in order.
    public static boolean isSorted(Comparable[] a) { 
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1]))
                return false;
        return true;
    }

    /*
     * 2.1.16 Certification. Write a check() method that calls sort() for a given
     * array.
     */

    public static boolean check() {

        // test integer
        Integer[] a = { 2, 4, 5, 0, 9, 1, 3, 8, 6, 7 };
        sort(a);
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }

        // test String
        String[] b = { "bed", "bug", "dad", "yes", "zoo", "now", "for", "tip", 
        "ilk", "dim", "tag", "jot", "sob", "nob", "sky" };
        sort(b);
        for (int i = 0; i < b.length - 1; i++) {
            if (less(b[i + 1], b[i])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) { 
        // Read strings from standard input, sort them, and print.
        // String[] a = In.readStrings();
        // sort(a);
        // assert isSorted(a);
        // show(a);

        //
        StdOut.println(check());
    }

}