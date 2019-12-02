package javasrc.ch01_1;

import lib.StdOut;

public class ex1_1_7{

    public static void main(String[] args){

        // a. find the square root
        double t = 9.0;
        while(Math.abs(t-9.0/t) > 0.001){
            t=(9.0/t + t) /2.0;
        }
        StdOut.printf("A. result is: %.5f\n", t);
    }
}