package javasrc.ch02_1;

/*
* Algorithm 2.1 Selection sort P.249
this one is from textbook

* Proposition A. Selection sort uses ~ (N^2 /2) compares and N exchanges to sort an
array of length N.

* Property D. The running times of insertion sort and selection sort are quadratic
and within a small constant factor of one another for randomly ordered arrays of
distinct values.

* Property 1: NOT stable
* Property 2: In place
* Property 3: extra space is 1
* Property 4: running time N^2
* Property 5: Running time is insensitive to input;
* Property 6: Data movement is minimal;
* 
*/
/******************************************************************************
 *  Compilation:  javac Selection.java
 *  Execution:    java  Selection < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   https://algs4.cs.princeton.edu/21elementary/tiny.txt
 *                https://algs4.cs.princeton.edu/21elementary/words3.txt
 *   
 *  Sorts a sequence of strings from standard input using selection sort.
 *   
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java Selection < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *    
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *  
 *  % java Selection < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
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

import lib.In;
import lib.StdOut;
import lib.StdDraw;
import lib.StdRandom;

public class Selection {
    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++)
                if (less(a[j], a[min]))
                    min = j;
            exch(a, i, min);
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

        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++) {
                if (less(a[j], a[min]))
                    min = j;
            }
            // draw before exch, label current as blue, min as red
            StdDraw.clear();
            for (int k = 0; k < N; k++) {

                if (k < i) {
                    StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
                } else if (k == i) {
                    StdDraw.setPenColor(StdDraw.BLUE);
                } else if (k == min) {
                    StdDraw.setPenColor(StdDraw.RED);
                }
                StdDraw.rectangle((k + 0.25), a[k] / 2.0, 0.25, a[k] / 2.0);
                StdDraw.setPenColor(StdDraw.DARK_GRAY);
            }
            pause(2000000000L);
            exch(a, i, min);

            // draw every exch
            StdDraw.clear();
            for (int k = 0; k < N; k++) {
                if (k < i) {
                    StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
                } else if (k == i) {
                    StdDraw.setPenColor(StdDraw.RED);
                } else if (k == min) {
                    StdDraw.setPenColor(StdDraw.BLUE);
                }
                StdDraw.rectangle((k + 0.25), a[k] / 2.0, 0.25, a[k] / 2.0);
                StdDraw.setPenColor(StdDraw.DARK_GRAY);
            }
            pause(2000000000L);
        }
        for (int k = 0; k < N; k++) {
            StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            StdDraw.rectangle((k + 0.25), a[k] / 2.0, 0.25, a[k] / 2.0);
        }
    }

    // helper to delay between two drawing
    private static void pause(long n) {
        for (long i = 0; i < n; i++) {
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
        // sort(a);
        // assert isSorted(a);
        // show(a);

        StdOut.println("\n2. Test drawing array content ...");
        int N = 20;
        Double[] b = new Double[N];
        for (int i = 0; i < N; i++) {
            b[i] = StdRandom.uniform();
        }
        show(b);
        sortAndDraw(b);
        show(b);
    }
}
