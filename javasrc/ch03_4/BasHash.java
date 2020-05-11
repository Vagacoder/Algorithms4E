package javasrc.ch03_4;

/*
* 3.4.33 Bad hash function. 
Consider the following hashCode() implementation for String, which was used in 
early versions of Java:

Explain why you think the designers chose this implementation and then why you
think it was abandoned in favor of the one in the previous exercise.

! Some char are skipped
*/

import lib.*;

public class BasHash {
    
    public static long hashCode(String s){
        long hash = 0;
        int skip = Math.max(1, s.length() / 8);

        for(int i = 0; i < s.length(); i += skip){
            hash = (hash*37) + s.charAt(i);
        }

        return hash;
    }


    public static void main(String[] args){
        String a = "0123456789ABCDEF";
        String b = "0020406080A0C0E0";
        StdOut.println(a);
        StdOut.println(b);
        StdOut.println(hashCode(a));
        StdOut.println(hashCode(b));
    }
}