package javasrc.ch01_1;

import lib.StdOut;
import lib.StdIn;

public class Knuth{

    private Knuth(){}

    public static void shuffle(Object[] a){
        int n = a.length;
        for(int i=0; i< n; i++){
            int r = (int) (Math.random() * (i + 1));
            Object temp = a[r];
            a[r] = a[i];
            a[i] =temp;
        }
    }

    public static void shuffleAlternate(Object[] a){
        int n = a.length;
        for(int i=0; i < n; i++){
            int r = i + (int) (Math.random() * (n-i));
            Object temp = a[r];
            a[r] = a[i];
            a[i] =temp;
        }
    }

    public static void main(String[] args){
        String[] a = StdIn.readAllStrings();
        Knuth.shuffle(a);
        for(String s: a){
            StdOut.println(s);
        }
    }

}