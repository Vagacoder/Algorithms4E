package javasrc.ch02_3;

/*
* 2.3.19 Median-of-5 partitioning. 
Implement a quicksort based on partitioning on the median of a random sample of 
five items from the subarray. Put the items of the sample at the appropriate ends 
of the array so that only the median participates in partitioning.

Run doubling tests to determine the effectiveness of the change, in comparison both
to the standard algorithm and to median-of-3 partitioning (see the previous exercise).

Extra credit : Devise a median-of-5 algorithm that uses fewer than seven compares on
any input.

*/

import lib.*;

import java.util.Arrays;

import javasrc.ch02_1.DoublingTest;

public class QuickMedian5 {

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

    // * 2.3.19
    private static int partition(Comparable[] a, int low, int high) {
        int i = low, j = high + 1;

        exch(a, low, findPivotIndex(a, low, high));
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

    // * 2.3.19 helper, find median of random 5
    private static int findPivotIndex(Comparable[] a, int low, int high){
        // TODO update to median of 5
        int index = low;

        if(high - low < 4){
            return index;
        }

        // ! Bad solution, but works
        // int index0 = StdRandom.uniform(low, high);
        // int index1 = StdRandom.uniform(low, high);
        // int index2 = StdRandom.uniform(low, high);
        // int index3 = StdRandom.uniform(low, high);
        // int index4 = StdRandom.uniform(low, high);

        // Comparable[] b = new Comparable[5];
        // b[0] = a[index0];
        // b[1] = a[index1];
        // b[2] = a[index2];
        // b[3] = a[index3];
        // b[4] = a[index4];
        // Arrays.sort(b);

        // if(a[index0].compareTo(b[2]) == 0){
        //     return index0;
        // } else if(a[index1].compareTo(b[2]) == 0){
        //     return index1;
        // } else if(a[index2].compareTo(b[2]) == 0){
        //     return index2;
        // } else if(a[index3].compareTo(b[2]) == 0){
        //     return index3;
        // } else if(a[index4].compareTo(b[2]) == 0){
        //     return index4;
        // }

        // ? Trying a better solution;
        // int index0 = StdRandom.uniform(low, high);
        // int index1 = StdRandom.uniform(low, high);
        // int index2 = StdRandom.uniform(low, high);
        // int index3 = StdRandom.uniform(low, high);
        // int index4 = StdRandom.uniform(low, high);

        int index0 = low;
        int index1 = low + 1;
        int index2 = low + 2;
        int index3 = low + 3;
        int index4 = low + 4;

        Integer[] b = new Integer[5];
        b[0] = index0;
        b[1] = index1;
        b[2] = index2;
        b[3] = index3;
        b[4] = index4;

        // * find median of 5
        // ! compare +1, to 1
        if(less(a[b[1]], a[b[0]])){
            exch(b, 0, 1);
        }
        // ! compare +1, to 2
        if (less(a[b[2]], a[b[1]])){
            exch(b, 1, 2);
        }
        // ! compare +1, to 3
        // ? till here, first 3 are sorted
        if (less(a[b[1]], a[b[0]])){
            exch(b, 0, 1);
        }
        // ! compare max +2, to 5
        // ? till here first 4 are sorted
        if (less(a[b[3]], a[b[1]])){
            if(less(a[b[3]] , a[b[0]])){
                exch(b, 2, 3);
                exch(b, 1, 2);
                exch(b, 0, 1);
            }else {
                exch(b, 2, 3);
                exch(b, 1, 2);
            }
        }else {
            if(less(a[b[3]], a[b[2]])){
                exch(b, 2, 3);
            }
        }
        // ! compare max +2, to 7
        if(less(a[b[4]], a[b[1]])){
            index = b[1];
        } else {
            if (less(a[b[4]], a[b[2]])){
                index = b[4];
            }else {
                index = b[2];
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

        StdOut.println("0. Testing find median of 5 ... ");

        // ? Single test 
        // Integer[] b = {2, 4, 5, 3, 1};
        // int pivotIndex = findPivotIndex(b, 0, 4);
        // StdOut.println("\n" + pivotIndex);

        // ? Batch test
        // Boolean good = true;
        // for (int i = 0; i < 100; i++){
        //     Integer[] a = {1, 2, 3, 4, 5};
        //     StdRandom.shuffle(a);
        //     for(int j = 0; j < a.length; j++){
        //         StdOut.print(a[j] + ", ");
        //     }
        //     int pivotIndex = findPivotIndex(a, 0, 4);
        //     StdOut.println("\n" + pivotIndex);
        //     if(a[pivotIndex] != 3){
        //         StdOut.println("Wrong!");
        //         good = false;
        //         break;
        //     }
        // }
        // if(good){
        //     StdOut.println("Passed!");
        // } else {
        //     StdOut.println("Failed!");
        // }


        // StdOut.println("1. Confirm sort() works correctly ... ");
        StdOut.println(check());
        StdOut.println();

        StdOut.print("2. Doubling test ... ");
        int repeat = 20;
        StdOut.println("# each size repeats " + repeat + " times");
        for (int N = 1000; N < 100000; N *= 2) {
            double quickTotal = 0;
            double quickM3Total = 0;
            double quickM5Total = 0;
            for (int i = 0; i < repeat; i++) {
                quickTotal += DoublingTest.getTime("Quick", N);
                quickM3Total += DoublingTest.getTime("QuickM3", N);
                quickM5Total += DoublingTest.getTime("QuickM5", N);
            }

            StdOut.println("for random array size of " + N);
            StdOut.println("\tAverage for regular quick sort:" + quickTotal / repeat);
            StdOut.println("\tAverage for median3 quick sort:" + quickM3Total / repeat);
            StdOut.println("\tAverage for median5 quick sort:" + quickM5Total / repeat);
            StdOut.println();
        }
    }

}