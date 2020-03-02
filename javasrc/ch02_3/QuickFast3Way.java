package javasrc.ch02_3;

/*
* 2.3.22 Fast three-way partitioning. (J. Bentley and D. McIlroy). 

Implement an entropy-optimal sort based on keeping equal keys at both the left 
and right ends of the subarray. 

Maintain indices p and q such that a[lo..p-1] that a[q+1..hi] are all equal to 
a[lo], an index i such that a[p..i-1] are all less than a[lo] and an index j such 
that a[j+1..q] are all greater than a[lo]. Add to the inner partitioning loop code 
to swap a[i] with a[p] (and increment p) if it is equal to v and to swap a[j] with 
a[q] (and decrement q) if it is equal to v before the usual comparisons of a[i] 
and a[j] with v. 

After the partitioning loop has terminated, add code to swap the items with equal 
keys into position.

Note : This code complements the code given in the text, in the sense that it does 
extra swaps for keys equal to the partitioning item’s key, while the code in the 
text does extra swaps for keys that are not equal to the partitioning item’s key.

*/

import lib.*;

public class QuickFast3Way {

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int low, int high) {
        if (high <= low) {
            return;
        }
        int j = partition(a, low, high);
        sort(a, low, j - 1);
        sort(a, j + 1, high);
    }

    // * ex 2.3.22
    private static int partition(Comparable[] a, int low, int high) {
        int i = low + 1;
        int j = high;
        int p = low + 1;
        int q = high;

        Comparable pivot = a[low];
        while (true) {
            while (!less(pivot, a[i])) {
                if (pivot.compareTo(a[i]) == 0) {
                    exch(a, p, i);
                    p++;
                }
                i++;
                if (i >= high) {
                    break;
                }
            }
            while (!less(a[j], pivot)) {
                if (pivot.compareTo(a[j]) == 0) {
                    exch(a, q, j);
                    q--;
                }
                j--;
                if (j == low) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            exch(a, i, j);
        }
        int pivotIndex = j;
        while (j >= p) {
            exch(a, j--, low++);
        }
        while (i <= q) {
            exch(a, i++, high--);
        }
        return pivotIndex;
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
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
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
        String[] b = { "bed", "bug", "dad", "yes", "zoo", "now", "for", "tip", "ilk", "dim", "tag", "jot", "sob", "nob",
                "sky" };
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

        StdOut.println("1. Confirm sort() works correctly ... ");
        StdOut.println("2 default arrays ... ");
        StdOut.println(check());
        StdOut.println("multiple arrays ... ");
        int length = 10000;
        int repeat = 20;
        boolean good = true;
        for (int i = 0; i < repeat; i++) {
            Double[] a = new Double[length];
            for (int j = 0; j < length; j++) {
                a[j] = StdRandom.uniform();
            }
            sort(a);
            if (!isSorted(a)) {
                good = false;
                break;
            }
        }
        StdOut.println(good ? "Successful!" : "Failed!");
        StdOut.println();
    }

}