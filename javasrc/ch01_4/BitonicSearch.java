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

import lib.StdOut;


public class BitonicSearch {

    public static int find(int[] a, int target) {
        int bpoint = findBitonicPoint(a);

        int indexInAscending = rankInsideAscending(target, a, 0, bpoint);
        if( indexInAscending >= 0 ){
            return indexInAscending;
        } else {
            return rankInsideDescending(target, a, bpoint, a.length-1);
        }
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
                    low = mid;
                }
            }
        }
        return result;
    }
    
    // helper: binary search in ascending sorted part of array
    private static int rankInsideAscending(int target, int[] a, int low, int high){
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (target < a[mid]) {
              high = mid - 1;
            } else if (target > a[mid]) {
              low = mid + 1;
            } else {
              return mid;
            }
          }
          return -1;
    }

    // helper: binary search in descending sorted part of array
    private static int rankInsideDescending(int target, int[] a, int low, int high){
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (target < a[mid]) {
                low = mid + 1;
            } else if (target > a[mid]) {
                high = mid - 1;
            } else {
              return mid;
            }
          }
          return -1;
    }


    public static void main(String[] args) {
        StdOut.println("1. testing helper find bitonic point ...");
        int[] a = {1,3,2};
        StdOut.println("input: {1, 3, 2}, expect: 1, mine is: " + findBitonicPoint(a));
        int[] b = {2,3,1};
        StdOut.println("input: {2, 3, 1}, expect: 1, mine is: " + findBitonicPoint(b));
        int[] c = {1,2,3};
        StdOut.println("input: {1, 2, 3}, expect: -1, mine is: " + findBitonicPoint(c));
        int[] d = {3,2,1};
        StdOut.println("input: {3, 2, 1}, expect: -1, mine is: " + findBitonicPoint(d));
        int[] e = {1,3,5,7,9,10,8,6,4,2,0};
        StdOut.println("input: {1,3,5,7,9,10,8,6,4,2,0}, expect: 5, mine is: " +
         findBitonicPoint(e));
        StdOut.println("1. end of test ...");

        StdOut.println("2. testing helper rank inside ascedning array ...");
        int[] f = {1,3,5,7,9,10,8,6,4,2,0};
        StdOut.println("input: {1,3,5,7,9,10,8,6,4,2,0}, target: 7, low: 1, high: 5 expect: 3, mine is: "
        + rankInsideAscending(7, f, 1, 5));
        StdOut.println("2. end of test ...");

        StdOut.println("3. testing helper rank inside descedning array ...");
        StdOut.println("input: {1,3,5,7,9,10,8,6,4,2,0}, target: 6, low: 5, high: 10 expect: 7, mine is: "
        + rankInsideDescending(6, f, 5, 10));
        StdOut.println("3. end of test ...");

        StdOut.println("4. testing find() in bitonic search ...");
        StdOut.println("input: {1,3,5,7,9,10,8,6,4,2,0}, target: 1, expect: 0, mine is: "
        + find(f, 1));
        StdOut.println("input: {1,3,5,7,9,10,8,6,4,2,0}, target: 0, expect: 10, mine is: "
        + find(f, 0));
        StdOut.println("input: {1,3,5,7,9,10,8,6,4,2,0}, target: -1, expect: -1, mine is: "
        + find(f, -1));
        StdOut.println("input: {1,3,5,7,9,10,8,6,4,2,0}, target: 3, expect: 1, mine is: "
        + find(f, 3));
        StdOut.println("input: {1,3,5,7,9,10,8,6,4,2,0}, target: 10, expect: 5, mine is: "
        + find(f, 10));
        StdOut.println("input: {1,3,5,7,9,10,8,6,4,2,0}, target: 4, expect: 8, mine is: "
        + find(f, 4));
        StdOut.println("4. end of test ...");
    }
}