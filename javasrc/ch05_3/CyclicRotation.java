package javasrc.ch05_3;

/*
 * Ex 5.3.26 Cyclic rotation check. Write a program that, given two strings, 
 * determines whether one is a cyclic rotation of the other, such as example and 
 * ampleex.
 */

import lib.*;

public class CyclicRotation {
    
    public static boolean isCyclicRotation(String s1, String s2){
        String newS = s1 + s1;
        return BruteForce.count(s2, newS) != 0 && s1.length() == s2.length();
    }


    public static void main(String[] args){
        String s1 = "example";
        String s2 = "xamplee";
        StdOut.println(isCyclicRotation(s1, s2));

        s1 = "example";
        s2 = "xample";
        StdOut.println(isCyclicRotation(s1, s2));
    }
}
