package javasrc.ch01_2;

import lib.StdOut;
import javasrc.ch01_2.Accumulator;

public class AccumulatorTester{

    public static void main(String[] args){
        Accumulator ac = new Accumulator();
        for(int i= 1; i<=10; i++){
            ac.addDataValue(i);
        }
        StdOut.printf("1 to 10, mean1 is: %.10f\n", ac.mean());
        StdOut.printf("1 to 10, mean2 is: %.10f\n", ac.mean2());
        StdOut.printf("1 to 10, var is: %.10f\n", ac.var());
        StdOut.printf("1 to 10, stddev is: %.10f\n", ac.stddev());

    }
}