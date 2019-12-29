package javasrc.ch01_4;

/*
1.4.17 Farthest pair (in one dimension). 
Write a program that, given an array a[] of N double values, finds a farthest pair:
 two values whose difference is no smaller than the the difference of any other
  pair (in absolute value). 

The running time of your program should be linear in the worst case.
*/

import lib.StdOut;

public class FarthestPair{

    public static double[] findFarthestPair(double[] a){
        if (a.length < 2){
            return null;
        }
        double[] result = {a[0], a[0]};
        double min = a[0];
        double max = a[0];

        for(double number: a){
            if (number>max){
                max = number;
            }
            if(number < min){
                min = number;
            }
        }
        result[0] = min;
        result[1] = max;
        return result;
    }

    public static void main(String[] args){
        double[] a = {  1.003, -8, 0, 9, -6, 10, -5.5, -10.0, 1, -4, -2, 5};
        double[] result = findFarthestPair(a);
        StdOut.println(result[0] + " " + result[1]);
    }
}