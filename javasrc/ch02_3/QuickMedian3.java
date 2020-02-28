package javasrc.ch02_3;

/*
* 2.3.18 Median-of-3 partitioning. 
Add median-of-3 partitioning to quicksort, as described in the text (see page 296). 

Run doubling tests to determine the effectiveness of the change.

*/

import javasrc.ch02_1.DoublingTest;
import lib.*;


public class QuickMedian3{


    public static void sort(Comparable[] a) { 
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int low, int high){
        if ( high <= low) {
            return;
        }
        int j = partition(a, low, high);
        sort(a, low, j - 1);
        sort(a, j + 1, high);
    }
    
    // * 2.3.18 
    private static int partition(Comparable[] a, int low, int high){
        int i = low, j = high + 1;

        exch(a, low, findPivotIndex(a, low, high));
        Comparable pivot = a[low];

        while(true){
            while(less(a[++i], pivot)){
                if(i == high) {
                    break;
                }
            }
            while(less(pivot, a[--j])){
                if(j == low){
                    break;
                }
            }
            if(i >= j){
                break;
            } 
            exch(a, i, j);
        }
        exch(a, low, j);
        return j;
    }

    // * 2.3.18 helper, find median of random 3
    private static int findPivotIndex(Comparable[] a, int low, int high){
        int index = low;
        if(high - low > 1){
            int mid = (low + high) /2;
            if (less(a[low], a[mid])){
                if(less(a[mid], a[high])){
                    return mid;
                } else if(less(a[high], a[low])){
                    return low;
                }  else{
                    return high;
                }
            } else {
                if(less(a[low], a[high])){
                    return low;
                } else if(less(a[high], a[mid])){
                    return mid;
                }  else{
                    return high;
                }
            }
        }

        return index;
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
        StdOut.println("1. Confirm sort() works correctly ... ");
        StdOut.println(check());
        StdOut.println();

        StdOut.print("2. Doubling test ... ");
        int repeat = 20;
        StdOut.println("# each size repeats " + repeat + " times");
        for (int N = 1000; N < 100000; N *= 2){
            double quickTotal = 0;
            double quickM3Total = 0;
            for (int i = 0; i < repeat; i++){
                quickTotal += DoublingTest.getTime("Quick", N);
                quickM3Total += DoublingTest.getTime("QuickM3", N);
            }

            StdOut.println("for random array size of " + N);
            StdOut.println("\tAverage for regular quick sort:" + quickTotal / repeat);
            StdOut.println("\tAverage for median3 quick sort:" + quickM3Total / repeat);
            StdOut.println();
        }
    }

}