package javasrc.ch02_1;

/* 
* Compare 2 sorting algorithms. P. 256 
This is good template for comparing different algorithms.
Later we will use similar programs many times.

* Property D. The running times of insertion sort and selection sort are quadratic
and within a small constant factor of one another for randomly ordered arrays of
distinct values.

*/

import lib.StdRandom;
import lib.StdOut;
import javasrc.ch01_4.Stopwatch;
import javasrc.ch02_2.Merge;
import javasrc.ch02_3.Quick;
import javasrc.ch02_4.Heap;

public class SortCompare {

    public static double time(String alg, Double[] a) {
        Stopwatch timer = new Stopwatch();
        if (alg.equals("Insertion")) {
            Insertion.sort(a);
        }
        if (alg.equals("Selection")) {
            Selection.sort(a);
        }
        if (alg.equals("Shell")) {
            Shell.sort(a);
        }
        if (alg.equals("Merge")) {
            Merge.sort(a);
        }
        if (alg.equals("Quick")) {
            Quick.sort(a);
        }
        if (alg.equals("Heap")) {
            Heap.sort(a);
        }
        if (alg.equals("Bubble")) {
            Bubble.sort(a);
        }
        return timer.elapsedTime();
    }

    public static double timeRandomInput(String alg, int number, int times) {
        double total = 0.0;
        Double[] a = new Double[number];
        for (int t = 0; t < times; t++) {
            for (int i = 0; i < number; i++) {
                a[i] = StdRandom.uniform();
            }
            total += time(alg, a);
        }
        return total;
    }

    public static void main(String[] args) {
        String alg1 = args[0];
        String alg2 = args[1];
        int number = Integer.parseInt(args[2]);
        int times = Integer.parseInt(args[3]);
        double time1 = timeRandomInput(alg1, number, times);
        double time2 = timeRandomInput(alg2, number, times);
        StdOut.printf("For %d random Doubles\n    %s is", number, alg1);
        StdOut.printf(" %.1f times faster than %s\n", time2 / time1, alg2);
    }
}