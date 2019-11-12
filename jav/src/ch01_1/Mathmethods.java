package jav.src.ch01_1;

public class Mathmethods {

  public static double sqrt(double x) {
    if (x < 0) {
      return Double.NaN;
    }
    double err = 1e-15;
    double t = x;
    while (Math.abs(t - x / t) > err * t) {
      t = (x / t + t) / 2.0;
    }
    return t;
  }

  public static int abs(int x) {
    return (x < 0) ? -x : x;
  }

  public static double abs(double x) {
    return (x < 0.0) ? (-x) : (x);
  }

  public static boolean isPrime(int x){
    if (x < 2) {return false;}
    for(int i = 2; i*i<=x ; i++){
      if (x % i == 0){return false;}
    }
    return true;
  }

  public static double hypotenuse(double a, double b){
    return sqrt(a*a + b*b);
  }

  public static double harmonic(int x) {
    double sum = 0.0;
    for(int i= 1; i <=x ; i++){
      sum += 1.0/i;
    }
    return sum;
  }

  public static void main(String[] args) {
    System.out.println(sqrt(9));
  }
}