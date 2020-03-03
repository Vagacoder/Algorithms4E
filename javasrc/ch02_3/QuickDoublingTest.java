package javasrc.ch02_3;

/* 
* This summary of doubling tests for all variants of Quick Sort.

*/

import lib.*;
import javasrc.ch02_1.DoublingTest;

public class  QuickDoublingTest { 

    public static void main(String[] args){
        StdOut.print("1. Doubling test ... ");
        int repeat = 20;
        StdOut.println("# each size repeats " + repeat + " times");
        for (int N = 1000; N < 100000; N *= 2) {
            
            double quickTotal = 0;
            double quickM3Total = 0;
            double quickM5Total = 0;
            double quickNoRecurTotal = 0;
            double quick3wayTotal =0;
            double quickFast3wTotal = 0;

            for (int i = 0; i < repeat; i++) {
                quickTotal += DoublingTest.getTime("Quick", N);
                quickM3Total += DoublingTest.getTime("QuickM3", N);
                quickM5Total += DoublingTest.getTime("QuickM5", N);
                quickNoRecurTotal += DoublingTest.getTime("QuickNoRecur", N);
                quick3wayTotal += DoublingTest.getTime("Quick3W", N);
                quickFast3wTotal += DoublingTest.getTime("QuickFast3W", N);
            }

            StdOut.println("for random array size of " + N);
            StdOut.println("\tAverage for regular quick sort:" + quickTotal / repeat);
            StdOut.println("\tAverage for median3 quick sort:" + quickM3Total / repeat);
            StdOut.println("\tAverage for median5 quick sort:" + quickM5Total / repeat);
            StdOut.println("\tAverage for no recu quick sort:" + quickNoRecurTotal / repeat);
            StdOut.println("\tAverage for   3 way quick sort:" + quick3wayTotal / repeat);
            StdOut.println("\tAverage for fast 3w quick sort:" + quickFast3wTotal / repeat);
            StdOut.println();
        }
    }
}