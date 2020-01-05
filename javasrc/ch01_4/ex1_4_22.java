package javasrc.ch01_4;

/*
1.4.22 Binary search with only addition and subtraction. [Mihai Patrascu] 

Write a program that, given an array of N distinct int values in ascending order, 
determines whether a given integer is in the array. 

Note: You may use only additions and subtractions and a constant amount of extra 
memory. (Regular binary search does use division: mid = (low + high) / 2 )

Requirement: The running time of your program should be proportional to log N in 
the worst case.

*/

import lib.In;
import lib.StdIn;
import lib.StdOut;
import java.util.Arrays;

public class ex1_4_22 {

    public static int find(int key, int[] a) {
        Arrays.sort(a);
        int N = a.length;
        int low = 0;
        int high = N - 1;
        while (low <= high) {
            if (low == high) {
                if (a[low] == key) {
                    return low;
                } else {
                    return -1;
                }
            }
            int mid = low + findMaxFibonacciN(high - low);
            if (a[mid] == key) {
                return mid;
            } else if (a[mid] > key) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }

    // helper
    private static int fibonacci(int n) {
        if (n < 2) {
            return 1;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    // helper, find the max N, which satisfies: fibonacci(N) <= n (argument).
    private static int findMaxFibonacciN(int n) {
        if (n == 1) {
            return 1;
        }
        int result = 1;
        while (fibonacci(result) <= n) {
            result++;
        }
        return result - 1;
    }

    public static void main(String[] args) {
        // 1. Manual Test
        // int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        // int[] b = { 84, 48, 68, 10, 18, 98, 12, 23, 54, 57, 33, 16, 77, 11, 29 };
        // StdOut.println(findMaxFibonacciN(3));
        // StdOut.println(find(7, a));
        // StdOut.println(find(10, a));
        // StdOut.println(find(0, a));
        // StdOut.println(find(23, b));

        // 2. Auto test
        int[] whitelist = new In(args[0]).readAllInts();

        // Arrays.sort(whitelist);

        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            if (find(key, whitelist) == -1) {
                StdOut.println(key);
            }
        }
    }
}