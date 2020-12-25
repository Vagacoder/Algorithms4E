package javasrc.ch05_3;

/*
 * Ex 5.3.27 Tandem repeat search. A tandem repeat of a base string t in a string 
 * s is a substring of s having at least two consecutive copies t (nonoverlapping). 
 * Develop and implement a linear-time algorithm that, given two strings s and t, 
 * returns the index of the beginning of the longest tandem repeat of t in s. For 
 * example, your program should return 3 when t is abcab and s is abcabcababcababcababcab.
 * 
 */


import lib.*;

public class TandemRepeatSearch {
    
    public static int tandemRepeatSearch(String pattern, String txt){
        String newP = pattern + pattern;
        return BruteForce.search(newP, txt);
    }


    public static void main(String[] args){
        String pattern = "abcab";
        String txt = "abcabcababcababcababcab";

        StdOut.println(tandemRepeatSearch(pattern, txt));
        
    }
}
