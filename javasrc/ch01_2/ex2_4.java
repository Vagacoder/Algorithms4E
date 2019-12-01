package javasrc.ch01_2;

import lib.StdOut;

public class ex2_4 {

    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = s1;
        s1 = "world";
        StdOut.println(s1);
        StdOut.println(s2);
    }
}