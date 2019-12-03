package javasrc.ch01_2;

import lib.StdOut;
import javasrc.ch01_2.Rational;

public class ex1_2_16{

    public static void main(String[] args) throws Exception {
        Rational a = new Rational(1, 2);
        Rational b = new Rational(3, 4);
        Rational c = new Rational(2, 7);
        Rational d = new Rational(-2, 10);
        Rational e = new Rational(3, -12);
        Rational f = new Rational(-3, -21);
        StdOut.println(a);
        StdOut.println(b);
        StdOut.println(c);
        StdOut.println(d);
        StdOut.println(e);
        StdOut.println(f);
        StdOut.println(a.plus(b));
        StdOut.println(a.minus(b));
        StdOut.println(a.times(e));
        StdOut.println(a.dividesBy(f));
        
    }
}