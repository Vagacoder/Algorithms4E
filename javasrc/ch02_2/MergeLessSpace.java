package javasrc.ch02_2;

/*
* 2.2.12 Sublinear extra space. 
Develop a merge implementation that reduces the extra space requirement to max(M, N/M), 
based on the following idea: 

(i) Divide the array into N/M blocks of size M (for simplicity in this description, 
assume that N is a multiple of M). 

(ii) Then, considering the blocks as items with their first key as the sort key, 
sort them using selection sort; 
* ??? Sort Blocks or sort elements inside block ???

(iii) Run through the array merging the first block with the second, then the 
second block with the third, and so forth.

*/

import javasrc.ch02_1.InsertionRange;
import lib.StdOut;

public class MergeLessSpace {
    public static void sort(Comparable[] a) { 
        int M = 15;
        Comparable[] aux = new Comparable[M];
        int N = a.length;
        int numberOfBlock = N/M + 1;
        int lastPieceSize = N%M;

        for (int i = 0; i < (numberOfBlock - 1); i++){
            InsertionRange.sortNX(a, (i * M), (i * M + M));
        }
        InsertionRange.sortNX(a, (N - lastPieceSize), (N - 1) );

        

    }


    private static void merge(Comparable[] a, int low, int high){
        if(high <= low){
            return;
        }
        int mid = (low + high)/2;



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

    public static boolean check() {

        // test integer
        Integer[] a = { 28, 4, 51, 0, 9, 41, 36, 8, 76, 7, 12, 56, 2, 38, 45, 89,
             1, 17, 5, 90, 91, 3, 19, 11, 6 };
        sort(a);
        show(a);
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }

        // test String
        String[] b = { "bed", "bug", "dad", "yes", "zoo", "now", "for", "tip", 
        "ilk", "dim", "tag", "jot", "sob", "nob",
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
        // String[] a = In.readStrings();
        // sort(a);
        // assert isSorted(a);
        // show(a);

        StdOut.println(check());
    }
}
