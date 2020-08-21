package javasrc.ch05_1;

/*
* Algorithm 5.2 MSD string sort. P.712 
* 
* The code in Algorithm 5.2 is deceptively simple, masking a rather sophisticated 
* computation.  Be sure understand the intricacies of the algorithm.
*
* Proposition C. To sort N random strings from an R-character alphabet, MSD string 
* sort examines about N log R N characters, on average.
*
* Proposition D. MSD string sort uses between 8N + 3R and ~7wN + 3WR array ac 
* cesses to sort N strings taken from an R-character alphabet, where w is the 
* average string length.
*
* When N is small, the factor of R dominates.
*
* Proposition D (continued). To sort N strings taken from an R-character alphabet,
* the amount of space needed by MSD string sort is proportional to R times the
* length of the longest string (plus N ), in the worst case.

 ! It is quite possible for MSD string sort to run out of time or space when sorting
 ! long strings taken from large alphabets, particularly if long equal keys are to 
 ! be expected.

 ? Sample file:
 ? data/shells.txt (P. 713)
 ? data/plates.txt
 ? data/words3.txt 


 * 5.1.8 Give the number of characters examined by MSD string sort and 3-way string
 * quicksort for a file of N keys a, aa, aaa, aaaa, aaaaa, . . .

*/

import javasrc.ch02_1.Insertion;
import javasrc.ch02_1.InsertionRange;
import lib.*;

public class MSD {

    // * radix
    private static int R = 256;

    // * cutoff for small subarrays, original is 15, set to 0 for study
    // private static final int M = 15;
    private static final int M = 0;

    // * auxiliary array for distribution
    private static String[] aux;


    // * 5.1.8 
    private static int charExamCount = 0;


    // ! Important helper to replace regular string.charAt()
    private static int charAt(String s, int d) {
        if (d < s.length()) {
            return s.charAt(d);
        } else {
            return -1;
        }
    }

    public static void sort(String[] a) {
        
        // * 5.1.8
        charExamCount = 0;

        int N = a.length;
        aux = new String[N];
        sort(a, 0, N - 1, 0);
    }

    // * recursive helper, sort from a[low] to a[high], starting at the dth
    // character
    private static void sort(String[] a, int low, int high, int d) {
        if (high <= low + M) {
            insertionSort(a, low, high, d);
            return;
        }

        // * Compute frequency counts
        int[] count = new int[R + 2];

        for (int i = low; i <= high; i++) {
            count[charAt(a[i], d) + 2]++;
            
            // * 5.1.8
            charExamCount++;
        }

        // * Transform counts to indeces
        for (int r = 0; r < R + 1; r++) {
            count[r + 1] += count[r];
        }

        // * Distribute
        for (int i = low; i <= high; i++) {
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        }

        // * Copy back
        for (int i = low; i <= high; i++) {
            a[i] = aux[i - low];
        }

        // ! Recursively sort for each character value
        for (int r = 0; r < R; r++) {
            sort(a, low + count[r], low + count[r + 1] - 1, d + 1);
        }
    }

    // * helper insertion sort (on string)
    public static void insertionSort(String[] a, int low, int high, int d) {
        for (int i = low; i <= high; i++) {
            for (int j = i; j > low && less(a[j], a[j - 1], d); j--) {
                exch(a, j, j - 1);
            }
        }
    }


    // * 5.1.8
    public static int getCharExamCount(){
        return charExamCount;
    }


    // * helper
    private static boolean less(String s1, String s2, int d) {
        for (int i = d; i < Math.min(s1.length(), s2.length()); i++) {
            if (s1.charAt(i) < s2.charAt(i)) {
                return true;
            } else if (s1.charAt(i) > s2.charAt(i)) {
                return false;
            }
        }
        return s1.length() < s2.length();
    }

    // * helper
    private static void exch(String[] a, int i, int j) {
        String t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        // * test #1
        StdOut.println("1. test shells.txt");
        String filename = "data/shells.txt";
        String[] input = new In(filename).readAllStrings();
        
        // ? test empty string, charAt() return -1
        input[0] = "";

        StdOut.println("Before sort:");
        for (String s : input) {
            StdOut.println(s);
        }

        MSD.sort(input);
        StdOut.println("\nAfter sort:");
        for (String s : input) {
            StdOut.println(s);
        }

        // * test #2
        StdOut.println("2. test words3.txt");
        filename = "data/words3.txt";
        input = new In(filename).readAllStrings();
        StdOut.println("Before sort:");
        for (String s : input) {
            StdOut.println(s);
        }

        MSD.sort(input);
        StdOut.println("\nAfter sort:");
        for (String s : input) {
            StdOut.println(s);
        }

        // * test #3
        StdOut.println("\n3. test plates.txt");
        filename = "data/plates.txt";
        input = new In(filename).readAllStrings();
        StdOut.println("Before sort:");
        for (String s : input) {
            StdOut.println(s);
        }

        MSD.sort(input);
        StdOut.println("\nAfter sort:");
        for (String s : input) {
            StdOut.println(s);
        }

        // * 5.1.8
        StdOut.println("\n3. test plates.txt");
        filename = "data/for5_1_8.txt";
        input = new In(filename).readAllStrings();

        MSD.sort(input);
        StdOut.println("Exam char:" + MSD.getCharExamCount());
    }
}