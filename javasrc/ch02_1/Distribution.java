package javasrc.ch02_1;

/*
* 2.1.33 Distribution. 
Write a client that enters into an infinite loop running sort() on arrays of the 
size given as the third command-line argument, measures the time taken for each 
run, and uses StdDraw to plot the average running times. A picture of the dis-
tribution of the running times should emerge.

*/

import lib.StdDraw;
import lib.StdRandom;
import java.awt.Color;
import javasrc.ch01_4.Stopwatch;

public class Distribution {

    private static double getRunningTime(String algo, int size) {
        Double[] a = new Double[size];
        for (int i = 0; i < size; i++) {
            a[i] = StdRandom.uniform();
        }
        Stopwatch timer = new Stopwatch();
        if(algo.equals("Insertion")){
            Insertion.sort(a);
        }else if(algo.equals("Selection")){
            Selection.sort(a);
        }else if(algo.equals("Shell")){
            Shell.sort(a);
        }else if(algo.equals("Bubble")){
            Bubble.sort(a);
        }

        return timer.elapsedTime();
    }

    public static void main(String[] args) {
        String algo1 = args[0];
        // String algo2 = args[1];
        int size = Integer.parseInt(args[2]);
        int number = 0;
        double totalTime = 0;
        StdDraw.setXscale(0, 400);
        StdDraw.setYscale(0, 5);
        StdDraw.setPenRadius(0.005);

        while (true) {
            number++;
            double time = getRunningTime(algo1, size);
            totalTime += time;
            double averageTime = totalTime/number;
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.point(number, time);
            StdDraw.setPenColor(Color.RED);
            StdDraw.point(number, averageTime);
        }

    }
}