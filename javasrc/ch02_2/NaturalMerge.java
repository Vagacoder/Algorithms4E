package javasrc.ch02_2;

/*
* 2.2.16 Natural mergesort. 
Write a version of bottom-up mergesort that takes advantage of order in the array 
by proceeding as follows each time it needs to find two arrays to merge: find a 
sorted subarray (by incrementing a pointer until finding an entry that is smaller 
than its predecessor in the array), then find the next, then merge them. 

Analyze the running time of this algorithm in terms of the array size and the number 
of maximal increasing sequences in the array.


*/

import lib.StdOut;

public class NaturalMerge{

    private static Comparable[] aux;

    public static void sort(Comparable[] a) { 
        aux = new Comparable[a.length];

        boolean sorted = false;

        while(!sorted){
            sorted = true;
            boolean secondHalf = false;
            int N = a.length;
            int low = 0;
            int mid = 0;
            int high = 0;
            int cur = 0;
            while (cur < N-1){

                // Search first half and second half
                if (less(a[cur+1], a[cur])){
                    if(secondHalf){
                        high = cur;
                        secondHalf = false;
                    }else {
                        mid = cur;
                        secondHalf = true;
                    }
                }

                // Special case: second half is at end of array
                if (secondHalf && cur == N - 2){
                    high = N - 1;
                    secondHalf = false;
                }

                // merge first and second halves.
                if (high > low){
                    merge(a, low, mid, high);
                    sorted = false;
                    low = cur + 1;
                    mid = low;
                    high = low;
                }
                cur++;
            }
        }
    }

    private static void merge(Comparable[] a, int low, int mid, int high){
        int i = low, j = mid + 1;

        for (int k = 0; k < a.length; k++){
            aux[k] = a[k];
        }

        for (int k = low; k <= high; k++){
            if(i>mid){
                a[k] = aux[j++];
            } else if (j > high){
                a[k] = aux[i++];
            }else if( less(aux[i], aux[j])){
                a[k] = aux[i++];
            } else {
                a[k] = aux[j++];
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

    public static void main(String[] args) { 
        // Read strings from standard input, sort them, and print.
        // String[] a = In.readStrings();
        // sort(a);
        // assert isSorted(a);
        // show(a);

        StdOut.println(check());
    }

}