package javasrc.ch01_4;


/*
1.4.16 Closest pair (in one dimension). 

Write a program that, given an array a[] of N double values, finds a closest pair:
two values whose difference is no greater than the the difference of any other 
pair (in absolute value). 

The running time of your program should be linearithmic in the worst case.

*/
import java.util.Arrays;
import lib.StdOut;

public class ClosestPair{

    public static double[] findClosestPair(double[] a){
        if (a.length < 2){
            return null;
        }
        Arrays.sort(a);
        double[] result = {a[0], a[1]};
        double minDiff = Math.abs(a[0] - a[1]);
        for (int i = 1; i < a.length; i++){
            if (Math.abs(a[i]-a[i-1]) <  minDiff){
                minDiff = Math.abs(a[i]-a[i-1]);
                result[0] = a[i-1];
                result[1] = a[i];
            }
        }
        return result;
    }

    public static void main(String[] args){
        double[] a = {  1.003, -8, 0, 9, -6, 10, -5.5, -10.0, 1, -4, -2, 5};
        double[] result = findClosestPair(a);
        StdOut.println(result[0] + " " + result[1]);
    }
}