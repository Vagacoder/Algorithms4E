package javasrc.ch01_4;
/*
You should consider running doubling ratio experiments for every program that
you write where performance matters
*/
import lib.StdRandom;
import lib.StdOut;

public class DoublingRatio{

    private DoublingRatio(){}
    private static final int MAXIMUM_INTEGER = 1000000;

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
        double prev = timeTrial(125);
        for(int n = 250; true; n*=2){
            double time = timeTrial(n);
            StdOut.printf("%6d %7.1f", n, time);
            StdOut.printf("%5.1f\n", time/prev);
            prev = time;
        }
    }
}