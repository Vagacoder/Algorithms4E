package javasrc.ch02_1;

/*
Algorithm 2.2 Insertion sort P. 251
this is from textbook
*/
/******************************************************************************
 *  Compilation:  javac Insertion.java
 *  Execution:    java Insertion < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   https://algs4.cs.princeton.edu/21elementary/tiny.txt
 *                https://algs4.cs.princeton.edu/21elementary/words3.txt
 *  
 *  Sorts a sequence of strings from standard input using insertion sort.
 *
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java Insertion < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *
 *  % java Insertion < words3.txt
 *  all bad bed bug dad ... yes yet zoo   [ one string per line ]
 *
 ******************************************************************************/

/*
* 2.1.17 Animation. 
Add code to Insertion, Selection and Shell to make them draw the array contents 
as vertical bars like the visual traces in this section, redrawing the bars after 
each pass, to produce an animated effect, ending in a “sorted” picture where the 
bars appear in order of their height. 

* Hint : Use a client like the one in the text that generates random Double values, 
insert calls to show() as appropriate in the sort code, and implement a show() 
method that clears the canvas and draws the bars.

*/

/*
* 2.1.24 Insertion sort with sentinel. 
Develop an implementation of insertion sort that eliminates the j>0 test in the 
inner loop by first putting the smallest item into position. 

Use SortCompare to evaluate the effectiveness of doing so. 

Note : It is often possible to avoid an index-out-of-bounds test in this way—the 
element that enables the test to be eliminated is known as a sentinel.

*/

/*
* 2.1.25 Insertion sort without exchanges. 
Develop an implementation of insertion sort that moves larger elements to the 
right one position with one array access per entry, rather than using exch() . 

Use SortCompare to evaluate the effectiveness of doing so.

*/

import lib.In;
import lib.StdDraw;
import lib.StdOut;
import lib.StdRandom;

public class Insertion {
    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--)
                exch(a, j, j - 1);
        }
    }

    // * 2.1.17 Animation.
    public static void sortAndDraw(Double[] a) {
        int N = a.length;

        // initialize canvas
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(-0.1, 1.1);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.DARK_GRAY);

        // first drawing of table
        for (int i = 0; i < N; i++) {
            StdDraw.rectangle((i + 0.25), a[i] / 2.0, 0.25, a[i] / 2.0);
        }

        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {

                // draw before exch, label current as blue, min as red
                StdDraw.clear();
                for (int k = 0; k < N; k++) {
                    if (k > i) {
                        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
                    } else if (k == j - 1 && less(a[j], a[j - 1])) {
                        StdDraw.setPenColor(StdDraw.RED);
                    }else if (k == j) {
                        StdDraw.setPenColor(StdDraw.BLUE);
                    }
                    StdDraw.rectangle((k + 0.25), a[k] / 2.0, 0.25, a[k] / 2.0);
                    StdDraw.setPenColor(StdDraw.DARK_GRAY);
                }
                pause(2000000000L);
                exch(a, j, j - 1);

                // draw after exch
                StdDraw.clear();
                for (int k = 0; k < N; k++) {
                    if (k > i) {
                        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
                    } else if (k == j) {
                        StdDraw.setPenColor(StdDraw.RED);
                    } else if (k == j - 1) {
                        StdDraw.setPenColor(StdDraw.BLUE);
                    }
                    StdDraw.rectangle((k + 0.25), a[k] / 2.0, 0.25, a[k] / 2.0);
                    StdDraw.setPenColor(StdDraw.DARK_GRAY);
                }
                pause(2000000000L);
            }
        }
    }

    // helper to delay between two drawing
    private static void pause(long n) {
        for (long i = 0; i < n; i++) {
        }
    }

    // * 2.1.24 Insertion sort with sentinel. 
    public static void sortS(Comparable[] a) {
        int N = a.length;

        int indexOfMin = findIndexOfMin(a);
        exch(a, 0, indexOfMin);

        for (int i = 1; i < N; i++) {
            for (int j = i; less(a[j], a[j - 1]); j--)
                exch(a, j, j - 1);
        }
    }

    // helper
    private static int findIndexOfMin(Comparable[] a){
        int N = a.length;
        if( N == 0){return -1;}
        int indexOfMin = 0;
        for (int i = 0; i<N; i++){
            if (less(a[i], a[indexOfMin])){
                indexOfMin = i;
            }
        }
        return indexOfMin;
    }


    // * 2.1.25 Insertion sort without exchanges.
    public static void sortNX(Comparable[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            // for (int j = i; j > 0 && less(a[j], a[j - 1]); j--)
            //     exch(a, j, j - 1);
            Comparable temp = a[i];
            int j = i;
            while (j> 0 && less(temp, a[j-1])){
                a[j] = a[j-1];
                j--;
            }
            a[j] = temp;
        }
    }


    // * 2.1.26
    public static void sortInt(int[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--)
                exch(a, j, j - 1);
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

    // helper for 2.1.26
    private static void exch(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a) { // Print the array, on a single line.
        for (int i = 0; i < a.length; i++)
            StdOut.print(a[i] + "\n");
        StdOut.println();
    }

    public static boolean isSorted(Comparable[] a) { // Test whether the array entries are in order.
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1]))
                return false;
        return true;
    }

    public static void main(String[] args) { // Read strings from standard input, sort them, and print.
        // StdOut.println("1. Test manual input ...");
        // String[] a = In.readStrings();
        // sortS(a);
        // assert isSorted(a);
        // show(a);

        StdOut.println("\n2. Test drawing array content ...");
        int N = 20;
        Double[] b = new Double[N];
        for (int i = 0; i < N; i++) {
            b[i] = StdRandom.uniform();
        }
        show(b);
        sortS(b);
        show(b);
    }
}
