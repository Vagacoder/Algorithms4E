package javasrc.ch02_1;

/*
* Based on Algorithm 2.2, sort only designated part of array
For improvment of Merge Sort. see Ex 2.2.11 P. 285
*/

import lib.StdOut;
import lib.StdRandom;

public class InsertionRange {
    public static void sort(Comparable[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            for (int j = i; j > low && less(a[j], a[j - 1]); j--)
                exch(a, j, j - 1);
        }
    }

    // * 2.1.25 Insertion sort without exchanges.
    public static void sortNX(Comparable[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            // for (int j = i; j > 0 && less(a[j], a[j - 1]); j--)
            //     exch(a, j, j - 1);
            Comparable temp = a[i];
            int j = i;
            while (j> low && less(temp, a[j-1])){
                a[j] = a[j-1];
                j--;
            }
            a[j] = temp;
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

    private static void show(Comparable[] a) { 
        for (int i = 0; i < a.length; i++)
            StdOut.print(a[i] + "\n");
        StdOut.println();
    }

    public static boolean isSorted(Comparable[] a) { 
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1]))
                return false;
        return true;
    }

    public static void main(String[] args) { 

        StdOut.println("\n2. Test random input ...");
        int N = 20;
        Double[] b = new Double[N];
        for (int i = 0; i < N; i++) {
            b[i] = StdRandom.uniform();
        }
        show(b);
        sortNX(b, 10, 15);
        show(b);
    }
}
