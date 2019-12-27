package javasrc.ch01_4;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;

/*
 1.4.8. Write a program to determine the number pairs of values in an input
 file that are equal. If your first try is quadratic, think again and use
 Arrays.sort() to develop a linearithmic solution.
 
 */

import lib.StdOut;

public class ex1_4_9 {

    public static void FindEqualPairs1(int[] ints) {
        int count = 0;
        int length = ints.length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                // int a = (ints[i]);
                // int b = (ints[j]);
                if (ints[i] == ints[j]) {
                    StdOut.println(ints[i] + " " + ints[j]);
                    count++;
                }
            }
        }

        StdOut.println("Total pairs are: " + count);
    }

    public static void FindEqualPairs2(int[] ints) {
        Arrays.sort(ints);
        int length = ints.length;
        int count = 0;
        for (int i = 0; i < length - 1; i++) {
            int j = i + 1;
            while (ints[i] == ints[j]) {
                count++;
                j++;
            }
        }
        StdOut.println("Total pairs are: " + count);
    }

    public static void main(String[] args) {
        In in = new In("./data/32KintsWithPairs.txt");
        int[] input = in.readAllInts();
        FindEqualPairs2(input);
    }
}
