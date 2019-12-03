package javasrc.ch01_2;

/*
Exercise 1.2.16 and 1.2.17
Rational numbers. Implement an immutable data type Rational for rational
numbers that supports addition, subtraction, multiplication, and division.

Robust implementation of rational numbers. Use assertions to develop an 
implementation of Rational (see Exercise 1.2.16) that is immune to overflow.

I used Exception
*/
import javasrc.ch01_1.Mathmethods;

public class Rational{

    private long numerator;
    private long denominator;

    public Rational(int numerator, int denominator) throws Exception {
        if(denominator == 0){
            throw new Exception("Denominator cannot be zero");
        }
        int gcd = Mathmethods.gcd(numerator,denominator);
        if (gcd != 1){
            numerator /= gcd;
            denominator /= gcd;
        }

        if (numerator > 0 && denominator < 0){
            numerator = -numerator;
            denominator = -denominator;
        }
        
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public String toString(){
        return numerator+"/"+denominator;
    }

    public Rational plus(Rational b) throws Exception {
        long newDeno = this.denominator * b.denominator;
        long newNumer = this.numerator* b.denominator + b.numerator*this.denominator;

        if (newDeno > Integer.MAX_VALUE || newNumer > Integer.MAX_VALUE ||
        newDeno < Integer.MIN_VALUE || newNumer<Integer.MIN_VALUE){
            throw new Exception("Result overflow");
        }
        return new Rational((int)newNumer, (int)newDeno);
    }

    public Rational minus(Rational b) throws Exception {
        long newDeno = this.denominator * b.denominator;
        long newNumer = this.numerator* b.denominator - b.numerator*this.denominator;

        if (newDeno > Integer.MAX_VALUE || newNumer > Integer.MAX_VALUE ||
        newDeno < Integer.MIN_VALUE || newNumer<Integer.MIN_VALUE){
            throw new Exception("Result overflow");
        }
        return new Rational((int)newNumer, (int)newDeno);
    }

    public Rational times(Rational b) throws Exception{
        long newDeno = this.denominator * b.denominator;
        long newNumer = this.numerator * b.numerator;

        if (newDeno > Integer.MAX_VALUE || newNumer > Integer.MAX_VALUE ||
        newDeno < Integer.MIN_VALUE || newNumer<Integer.MIN_VALUE){
            throw new Exception("Result overflow");
        }
        return new Rational((int)newNumer, (int)newDeno);
    }
    
    public Rational dividesBy(Rational b) throws Exception{
        if (b.numerator == 0){
            throw new Exception("Cannot be divided by zero");
        }
        long newDeno = this.denominator * b.numerator;
        long newNumer = this.numerator * b.denominator;

        if (newDeno > Integer.MAX_VALUE || newNumer > Integer.MAX_VALUE ||
        newDeno < Integer.MIN_VALUE || newNumer<Integer.MIN_VALUE){
            throw new Exception("Result overflow");
        }
        return new Rational((int)newNumer, (int)newDeno);
    }

}