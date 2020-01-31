package javasrc.ch02_1;

/* Compare 2 sorting algorithms. P. 256 
This is good template for comparing different algorithms.
Later we will use similar programs many times.
*/

/*
* 2.1.29 Shellsort increments. 
Run experiments to compare the increment sequence in Algorithm 2.3 with the sequence: 
1, 5, 19, 41, 109, 209, 505, 929, 2161, 3905, 8929, 16001, 36289, 64769, 146305, 260609 
(which is formed by merging together the sequences 9·4^k - 9·2^k+1 and 4^k + 3·2^k + 1). 
See Exercise 2.1.11.

*/

import lib.StdRandom;
import lib.StdOut;
import javasrc.ch01_4.Stopwatch;

public class ex2_1_19 {

    public static double time(String alg, Double[] a) {
        Stopwatch timer = new Stopwatch();

        if (alg.equals("Shell")) {
            Shell.sort(a);
        }        
        if (alg.equals("ShellX")) {
            Shell.sort1(a);
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