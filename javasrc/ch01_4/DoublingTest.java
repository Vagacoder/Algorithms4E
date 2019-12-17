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
import lib.StdRandom;
import lib.StdOut;

public class DoublingTest {

    private static final int MAXIMUM_INTEGER = 1000000;

    private DoublingTest(){}

    public static double timeTrial(int n){
        int[] a = new int[n];
        for(int i = 0; i < n; i++){
            a[i] = StdRandom.uniform(-MAXIMUM_INTEGER, MAXIMUM_INTEGER);
        }
        Stopwatch timer = new Stopwatch();
        ThreeSum.count(a);
        return timer.elapsedTime();
    }

    public static void main(String[] args){
        for(int n=250; true; n += n){
            double time = timeTrial(n);
            StdOut.printf("%7d %7.1f\n\n", n, time);
        }
    }

}