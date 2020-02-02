package javasrc.ch02_1;

/*
* 2.1.31 Doubling test. 
Write a client that performs a doubling test for sort algorithms. Start at N 
equal to 1000, and print N, the predicted number of seconds, the actual num-
ber of seconds, and the ratio as N doubles. 

Use your program to validate that insertion sort and selection sort are quadratic 
for random inputs, and formulate and test a hypothesis for shellsort.
*/

/*
* 2.1.32 Plot running times. 
Write a client that uses StdDraw to plot the average running times of the algorithm 
for random inputs and various values of the array size. 

You may add one or two more command-line arguments. Strive to design a useful tool.
*/

import lib.StdDraw;
import lib.StdOut;
import lib.StdRandom;
import javasrc.ch01_4.Stopwatch;

public class DoublingTest {

    private static double getTime(String algo, int N) {
        double result = 0;

        Double[] a = new Double[N];
        for (int i = 0; i < N; i++) {
            a[i] = StdRandom.uniform();
        }

        Stopwatch timer = new Stopwatch();
        if (algo.equals("Selection")) {
            Selection.sort(a);
        } else if (algo.equals("Insertion")) {
            Insertion.sort(a);
        } else if (algo.equals("Shell")) {
            Shell.sort(a);
        }
        result = timer.elapsedTime();

        return result;
    }

    public static void main(String[] args) {

        String algo = args[0];
        int N = Integer.parseInt(args[1]);
        int T = Integer.parseInt(args[2]);

        int maxY = 5;
        StdDraw.setXscale(-1, T+1);
        StdDraw.setYscale(-1, maxY+1);
        StdDraw.setPenRadius(0.001);
        StdDraw.line(0, -0.01, T, -0.01);
        StdDraw.line(-0.01, 0, -0.01, maxY);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.textLeft(0.2, maxY*0.95, (algo + " sort"));
        StdDraw.textLeft((T/2 - 0.4), -0.7, "Array size");
        StdDraw.text((-0.5), (maxY/2),"Time", 90);

        StdOut.println("For " + algo + ":\n");
        StdDraw.setPenColor(StdDraw.RED);

        for (int i = 0; i < 10; i++) {
            double totalTime = 0;
            for (int j = 0; j < T; j++) {
                totalTime += getTime(algo, N);
            }
            double time = totalTime / T;

            // * Console output
            StdOut.printf("Array size: %d used %.4f seconds.\n", N, time);
            StdOut.printf("If algorithmn is quadratic, next time would be: %.4f seconds.\n\n", 
            (time * 2 * 2));

            // * Plot average running time
            StdDraw.point(i, time);

            N *= 2;
        }

    }

}