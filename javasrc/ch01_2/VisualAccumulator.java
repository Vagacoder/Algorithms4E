package javasrc.ch01_2;

import lib.StdDraw;

public class VisualAccumulator {
    private double total;
    private int Number;

    public VisualAccumulator(int trials, double max) {
        StdDraw.setXscale(0, trials);
        StdDraw.setYscale(0, max);
        StdDraw.setPenRadius(0.005);
    }

    public void addDataValue(double val) {
        Number++;
        total += val;
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.point(Number, val);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(Number, mean());
    }

    public double mean() {
        return total / Number;
    }

    public String toString() {
        return "Mean (" + Number + "values): " + String.format("%7.5f", mean());
    }

}