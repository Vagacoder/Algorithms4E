package javasrc.ch02_1;

/* Compare 2 sorting algorithms. P. 256 
This is good template for comparing different algorithms.
Later we will use similar programs many times.
*/

/*
* Client of ex 2.1.26
*/

import lib.StdRandom;
import lib.StdOut;
import javasrc.ch01_4.Stopwatch;

public class ex2_1_26 {


    public static void timeRandomInput(int number, int times) {
        double totalA = 0.0;
        double totalB = 0.0;

        Integer[] a = new Integer[number];
        int[] b = new int[number];

        for (int t = 0; t < times; t++) {
            for (int i = 0; i < number; i++) {
                int random = StdRandom.uniform(0, 10000);
                a[i] = random;
                b[i] = random;
            }

            Stopwatch timer = new Stopwatch();
            Insertion.sort(a);
            totalA += timer.elapsedTime();
            
            Stopwatch timer1 = new Stopwatch();
            Insertion.sortInt(b);
            totalB += timer1.elapsedTime();
        }

        StdOut.printf("For %d random Doubles\n    %s is", number, "Insertion Generic");
        StdOut.printf(" %.1f times faster than %s\n", totalA / totalB, "Insertion Primitive");

    }

    public static void main(String[] args) {
        int number = Integer.parseInt(args[0]);
        int times = Integer.parseInt(args[1]);

        timeRandomInput(number, times);
    }
}