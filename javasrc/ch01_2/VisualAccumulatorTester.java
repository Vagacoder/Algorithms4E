package javasrc.ch01_2;

import edu.princeton.cs.algs4.StdOut;
import lib.StdRandom;

public class VisualAccumulatorTester {

    public static void main(String[] args) {
        // int input = Integer.parseInt(args[0]);
        int input = 2000;
        VisualAccumulator va = new VisualAccumulator(input, 1);
        for (int i = 0; i < input; i++) {
            va.addDataValue(StdRandom.uniform());
        }
        StdOut.println(va);
    }
}