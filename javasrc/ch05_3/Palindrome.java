package javasrc.ch05_3;


/*
* 5.3.23 Write a program that reads characters one at a time and reports at each 
* instant if the current string is a palindrome. Hint : Use the Rabin-Karp hashing idea.
* 
*  
*/

import java.lang.Math;
import java.math.BigInteger;
import java.util.Random;
import lib.*;

public class Palindrome {
    
    public static boolean isPalindrome(String txt){
        int n = txt.length();
        if (n < 2){
            return true;
        }

        int r = 256;
        long q = longRandomPrime();
        long fHash = txt.charAt(0) % q;
        long rHash = fHash;
        
        for (int i = 1; i < n; i++){
            fHash = (r * fHash + txt.charAt(i)) % q;
            long rpoweri = r;
            for (int j = 1; j < i; j++){
                rpoweri *= r;
            }
            rHash = (rHash + txt.charAt(i) * rpoweri) % q;
        }

        if (fHash == rHash){
            return true;
        }else{
            return false;
        }
    }

    public static long longRandomPrime(){
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
        // return 277;
    }


    public static void main(String[] args){
        String a1 = "tt";
        StdOut.printf("For %s, exp: true, res: %b\n", a1, Palindrome.isPalindrome(a1));

        a1 = "tx";
        StdOut.printf("For %s, exp: false, res: %b\n", a1, Palindrome.isPalindrome(a1));

        a1 = "txt";
        StdOut.printf("For %s, exp: true, res: %b\n", a1, Palindrome.isPalindrome(a1));

        a1 = "ext";
        StdOut.printf("For %s, exp: false, res: %b\n", a1, Palindrome.isPalindrome(a1));

        a1 = "text";
        StdOut.printf("For %s, exp: false, res: %b\n", a1, Palindrome.isPalindrome(a1));

        a1 = "teet";
        StdOut.printf("For %s, exp: true, res: %b\n", a1, Palindrome.isPalindrome(a1));

        a1 = "texet";
        StdOut.printf("For %s, exp: true, res: %b\n", a1, Palindrome.isPalindrome(a1));

        a1 = "txeet";
        StdOut.printf("For %s, exp: false, res: %b\n", a1, Palindrome.isPalindrome(a1));
    }
}
