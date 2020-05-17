package javasrc.ch03_5;

import java.util.Hashtable;

import lib.*;
/*
* 3.5.15 Write a program that takes a string on standard input and an integer k as com-
mand-line argument and puts on standard output a sorted list of the k-grams (sub-
strings of length k) found in the string, each followed by its index in the string.

*/

public class ex3_5_15 {
  
    public static void main(String[] args){
        // String str = args[0];
        // int k = Integer.parseInt(args[1]);

        String str = "abcdefghijk";
        int k = 3;
        int length = str.length();

        if(k >= length){
            StdOut.println("k is out of string length");
            return;
        }

        Hashtable<Integer, String> table = new Hashtable<>();

        for(int i =0; i <= length - k;i++){
            table.put(i, str.substring(i, i+k));
        }
        
        for(int i: table.keySet()){
            StdOut.println(table.get(i) + " at " + i);
        }

    }

}