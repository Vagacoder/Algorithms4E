package javasrc.ch01_4;

/*
1.4.15 Faster 3-sum. 
Using a linear algorithm to count the three numbers that sum to zero after the 
array is sorted (instead of the binary-search-based linearithmic algorithm).\

Note: the practice of Faster 2-sum is done in TwoSumFaster.java

*/

import lib.StdOut;
import lib.In;
import java.util.Arrays;

public class ThreeSumFaster {

    public static int count(int[] a) {
        Arrays.sort(a);
        int N = a.length;
        int result = 0;

        for (int i = 0; i < N - 1; i++) {
            int curLow = i + 1;
            int curHigh = N - 1;

            while (curHigh > curLow) {
                if (a[curLow] + a[curHigh] > -a[i]) {
                    curHigh--;
                } else if (a[curLow] + a[curHigh] < -a[i]) {
                    curLow++;
                } else {
                    result++;
                    // StdOut.println(a[curLow] + " " + a[curHigh]);
                    curHigh--;
                    curLow++;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int[] a = in.readAllInts();
        Stopwatch timer = new Stopwatch();
        StdOut.println(count(a));
        StdOut.println("It takes time of " + timer.elapsedTime());
    }
}