package javasrc.ch01_4;

/*
1.4.20 Bitonic search. 
An array is bitonic if it is comprised of an increasing sequence of integers 
followed immediately by a decreasing sequence of integers. 

Write a program that:
Given a bitonic array of N distinct int values, determines whether a given integer is
in the array. 

Note: Input array is guaranteed to be bitonic sequence (and length >= 3)

Requirement: Your program should use ~3 lg N compares in the worst case. 
Extra credit: use only ~2 lg N compares in the worst case.

*/

public class BitonicSearch {

    public static boolean find(int[] a, int target) {

        return false;
    }

    // helper: fing the index of max int, which is bitonic point, in a bitonic
    // sequence
    private static int findBitonicPoint(int[] a) {
        int result = -1;
        int N = a.length;
        int low = 0;
        int high = N - 1;

        while (low < high - 1) {
            int mid = (low + high) / 2;
            if ((a[mid] > a[mid - 1]) && (a[mid] > a[mid + 1])) {
                return mid;
            } else {
                if (a[mid] < a[mid - 1]) {
                    high = mid;
                } else {
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {

    }
}