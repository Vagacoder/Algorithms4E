package javasrc.ch02_3;


/*
* 2.3.20 Nonrecursive quicksort. 
Implement a nonrecursive version of quicksort based on a main loop where a subarray 
is popped from a stack to be partitioned, and the resulting subarrays are pushed 
onto the stack. 

Note : Push the larger of the subarrays onto the stack first, which guarantees 
that the stack will have at most lg N entries.

*/

import edu.princeton.cs.algs4.Stack;
import lib.*;
import javasrc.ch02_1.DoublingTest;


// * 2.3.20 helper class
class SubArrayInfo{

    int low;
    int high;

    SubArrayInfo(int low, int high){
        this.low = low;
        this.high = high;
    }
}

public class QuickNoRecursive{

    // * 2.3.20
    public static void sort(Comparable[] a) { 
        StdRandom.shuffle(a);
        Stack stack = new Stack<>();
        int length = a.length;
        SubArrayInfo aInfo= new SubArrayInfo(0, length - 1);
        stack.push(aInfo);

        while(!stack.isEmpty()){
            SubArrayInfo workingArray = (SubArrayInfo) stack.pop();
            int j = partition(a, workingArray.low, workingArray.high);
            if (j + 1 < workingArray.high){
                stack.push(new SubArrayInfo(j + 1, workingArray.high));
            }
            if (workingArray.low < j-1) {
                stack.push(new SubArrayInfo(workingArray.low, j - 1));
            }
        }
    }


    private static int partition(Comparable[] a, int low, int high){
        // if ( high <= low) {
        //     return low;
        // }
        int i = low, j = high + 1;
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
        // Read strings from standard input, sort them, and print.
        // String[] a = In.readStrings();
        // sort(a);
        // assert isSorted(a);
        // show(a);

        StdOut.println("1. Confirm sort() works correctly ... ");
        StdOut.println("2 default arrays ... ");
        StdOut.println(check());
        StdOut.println("multiple arrays ... ");
        int length = 10000;
        int repeat = 20;
        boolean good = true;
        for (int i = 0; i < repeat; i++) {
            Double[] a = new Double[length];
            for (int j = 0; j < length; j++){
                a[j] = StdRandom.uniform();
            }
            sort(a);
            if(!isSorted(a)){
                good = false;
                break;
            }
        }
        StdOut.println(good?"Successful!":"Failed!"); 
        StdOut.println();

        StdOut.print("2. Doubling test ... ");
        StdOut.println("# each size repeats " + repeat + " times");
        for (int N = 1000; N < 100000; N *= 2) {
            double quickTotal = 0;
            double quickM3Total = 0;
            double quickM5Total = 0;
            double quickNoRecurTotal = 0;
            for (int i = 0; i < repeat; i++) {
                quickTotal += DoublingTest.getTime("Quick", N);
                quickM3Total += DoublingTest.getTime("QuickM3", N);
                quickM5Total += DoublingTest.getTime("QuickM5", N);
                quickNoRecurTotal += DoublingTest.getTime("QuickNoRecur", N);
            }

            StdOut.println("for random array size of " + N);
            StdOut.println("\tAverage for regular quick sort:" + quickTotal / repeat);
            StdOut.println("\tAverage for median3 quick sort:" + quickM3Total / repeat);
            StdOut.println("\tAverage for median5 quick sort:" + quickM5Total / repeat);
            StdOut.println("\tAverage for no recu quick sort:" + quickNoRecurTotal / repeat);
            StdOut.println();
        }
    }

}

