package javasrc.ch01_4;

import java.util.ArrayList;
import java.util.List;

/*
1.4.18 Local minimum of an array. 
Write a program that, given an array a[] of N distinct integers, finds a local 
minimum: an entry a[i] that is strictly less than its neighbors. Each internal 
entry (other than a[0] and a[N-1]) has 2 neighbors. 

Your program should use ~2 lg N compares in the worst case.

*/

import lib.StdOut;

public class LocalMinimum {

    // My own solution of ~ N, and find all local minimums
    public static List<Integer> find1(int[] a) {
        ArrayList<Integer> result = new ArrayList<>();
        ;
        int N = a.length;
        if (N < 3) {
            return result;
        }

        for (int i = 1; i < N - 1; i++) {
            if ((a[i - 1] > a[i]) && (a[i + 1] > a[i])) {
                result.add(i);
            }
        }

        return result;
    }

    // The idea from the web answer, which does not make sense.
    public static int find2(int[] a) {
        int result = -1;
        int N = a.length;
        if (N < 3) {
            return result;
        }
        int low = 0;
        int high = N - 1;

        while (low < high) {
            int mid = (low + high) / 2;
            if ((a[mid - 1] > a[mid]) && (a[mid + 1] > a[mid])) {
                return mid;
            } else {
                if (a[mid - 1] < a[mid + 1]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] a = {};
        int[] b = { 1 };
        int[] c = { 1, 2 };
        int[] d = { 2, 1 };
        int[] e = { 1, 2, 3 };
        int[] f = { 2, 1, 3 };
        int[] g = { 1, 3, 2 };
        int[] h = { 1, 2, 3, 4, 3, 6 };

        StdOut.println(find1(a));
        StdOut.println(find1(b));
        StdOut.println(find1(c));
        StdOut.println(find1(d));
        StdOut.println(find1(e));
        StdOut.println(find1(f));
        StdOut.println(find1(g));
        StdOut.println(find1(h));
    }
}