package javasrc.ch01_2;

public class Accumulator {
    private double total;
    private int N;
    private double mean;
    private double s;

    public void addDataValue(double val) {
        N++;
        total += val;
        s = s+1.0*(N-1)/N*(val-mean)*(val-mean);
        mean = mean + (val-mean)/N;
    }

    public double mean() {
        return total / N;
    }

    public double mean2(){
        return mean;
    }

    public double var(){
        return s/(N-1);
    }

    public double stddev(){
        return Math.sqrt(this.var());
    }

    public String toString() {
        return "Mean (" + N + "values): " + String.format("%7.5f", mean());
    }
}