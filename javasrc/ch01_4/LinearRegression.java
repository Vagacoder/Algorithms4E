package javasrc.ch01_4;
/******************************************************************************
 *  Compilation:  javac LinearRegression.java
 *  Execution:    java  LinearRegression
 *  Dependencies: none
 *  
 *  Compute least squares solution to y = beta * x + alpha.
 *  Simple linear regression.
 *
 ******************************************************************************/
import lib.StdOut;

public class LinearRegression{

    private final double intercept, slope;
    private final double r2;
    private final double svar0, svar1;

    public LinearRegression(double[] x, double[] y){
        if(x.length != y.length){
            throw new IllegalArgumentException("array lengths are not equal");
        }
        int n = x.length;

        // first pass
        double sumx = 0.0, sumy = 0.0, sumx2= 0.0;
        for (int i =0; i< n; i++){
            sumx += x[i];
            sumx2 += x[i] * x[i];
            sumy += y[i];
        }
        double xbar = sumx/n;
        double ybar = sumy/n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i< n; i++){
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        slope = xybar/xxbar;
        this.intercept = ybar - this.slope * xbar;

        // more statistical analysis
        double rss = 0.0;
        double ssr = 0.0;
        for (int i = 0; i < n ; i++){
            double fit = this.slope*x[i] + this.intercept;
            rss += (fit - y[i])*(fit - y[i]);
            ssr += (fit - ybar)*(fit - ybar);
        }

        int degreesOfFreedom = n - 2;
        r2 = ssr/yybar;
        double svar = rss/ degreesOfFreedom;
        this.svar1 = svar / xxbar;
        this.svar0 = svar/n + xbar * xbar * this.svar1;
    }

    public double intercept(){
        return this.intercept;
    }

    public double slope(){
        return this.slope;
    }

    public double R2(){
        return this.r2;
    }

    public double interceptStdErr(){
        return Math.sqrt(this.svar0);
    }

    public double slopeStdErr(){
        return Math.sqrt(this.svar1);
    }

    public double predict(double x){
        return this.slope*x + this.intercept;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(String.format("%.2f n + %.2f", slope(), intercept()));
        s.append("  (R^2 = " + String.format("%.3f", R2()) + ")");
        return s.toString();
    }

    public static void main(String[] args){
        double[] x = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        double[] y = {-0.1, 1.2, 1.9, 2.8, 4.1, 5.2, 6.1, 6.9, 7.7, 9.0};
        LinearRegression lr = new LinearRegression(x, y);
        StdOut.println("slope: " + lr.slope());
        StdOut.println("intercept: " + lr.intercept());
        StdOut.println(lr.toString());
    }
}