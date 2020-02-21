package javasrc.ch02_3;

/*
* 2.3.5 Give a code fragment that sorts an array that is known to consist of items having
just two distinct keys.

*/

import lib.StdOut;

public class Sort2KeyArray{

    public static void sort(Comparable[] a, Comparable small, Comparable large){
        int i = 0, j = a.length;
        while(true){
            while(a[++i].equals(small)){
                if(i == a.length){
                    break;
                }
            }
            while(a[--j].equals(large)){
                if(j == 0){
                    break;
                }
            }
            if(i>=j){
                break;
            }
            exch(a, i, j);
        }
        exch(a, 0, j);
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
        Integer[] a = { 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1 };
        sort(a, 0, 1);
        show(a);
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }

        // test String
        String[] b = { "a", "a", "b", "a", "b", "a", "a", "a", 
        "a", "a", "a", "b", "a", "a", "a" };
        sort(b, "a", "b");
        show(b);
        for (int i = 0; i < b.length - 1; i++) {
            if (less(b[i + 1], b[i])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) { 
        StdOut.println(check());
    }
}