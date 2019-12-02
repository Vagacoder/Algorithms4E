package javasrc.ch01_2;

import lib.StdOut;

public class ex1_2_7{

    public static void main(String[] args){
        StdOut.println("Input is: I love YOU!; Output is: " + reverseString("I love YOU!"));
    }

    public static String reverseString(String s){
        int N = s.length();
        if(N <=1){
            return s;
        }
        String a = s.substring(0, N/2);
        String b = s.substring(N/2, N);
        return reverseString(b) + reverseString(a);
    }
}