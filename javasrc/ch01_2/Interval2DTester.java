package javasrc.ch01_2;

import lib.Point2D;
import lib.StdOut;
import lib.StdRandom;
import lib.Interval1D;
import lib.Interval2D;

public class Interval2DTester {

    public static void main(String[] args) {

        double xlow = Double.parseDouble(args[0]);
        double xhigh = Double.parseDouble(args[1]);
        double ylow = Double.parseDouble(args[2]);
        double yhigh = Double.parseDouble(args[3]);
        int Number = Integer.parseInt(args[4]);

        Interval1D xint = new Interval1D(xlow, xhigh);
        Interval1D yint = new Interval1D(ylow, yhigh);
        Interval2D box = new Interval2D(xint, yint);
        box.draw();

        Counter c = new Counter("hits");
        for (int i = 0; i < Number; i++) {
            double x = StdRandom.uniform();
            double y = StdRandom.uniform();
            Point2D p = new Point2D(x, y);
            if (box.contains(p)) {
                c.increment();
            } else {
                p.draw();
            }
        }

        StdOut.println(c);
        StdOut.printf("area = %.2f\n", box.area());
    }
}