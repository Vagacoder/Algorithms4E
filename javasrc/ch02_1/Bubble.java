package javasrc.ch02_1;

/*
* Bubble Sort
One of most easiest sorting algorithm to implement.

* Property 1: Stable
* Property 2: In place
* Property 3: extra space is 1
* Property 4: running time N^2
* Property 5: Running time is insensitive to input;

*/

import lib.In;
import lib.StdOut;

public class Bubble{

    public static void sort(Comparable[] a) { 
        int N = a.length;
        boolean done = false;

        while(!done){
            done = true;
            for (int i = 0; i < N - 1; i++){
                if(less(a[i + 1], a[i])){
                    exch(a, i, i+1);
                    done = false;
                }
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
        String[] b = { "bed", "bug", "dad", "yes", "zoo", "now", "for", "tip", 
        "ilk", "dim", "tag", "jot", "sob", "nob", "sky" };
        sort(b);
        for (int i = 0; i < b.length - 1; i++) {
            if (less(b[i + 1], b[i])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) { // Read strings from standard input, sort them, and print.
        StdOut.println(check());
        String[] a = In.readStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }
}