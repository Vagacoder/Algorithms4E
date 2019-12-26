package javasrc.ch01_4;

/******************************************************************************
 *  Compilation:  javac DoublingTest.java
 *  Execution:    java DoublingTest
 *  Dependencies: ThreeSum.java Stopwatch.java StdRandom.java StdOut.java
 *
 *  % java DoublingTest 
 *      250     0.0
 *      500     0.0
 *     1000     0.1
 *     2000     0.6
 *     4000     4.5
 *     8000    35.7
 *  ...
 *
 ******************************************************************************/

 /*
1.4.3 Modify DoublingTest to use StdDraw to produce plots like the standard and
log-log plots in the text, rescaling as necessary so that the plot always fills 
a substantial portion of the window.

 */
import lib.StdRandom;
import lib.StdOut;

import java.awt.Color;

import lib.StdDraw;

public class DoublingTest {

    private static final int MAXIMUM_INTEGER = 1000000;

    private DoublingTest(){}

    public static double timeTrial(int n){
        int[] a = new int[n];
        for(int i = 0; i < n; i++){
            a[i] = StdRandom.uniform(-MAXIMUM_INTEGER, MAXIMUM_INTEGER);
        }
        Stopwatch timer = new Stopwatch();
        ThreeSumFast.count(a);
        return timer.elapsedTime();
    }

    public static void main(String[] args){

        int X = 10000;
        int Y = 100;
        StdDraw.setXscale(0, X);
        StdDraw.setYscale(0, Y);
        StdDraw.setPenRadius(0.02);
        

        for(int n=250; true; n += n){
            double time = timeTrial(n);
            StdOut.printf("%7d %7.1f\n\n", n, time);
            StdDraw.setPenColor(Color.RED);
            StdDraw.point(n, time);
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.point(Math.log(n), Math.log(time));
        }
    }

}