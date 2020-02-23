package javasrc.ch02_3;

/*
* 2.3.7 Find the expected number of subarrays of size 0, 1, and 2 when quicksort 
is used to sort an array of N items with distinct keys. If you are mathematically 
inclined, do the math; if not, run some experiments to develop hypotheses.

C(0) = C(1) = 2 * C(2)

*/

import lib.*;

public class ex2_3_7{

    // * 2.3.7 
    private static int countFor0;
    private static int countFor1;
    private static int countFor2;

    public static void sort(Comparable[] a) {
        countFor0 = 0;
        countFor1 = 0;
        countFor2 = 0;
        // StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int low, int high) {
        
        // * 2.3.7
        if (high < low){
            countFor0 ++;
        } else if(high == low ){
            countFor1++;
        } else if ( high == low + 1){
            countFor2++;
        }

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

    // * 2.3.7 return Cn value
    public static int getCn0() {
        return countFor0;
    }

    public static int getCn1() {
        return countFor1;
    }

    public static int getCn2() {
        return countFor2;
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

        StdOut.println("1. run 2 tests: 10 integers and 15 strings ... ");
        StdOut.println(check());
        StdOut.println();

        StdOut.println("2. Find Cn");
        StdOut.println("2.1 N = 100");
        int N = 100;
        Double[] c = new Double[N];
        int totalCN0 = 0;
        int totalCN1 = 0;
        int totalCN2 = 0;
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < N; i++) {
                c[i] = StdRandom.uniform();
            }
            sort(c);
            totalCN0 += getCn0();
            totalCN1 += getCn1();
            totalCN2 += getCn2();
        }
        StdOut.println("Average Cn of size 0 for N= 100: "+ (totalCN0 * 1.0 / 10 )); 
        StdOut.println("Average Cn of size 1 for N= 100: "+ (totalCN1 * 1.0 / 10 )); 
        StdOut.println("Average Cn of size 2 for N= 100: "+ (totalCN2 * 1.0 / 10 )); 
        StdOut.println();
     
        StdOut.println("2.2 N = 1000");
        N = 1000;
        c = new Double[N];
        totalCN0 = 0;
        totalCN1 = 0;
        totalCN2 = 0;
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < N; i++) {
                c[i] = StdRandom.uniform();
            }
            sort(c);
            totalCN0 += getCn0();
            totalCN1 += getCn1();
            totalCN2 += getCn2();
        }
        StdOut.println("Average Cn of size 0 for N= 1000: "+ (totalCN0 * 1.0 / 10 )); 
        StdOut.println("Average Cn of size 1 for N= 1000: "+ (totalCN1 * 1.0 / 10 )); 
        StdOut.println("Average Cn of size 2 for N= 1000: "+ (totalCN2 * 1.0 / 10 )); 
        StdOut.println();

        StdOut.println("2.3 N = 10000");
        N = 10000;
        c = new Double[N];
        totalCN0 = 0;
        totalCN1 = 0;
        totalCN2 = 0;
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < N; i++) {
                c[i] = StdRandom.uniform();
            }
            sort(c);
            totalCN0 += getCn0();
            totalCN1 += getCn1();
            totalCN2 += getCn2();
        }
        StdOut.println("Average Cn of size 0 for N= 10000: "+ (totalCN0 * 1.0 / 10 )); 
        StdOut.println("Average Cn of size 1 for N= 10000: "+ (totalCN1 * 1.0 / 10 )); 
        StdOut.println("Average Cn of size 2 for N= 10000: "+ (totalCN2 * 1.0 / 10 )); 
        StdOut.println();
    }

}