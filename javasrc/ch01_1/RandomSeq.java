package javasrc.ch01_1;

import lib.StdOut;
import lib.StdRandom;

public class RandomSeq {

    public static void main(String[] args) {
        int number = Integer.parseInt(args[0]);
        double a = Double.parseDouble(args[1]);
        double b = Double.parseDouble(args[2]);
        for (int i = 0; i < number; i++) {
            double result = StdRandom.uniform(a, b);
            StdOut.printf("%.2f\n", result);
        }
    }
}