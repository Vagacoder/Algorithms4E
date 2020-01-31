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
import lib.StdDraw;
import lib.StdOut;
import lib.StdRandom;

public class Shell {

    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) {
            // h = 1, 4, 13, 40, 121, 364, 1093, ...
            h = 3 * h + 1;
        }

        // * 2.1.12 print the number of compares divided by the array size for each
        // increment.

        while (h >= 1) {
            int compareNumber = 0;
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                    compareNumber++;
                }
            }
            // StdOut.println("For increment: " + h + " compare # / array size = " + (compareNumber / N));
            h = h / 3;
        }
    }

    // * 2.1.17 Animation.
    public static void sortAndDraw(Double[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) {
            // h = 1, 4, 13, 40, 121, 364, 1093, ...
            h = 3 * h + 1;
        }

        // initialize canvas
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(-0.1, 1.1);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.DARK_GRAY);

        // first drawing of table
        for (int i = 0; i < N; i++) {
            StdDraw.rectangle((i + 0.25), a[i] / 2.0, 0.25, a[i] / 2.0);
        }

        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {

                    // draw before exch, label current as blue, min as red
                    StdDraw.clear();
                    for (int k = 0; k < N; k++) {
                        if (k > i) {
                            StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
                        } else if (k == j) {
                            StdDraw.setPenColor(StdDraw.BLUE);
                        }
                        StdDraw.rectangle((k + 0.25), a[k] / 2.0, 0.25, a[k] / 2.0);
                        StdDraw.setPenColor(StdDraw.DARK_GRAY);
                    }
                    pause(2000000000L);
                    exch(a, j, j - h);

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
            h = h / 3;
        }
    }

    // * 2.1.11 Implement a version of shellsort that keeps the increment sequence
    // in an array, rather than computing it.

    // * 2.1.29 Shellsort increments.
    // set increment sequence to 1, 5, 19, 41, 109, 209, 505, 929, 2161, 3905, 8929,
    // 16001, 36289, 64769, 146305, 260609

    // private static int[] incrementSeq = { 1, 4, 13, 40, 121, 364, 1093, 3280 };
    private static int[] incrementSeq = { 1, 5, 19, 41, 109, 209, 505, 929, 2161, 3905, 8929,
        16001, 36289, 64769, 146305, 260609 };

    public static void sort1(Comparable[] a) {
        int N = a.length;
        int index = 0;
        while (incrementSeq[index] <= N) {
            index++;
            if (index >= incrementSeq.length) {
                break;
            }
        }
        int h = incrementSeq[--index];
        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
            h = h / 3;
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