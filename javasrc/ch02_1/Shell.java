package javasrc.ch02_1;

/*
Algorithm 2.3 Shell Sort. P. 259
Improved Insertion Sort.
*/

/*
2.1.11 Implement a version of shellsort that keeps the increment sequence in an array,
rather than computing it.

2.1.12 Instrument shellsort to print the number of compares divided by the array size
for each increment. Write a test client that tests the hypothesis that this number is a
small constant, by sorting arrays of random Double values, using array sizes that are
increasing powers of 10, starting at 100.

*/

import lib.StdOut;
import lib.In;

public class Shell {

    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) {
            // h = 1, 4, 13, 40, 121, 364, 1093, ...
            h = 3 * h + 1;
        }

        // 2.1.12 print the number of compares divided by the array size for each increment.
        
        while (h >=1){
            int compareNumber = 0;
            for(int i = h; i < N; i++){
                for(int j = i; j >= h && less(a[j], a[j-h]); j-=h){
                    exch(a, j, j-h);
                    compareNumber++;
                }
            }
            StdOut.println("For increment: "+ h +" compare # / array size = " + (compareNumber/N));
            h = h/3;
        }
    }

    // 2.1.11 Implement a version of shellsort that keeps the increment sequence 
    // in an array, rather than computing it.

    private static int[] incrementSeq = {1, 4, 13, 40, 121, 364, 1093, 3280};
    

    public static void sort1(Comparable[] a){
        int N = a.length;
        int index = 0;
        while (incrementSeq[index] <= N ){
            index++;
            if (index >= incrementSeq.length){
                break;
            }
        }
        int h = incrementSeq[--index];
        while (h >=1){
            for(int i = h; i < N; i++){
                for(int j = i; j >= h && less(a[j], a[j-h]); j-=h){
                    exch(a, j, j-h);
                }
            }
            h = h/3;
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a) { // Print the array, on a single line.
        for (int i = 0; i < a.length; i++)
            StdOut.print(a[i] + " ");
        StdOut.println();
    }

    public static boolean isSorted(Comparable[] a) { // Test whether the array entries are in order.
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1]))
                return false;
        return true;
    }

    public static void main(String[] args) { // Read strings from standard input, sort them, and print.
        String[] a = In.readStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }
}