package javasrc.ch02_3;

/*
* 2.3.11 Suppose that we scan over items with keys equal to the partitioning 
item’s key instead of stopping the scans when we encounter them. 

Show that the running time of this version of quicksort is quadratic for all 
arrays with just a constant number of distinct keys.

*/

import lib.*;

public class ex2_3_11 {
    // * 2.3.6 count for CN, compare numbers
    private static int count;

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

    private static int partition(Comparable[] a, int low, int high) {
        int i = low, j = high + 1;
        Comparable pivot = a[low];

        // * 2.3.11 reverse the less(a, b) to !less(b, a)
        while (true) {
            while (!less(pivot, a[++i])) {
                if (i == high) {
                    break;
                }
            }
            while (!less(a[--j], pivot)) {
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

    // * 2.3.6 return Cn value
    public static int getCN() {
        return count;
    }

    // * 2.3.6 count the Cn
    private static boolean less(Comparable v, Comparable w) {
        count++;
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

        StdOut.println("0. check() ... ");
        StdOut.println(check());

        StdOut.println("1. run 2 tests: 10 integers and 15 strings ... ");
        StdOut.println(check());
        StdOut.println();

        StdOut.println("2. Find Cn for random keys");
        StdOut.println("2.1 N = 100");
        int N = 100;
        Double[] c = new Double[N];
        int totalCN = 0;
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < N; i++) {
                c[i] = StdRandom.uniform();
            }
            sort(c);
            totalCN += getCN();
        }
        StdOut.println("Average Cn for N= 100: " + (totalCN * 1.0 / 10));
        StdOut.println("NlnN: " + (N * Math.log(N)));
        StdOut.println("2NlnN: " + (2 * N * Math.log(N)));
        StdOut.println("N^2: " + (N * N));
        StdOut.println("Average Cn / 2NlnN: " + (totalCN * 1.0 / 10) / (2 * N * Math.log(N)));
        StdOut.println("Average Cn / N^2: " + (totalCN * 1.0 / 10) / (N * N));
        StdOut.println();

        StdOut.println("2.2 N = 1000");
        N = 1000;
        c = new Double[N];
        totalCN = 0;
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < N; i++) {
                c[i] = StdRandom.uniform();
            }
            sort(c);
            totalCN += getCN();
        }
        StdOut.println("Average Cn for N= 1000: " + (totalCN * 1.0 / 10));
        StdOut.println("NlnN: " + (N * Math.log(N)));
        StdOut.println("2NlnN: " + (2 * N * Math.log(N)));
        StdOut.println("N^2: " + (N * N));
        StdOut.println("Average Cn / 2NlnN: " + (totalCN * 1.0 / 10) / (2 * N * Math.log(N)));
        StdOut.println("Average Cn / N^2: " + (totalCN * 1.0 / 10) / (N * N));
        StdOut.println();

        StdOut.println("2.3 N = 10000");
        N = 10000;
        c = new Double[N];
        totalCN = 0;
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < N; i++) {
                c[i] = StdRandom.uniform();
            }
            sort(c);
            totalCN += getCN();
        }
        StdOut.println("Average Cn for N= 10000: " + (totalCN * 1.0 / 10));
        StdOut.println("NlnN: " + (N * Math.log(N)));
        StdOut.println("2NlnN: " + (2 * N * Math.log(N)));
        StdOut.println("N^2: " + (N * N));
        StdOut.println("Average Cn / 2NlnN: " + (totalCN * 1.0 / 10) / (2 * N * Math.log(N)));
        StdOut.println("Average Cn / N^2: " + (totalCN * 1.0 / 10) / (N * N));
        StdOut.println();

        StdOut.println("3. Find Cn for constant number keys");
        StdOut.println("3.1 N = 100");
        N = 100;
        c = new Double[N];
        totalCN = 0;
        double[] keys= {0.0, 1.0, 2.0, 3.0, 4.0};
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < N; i++) {
                c[i] = keys[StdRandom.uniform(0, 5)];
            }
            sort(c);
            totalCN += getCN();
        }
        StdOut.println("Average Cn for N= 100: " + (totalCN * 1.0 / 10));
        StdOut.println("NlnN: " + (N * Math.log(N)));
        StdOut.println("2NlnN: " + (2 * N * Math.log(N)));
        StdOut.println("N^2: " + (N * N));
        StdOut.println("Average Cn / 2NlnN: " + (totalCN * 1.0 / 10) / (2 * N * Math.log(N)));
        StdOut.println("Average Cn / N^2: " + (totalCN * 1.0 / 10) / (N * N));
        StdOut.println();

        StdOut.println("3.2 N = 1000");
        N = 1000;
        c = new Double[N];
        totalCN = 0;
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < N; i++) {
                c[i] = keys[StdRandom.uniform(0, 5)];
            }
            sort(c);
            totalCN += getCN();
        }
        StdOut.println("Average Cn for N= 1000: " + (totalCN * 1.0 / 10));
        StdOut.println("NlnN: " + (N * Math.log(N)));
        StdOut.println("2NlnN: " + (2 * N * Math.log(N)));
        StdOut.println("N^2: " + (N * N));
        StdOut.println("Average Cn / 2NlnN: " + (totalCN * 1.0 / 10) / (2 * N * Math.log(N)));
        StdOut.println("Average Cn / N^2: " + (totalCN * 1.0 / 10) / (N * N));
        StdOut.println();

        StdOut.println("3.3 N = 10000");
        N = 10000;
        c = new Double[N];
        totalCN = 0;
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < N; i++) {
                c[i] = keys[StdRandom.uniform(0, 5)];
            }
            sort(c);
            totalCN += getCN();
        }
        StdOut.println("Average Cn for N= 10000: " + (totalCN * 1.0 / 10));
        StdOut.println("NlnN: " + (N * Math.log(N)));
        StdOut.println("2NlnN: " + (2 * N * Math.log(N)));
        StdOut.println("N^2: " + (N * N));
        StdOut.println("Average Cn / 2NlnN: " + (totalCN * 1.0 / 10) / (2 * N * Math.log(N)));
        StdOut.println("Average Cn / N^2: " + (totalCN * 1.0 / 10) / (N * N));
        StdOut.println();
    }

}