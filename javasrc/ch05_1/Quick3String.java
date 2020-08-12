package javasrc.ch05_1;

/*
 * Algorithm 5.3 Three-way string quicksort. P.720
 * 
 * Improvements:
 * 1. treat small subarray using insertion sort.
 * 2. handle specialized alphabets, add an Alphabet argument to each of methods
 * 3. randomization, shuffle array like quicksort.
 * 
 * Worst case: array is sorted or nearly sorted.
 * 
 * Proposition E. To sort an array of N random strings, 3-way string quicksort 
 * uses ~ 2N ln N character compares, on the average.
 * 

  
 ? Sample file:
 ? data/shells.txt (P. 713)
 ? data/plates.txt
 ? data/words3.txt 
 
 */

import lib.*;

public class Quick3String {

    public static void sort(String[] a){
        sort(a, 0, a.length - 1, 0);
    }

    private static void sort(String[] a, int low, int high, int d){
        if(high <= low){
            return;
        }

        int lt = low;
        int gt = high;
        int v = charAt(a[low], d);
        int i = low + 1;

        while(i <= gt){
            int t = charAt(a[i], d);
            if(t < v){
                exch(a, lt++, i++);
            }else if( t > v){
                exch(a, i, gt--);
            }else{
                i++;
            }
        }

        // ! here, a[low ... lt -1] < v = a[lt ... gt] < a[gt+1 ... hihg]
        // * sort these 3 parts
        sort(a, low, lt-1, d);
        if(v >= 0){
            sort(a, lt, gt, d+1);
        }
        sort(a, gt+1, high, d);

    }

    // ! Important helper to replace regular string.charAt()
    private static int charAt(String s, int d) {
        if (d < s.length()) {
            return s.charAt(d);
        } else {
            return -1;
        }
    }

    // * helper
    private static void exch(String[] a, int i, int j) {
        String t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args){
        // * test #1
        StdOut.println("1. test shells.txt");
        String filename = "data/shells.txt";
        String[] input = new In(filename).readAllStrings();
        
        // ? test empty string, charAt() return -1
        // input[0] = "";

        StdOut.println("Before sort:");
        for (String s : input) {
            StdOut.println(s);
        }

        Quick3String.sort(input);
        StdOut.println("\nAfter sort:");
        for (String s : input) {
            StdOut.println(s);
        }
    }
}