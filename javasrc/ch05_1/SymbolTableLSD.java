package javasrc.ch05_1;

/*
* 5.1.1 Develop a sort implementation that counts the number of different key 
* values, then uses a symbol table to apply key-indexed counting to sort the 
* array. (This method is not for use when the number of different key values is 
* large.)
* 
* Use Symbol table, SequentialSearchST (ch3.1)

*/

import javasrc.ch03_1.SequentialSearchST;
import lib.*;

public class SymbolTableLSD {

    public static void sort(String[] a, int W) {

        int n = a.length;
        int R = 256;
        String[] aux = new String[n];
        // SequentialSearchST<String, Integer> aux = new SequentialSearchST<>();

        // * sort the strings W times
        for (int d = W - 1; d >= 0; d--) {

            // * initial symbol table for count, R+1 counts
            SequentialSearchST<Integer, Integer> count = new SequentialSearchST<>();
            for (int r = 0; r < R+1; r++) {
                count.put(r, 0);
            }

            // * 1. Computer frequency counts
            for (int i = 0; i < n; i++) {
                int charInt = a[i].charAt(d) + 1;
                Integer freq = count.get(charInt);
                count.put((charInt), freq + 1);
            }

            // * 2. Transform counts to indeces
            for (int r = 0; r < R; r++) {
                Integer freqAtR = count.get(r);
                Integer freqAtRplusOne = count.get(r + 1);
                count.put((r + 1), freqAtRplusOne + freqAtR);
            }

            // * 3. Distribute
            for (int i = 0; i < n; i++) {
                int charInt = a[i].charAt(d);
                int newIndex = count.get(charInt);
                aux[newIndex] = a[i];
                newIndex++;
                count.put(charInt, newIndex);
            }

            // * 4. Copy back
            for (int i = 0; i < n; i++) {
                a[i] = aux[i];
            }

        }
    }

    public static void main(String[] args) {
        // * test #1
        StdOut.println("1. test words3.txt");
        String filename = "data/words3.txt";
        String[] input = new In(filename).readAllStrings();
        StdOut.println("Before sort");
        for (String s : input) {
            StdOut.println(s);
        }

        SymbolTableLSD.sort(input, input[0].length());
        StdOut.println("\nAfter sort");
        for (String s : input) {
            StdOut.println(s);
        }

        // * test #2
        StdOut.println("\n2. test plates.txt");
        filename = "data/plates.txt";
        input = new In(filename).readAllStrings();
        StdOut.println("Before sort");
        for(String s: input){
            StdOut.println(s);
        }

        SymbolTableLSD.sort(input, input[0].length());
        StdOut.println("\nAfter sort");
        for(String s: input){
            StdOut.println(s);
        }
}

}