package javasrc.ch02_3;

/*
* 2.3.13 What is the recursive depth of quicksort, in the best, worst, and 
average cases?

This is the size of the stack that the system needs to keep track of the recursive 
calls. 

See Exercise 2.3.20 for a way to guarantee that the recursive depth is logarithmic 
in the worst case.


*/

import lib.*;

public class ex2_3_13 {

    // * 2.3.13 count for stack deep level
    private static int count;

    public static void sort(Comparable[] a) {
        count = 0;
        sort(a, 0, a.length - 1, 0);
    }

    private static void sort(Comparable[] a, int low, int high, int level) {
        level++;
        if (level > count) {
            count = level;
        }
        if (high <= low) {
            return;
        }
        int j = partition(a, low, high);
        sort(a, low, j - 1, level);
        sort(a, j + 1, high, level);
        return;
    }

    private static int partition(Comparable[] a, int low, int high) {
        int i = low, j = high + 1;
        Comparable pivot = a[low];
        while (true) {
            while (less(a[++i], pivot)) {
                if (i == high) {
                    break;
                }
            }
            while (less(pivot, a[--j])) {
                if (j == low) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            exch(a, i, j);
        }
        exch(a, low, j);
        return j;
    }

    // * 2.3.13 return Cn value
    public static int getCN() {
        return count;
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
        Integer[] a = { 5, 0, 1, 3, 4, 2, 8, 6, 7, 9, 10 };
        sort(a);
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }
        StdOut.println(getCN());

        // test String
        String[] b = { "bed", "bug", "dad", "yes", "zoo", "now", "for", "tip", "ilk", "dim", "tag", "jot", "sob", "nob",
                "sky" };
        sort(b);
        for (int i = 0; i < b.length - 1; i++) {
            if (less(b[i + 1], b[i])) {
                return false;
            }
        }
        StdOut.println(getCN());
        return true;
    }

    public static void main(String[] args) {

        StdOut.println("0. check() ... ");
        StdOut.println(check());

        // ? test short array,
        // ? log2 10 = 3.32, log2 20 = 4.32, log2 100 = 6.64
        StdOut.println("1. Find Cn in short array");
        int L = 20;
        Integer[] a = new Integer[L];
        for (int i = 0; i < L; i++) {
            a[i] = i + 1;
        }
        StdRandom.shuffle(a);
        sort(a);
        StdOut.println("Is sorting successful: " + isSorted(a));
        StdOut.println("For N= " + L + ", Count is: " + getCN());
        StdOut.println();

        // ? test Average Case, Worst Case, Best Case
        StdOut.println("2. Find Cn in different cases");
        int M = 20;
        int repeat = 10;
        StdOut.println("Repeat " + repeat + " times");
        // ? Average Case (random array)
        StdOut.println("2.1 Average case, array size: " + M);
        Double[] c = new Double[M];
        int totalCN = 0;
        for (int j = 0; j < repeat; j++) {
            for (int i = 0; i < M; i++) {
                c[i] = StdRandom.uniform();
            }
            sort(c);
            totalCN += getCN();
        }
        StdOut.println("Average Cn for " + M + " elements: " + (totalCN * 1.0 / repeat));
        StdOut.println("lnN: " + (Math.log(M)));
        StdOut.println();

        // ? Worst Case
        StdOut.println("2.2 Worst case, array size: " + M);
        c = new Double[M];
        totalCN = 0;
        for (int j = 0; j < repeat; j++) {
            double startValue = 0.001;
            for (int i = 0; i < M; i++) {
                c[i] = (startValue += 0.001);
            }
            sort(c);
            totalCN += getCN();
        }
        StdOut.println("Average Cn for " + M + " elements: " + (totalCN * 1.0 / repeat));
        StdOut.println("lnN: " + (Math.log(M)));
        StdOut.println();

        // ? Best Case
        int O = 20;
        StdOut.println("2.3 Best case, array size: " + O);
        Integer[] d = new Integer[O];
        int count = 0;
        int initValue = 0;
        for (int i = 0; i < O; i++) {
            d[i] = (initValue += 1);
        }
        BestCase.getBestCase(d);
        show(d);
        sort(d);
        count = getCN();

        StdOut.println("Cn for for " + O + " elements: " + count);
        StdOut.println("lnN: " + (Math.log(O)));
        StdOut.println();
    }

}