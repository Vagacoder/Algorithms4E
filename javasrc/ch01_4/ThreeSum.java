package javasrc.ch01_4;

/******************************************************************************
 *  Compilation:  javac ThreeSum.java
 *  Execution:    java ThreeSum input.txt
 *  Dependencies: In.java StdOut.java Stopwatch.java
 *  Data files:   https://algs4.cs.princeton.edu/14analysis/1Kints.txt
 *                https://algs4.cs.princeton.edu/14analysis/2Kints.txt
 *                https://algs4.cs.princeton.edu/14analysis/4Kints.txt
 *                https://algs4.cs.princeton.edu/14analysis/8Kints.txt
 *                https://algs4.cs.princeton.edu/14analysis/16Kints.txt
 *                https://algs4.cs.princeton.edu/14analysis/32Kints.txt
 *                https://algs4.cs.princeton.edu/14analysis/1Mints.txt
 *
 *  A program with cubic running time. Reads n integers
 *  and counts the number of triples that sum to exactly 0
 *  (ignoring integer overflow).
 *
 *  % java ThreeSum 1Kints.txt 
 *  70
 *
 *  % java ThreeSum 2Kints.txt 
 *  528
 *
 *  % java ThreeSum 4Kints.txt 
 *  4039
 *
 ******************************************************************************/

/*
1.4.2 Modify ThreeSum to work properly even when the int values are so large that
adding two of them might cause overflow.

*/

import lib.In;
import lib.StdOut;

public class ThreeSum{

    public static int count(int[] a){
        int N = a.length;
        int cnt = 0;
        for (int i = 0; i< N; i++){
            for (int j =i+1; j<N; j++){
                for(int k=j+1; k<N; k++){
                    if(((long)a[i]) + a[j] + a[k] == 0){
                        // StdOut.println(a[i] + " " + a[j] + " " + a[k]);
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args){
        In in = new In(args[0]);
        int[] a = in.readAllInts();

        Stopwatch timer = new Stopwatch();
        int count = count(a);
        StdOut.println("It takes time of " + timer.elapsedTime());
        StdOut.println(count);
    }


}