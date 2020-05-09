package javasrc.ch03_4;

/*
* 3.4.23 Consider modular hashing for string keys with R = 256 and M = 255. Show
that this is a bad choice because any permutation of letters within a string hashes to the
same value.

? For string hash(): hash = (R * hash + s.charAt(i))% M; 
? if R = M + 1, that means (R * previous hash) % M = previous hash, which results in
? the loop simplely add all char's number together, ignoring the order of char
? at all.
*/

import lib.*;

public class ex3_4_23{

    public static int hashString(String s, int R, int M){
        int hash = 0;
        for(int i = 0; i < s.length(); i ++){
            hash = (R * hash + s.charAt(i))% M;
        }
        return hash;
    }


    public static void main(String[] args){
        int R = 256;
        int M = 255;
        String str1 = "AS";
        String str2 = "SA";
        StdOut.println(hashString(str1, R, M));
        StdOut.println(hashString(str2, R, M));
    }
}