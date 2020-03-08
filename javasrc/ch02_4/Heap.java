package javasrc.ch02_4;

/* 
* Algorithm 2.7 Heap sort, P. 324

* Proposition p. 
The height of a complete binary tree of size N is ⎣ lg N ⎦ .

* Proposition Q. 
In an N-key priority queue, the heap algorithms require no more than 1 + lg N compares 
for insert and no more than 2 lg N compares for remove the maximum.

* Proposition Q (continued). 
In an index priority queue of size N, the number of compares required is proportional 
to at most log N for insert, change priority, delete, and remove the minimum.

* Proposition r. 
Sink-based heap construction uses fewer than 2N compares and fewer than N exchanges 
to construct a heap from N items.

* Proposition s. 
Heapsort uses fewer than 2N lg N + 2N compares (and half that many exchanges) to 
sort N items.

*/

import lib.*;

public class Heap {

    public static void sort(Comparable[] a) {
        int N = a.length - 1;

        // * Build up heap
        // ! For 0-based array, parent index is (i - 1)/2
        for (int k = (N - 1) / 2; k >= 0; k--) {
            sink(a, k, N);
        }

        // * Sorting
        int cur = N;
        while (cur > 0) {
            exch(a, 0, cur--);
            sink(a, 0, cur);
        }
    }

    // ! For 0-based array, children indecs are 2*i + 1 and 2*i + 2;
    private static void sink(Comparable[] a, int i, int N) {
        int k = 2 * i + 1;
        while (k <= N) {
            if (k < N && less(a[k], a[k + 1])) {
                k++;
            }
            if (!less(a[i], a[k])) {
                break;
            }
            exch(a, i, k);
            i = k;
            k = 2 * i + 1;
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
        show(a);
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }

        // test String
        String[] b = { "bed", "bug", "dad", "yes", "zoo", "now", "for", "tip", "ilk", "dim", "tag", "jot", "sob", "nob",
                "sky" };
        sort(b);
        show(b);
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

        StdOut.println("1. Confirm sort() works correctly ... ");
        StdOut.println("2 default arrays ... ");
        StdOut.println(check());
        StdOut.println("multiple arrays ... ");
        int length = 10000;
        int repeat = 20;
        boolean good = true;
        for (int h = 10; h < length; h*=10) {
            for (int i = 0; i < repeat; i++) {
                Double[] a = new Double[h];
                for (int j = 0; j < h; j++) {
                    a[j] = StdRandom.uniform();
                }
                // show(a);
                sort(a);
                if (!isSorted(a)) {
                    good = false;
                    break;
                }
            }
        }
        StdOut.println(good ? "Successful!" : "Failed!");
        StdOut.println();
    }
}