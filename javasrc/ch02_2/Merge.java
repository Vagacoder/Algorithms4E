package javasrc.ch02_2;

/*
* Algorithm 2.4 Top-down mergesort P. 273
* proposition F: 
Top-down mergesort uses between 1⁄2 N lg N and N lg N compares to sort any array of length N.

* proposition G: 
Top-down mergesort uses at most 6N lg N array accesses to sort an array of length N.

* 1. basic main functions are: 2x sort(); 1x merge()
* 2. basic helper functions are: less(), exch(), show(), isSorted()
*/

/*
* Improvement #1 : skip merge() when array is already sorted();
* 2.2.8 Suppose that Algorithm 2.4 is modified to skip the call on merge() whenever
a[mid] <= a[mid+1]. Prove that the number of compares used to mergesort a sorted
array is linear.

*/

/*
* 2.2.10 Faster merge. 
Implement a version of merge() that copies the second half of a[] to aux[] in 
decreasing order and then does the merge back to a[]. This change allows you to 
remove the code to test that each of the halves has been exhausted from the
inner loop. 

* Note: The resulting sort is not stable (see page 341).
*/

/*
* Improvement #2: Add a cutoff for small subarrays (switch to Insertion sort)
* 2.2.11  Implement the three improvements to mergesort that are de-
scribed in the text on page 275: Add a cutoff for small subarrays, test whether the array is
already in order, and avoid the copy by switching arguments in the recursive code.
*/

/*
* Imporvement #3: avoid the copy by switching arguments in the recursive code
*/

import lib.StdOut;

import java.util.Arrays;

import javasrc.ch02_1.InsertionRange;

public class Merge {

    private static Comparable[] aux;

    public static void sort(Comparable[] a) {
        aux = new Comparable[a.length];
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int low, int high) {
        if (high <= low) {
            return;
        }

        int mid = (low + high) / 2;
        sort(a, low, mid);
        sort(a, mid + 1, high);
        fasterMerge(a, low, mid, high);
    }

    // * Improvement #2: 
    private static void sortCutOff(Comparable[] a, int low, int high) {
        if (high <= low) {
            return;
        }

        // * Improvement #2: Add a cutoff for small subarrays (switch to Insertion sort)
        if (a.length < 16) {
            InsertionRange.sortNX(a, low, high);
        }

        int mid = (low + high) / 2;
        sort(a, low, mid);
        sort(a, mid + 1, high);
        fasterMerge(a, low, mid, high);
    }

    // * Improvement #3:avoid the copy by switching arguments in the recursive code
    public static void sortNoCopy(Comparable[] a) {
        aux = Arrays.copyOf(a, a.length);
        sortNoCopy(a, aux, 0, a.length - 1);
    }

    // * Improvement #3:avoid the copy by switching arguments in the recursive code
    private static void sortNoCopy(Comparable[] src, Comparable[] aux, int low, int high) {
        if (high <= low) {
            return;
        }

        // if (src.length < 16) {
        //     InsertionRange.sortNX(src, low, high);
        // }

        int mid = (low + high) / 2;
        sortNoCopy(aux, src, low, mid);
        sortNoCopy(aux, src, mid + 1, high);
        mergeNoCopy(src, aux, low, mid, high);
    }
        
    // * Improvement #3:avoid the copy by switching arguments in the recursive code
    private static void mergeNoCopy(Comparable[] src, Comparable[] aux, int low, int mid, int high){
        int i = low, j = mid + 1;

        for (int k = low; k <= high; k++) {
            if (i > mid) {
                src[k] = aux[j++];
            } else if (j > high) {
                src[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                src[k] = aux[j++];
            } else {
                src[k] = aux[i++];
            }
        }
    }

    private static void merge(Comparable[] a, int low, int mid, int high) {
        int i = low, j = mid + 1;

        // * Improvement #1 : skip merge() when array is already sorted();
        if (!less(a[j], a[mid])) {
            return;
        }

        for (int k = low; k <= high; k++) {
            aux[k] = a[k];
        }

        for (int k = low; k <= high; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > high) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    // * 2.2.10 Faster merge
    private static void fasterMerge(Comparable[] a, int low, int mid, int high) {
        for (int i = low; i <= mid; i++) {
            aux[i] = a[i];
        }        // if (src.length < 16) {
            //     InsertionRange.sortNX(src, low, high);
            // }

        for (int i = mid + 1; i <= high; i++) {
            aux[i] = a[high - (i - (mid + 1))];
        }

        int i = low, j = high;
        for (int k = low; k <= high; k++) {
            if (less(aux[j], aux[i])) {
                a[k] = aux[j--];
            } else {
                a[k] = aux[i++];
            }
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

    /*
     * 2.1.16 Certification. Write a check() method that calls sort() for a given
     * array.
     */

    public static boolean check() {

        // test integer
        Integer[] a = { 28, 4, 51, 0, 9, 41, 36, 8, 76, 7, 12, 56, 2, 38, 45, 89, 1, 17, 5, 90, 91, 3,  19, 11, 6};
        sort(a);
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }
        show(a);

        // test String
        String[] b = { "bed", "bug", "dad", "yes", "zoo", "now", "for", "tip", "ilk", "dim", "tag", "jot", "sob", "nob",
                "sky" };
        sort(b);
        for (int i = 0; i < b.length - 1; i++) {
            if (less(b[i + 1], b[i])) {
                return false;
            }
        }
        show(b);
        return true;
    }

    public static void main(String[] args) { 
        // String[] a = In.readStrings();
        // sort(a);
        // assert isSorted(a);
        // show(a);

        StdOut.println(check());
    }
}