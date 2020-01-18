package javasrc.ch02_1;

import lib.StdRandom;

/*
2.1.12 Instrument shellsort to print the number of compares divided by the array size
for each increment. Write a test client that tests the hypothesis that this number is a
small constant, by sorting arrays of random Double values, using array sizes that are
increasing powers of 10, starting at 100.

client usage
*/

public class ex2_1_12{
    public static void main(String[] args){
        int size = Integer.parseInt(args[0]);
        Double[] a = new Double[size];
        for(int i = 0; i< size; i++) {
            a[i] = StdRandom.uniform();
        }
        Shell.sort(a);
    }
}