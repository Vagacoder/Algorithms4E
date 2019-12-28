package javasrc.ch01_4;

/*
1.4.15 As a warmup, develop an implementation TwoSumFaster that
uses a linear algorithm to count the pairs that sum to zero after the array is sorted (in-
stead of the binary-search-based linearithmic algorithm). 
*/

import java.util.Arrays;
import lib.StdOut;
import lib.In;

public class TwoSumFaster {

    public static int count(int[] a, int key) {
        Arrays.sort(a);
        int result = 0;
        int N = a.length;
        int curLow = 0;
        int curHigh = N - 1;

        while (curHigh > curLow) {
            if (a[curLow] + a[curHigh] > key) {
                curHigh--;
            } else if (a[curLow] + a[curHigh] < key) {
                curLow++;
            } else {
                result++;
                StdOut.println(a[curLow] + " " + a[curHigh]);
                curHigh--;
                curLow++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int[] a = in.readAllInts();
        StdOut.println(count(a, 0));
    }
}