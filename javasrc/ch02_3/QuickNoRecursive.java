package javasrc.ch02_3;

/*
* 2.3.20 Nonrecursive quicksort. 
Implement a nonrecursive version of quicksort based on a main loop where a subarray 
is popped from a stack to be partitioned, and the resulting subarrays are pushed 
onto the stack. 

Note : Push the larger of the subarrays onto the stack first, which guarantees 
that the stack will have at most lg N entries.

*/

import javasrc.ch01_3.LinkedListStack;
import lib.*;

public class QuickNoRecursive{


    public static void sort(Comparable[] a) { 
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    // * 2.3.20
    private static void sort(Comparable[] a, int low, int high){

        LinkedListStack stack = new LinkedListStack<>();

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