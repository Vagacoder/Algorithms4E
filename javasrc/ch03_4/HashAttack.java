package javasrc.ch03_4;

/*
* 3.4.32 Hash attack. Find 2N strings, each of length 2N, that have the same hashCode()
value. 

Strong hint : Aa and BB have the same value.

! Solution algorithm: for a string, consider as an array of char {0, 1, ... n};
! at index i, the difference of char[i] between two string must be compensated 
! by char[i + 1]. For example:
! string a: "Aa" and string b: "BB". At index of 0, 'A' is 65, 'B' is 66, difference
! is 65 - 66 = -1; when index moving to 1, the difference amplified: -1 * 31 = -31, 
! therefore char[1] of string a must 31 larger than char[1] of string b, like 'a' 
! is 97, 'B' is 66, difference is 97-66 =31. This compensates the difference at 
! index of 0, -31.
*/

import lib.*;

public class HashAttack {
    
    public static int hashCode(String s){
        int hash = 0;

        for(int i = 0; i < s.length(); i++){
            
            hash = (hash * 31) + s.charAt(i);

            // StdOut.println(s.charAt(i) + ": " + (int)s.charAt(i));
            // StdOut.println(hash);
        }

        return hash;
    }

    public static String hashAttack(String s1){
        int n = s1.length();
        if(n <= 1){
            return s1;
        }

        String result = "";

        for(int i = 0; i < n; i+=2){
            char char1 = s1.charAt(i);
            if(i+1 < n){
                result += (char)((int) char1 + 1);
                char char2 = s1.charAt(i+1);
                result += (char)((int) char2 - 31);
            }else{
                result += char1;
            }
        }

        return result;
    }

    public static void main(String[] args){
        StdOut.println("Testing hint ...");
        String a = "Aa";
        String b = "BB";
        StdOut.println(hashCode(a));
        StdOut.println(hashCode(b));
        StdOut.println();

        String c = "I l0ve CompUter ScIenec3!";
        String d = hashAttack(c);
        StdOut.println(c);
        StdOut.println(c.length());
        StdOut.println(d);
        StdOut.println(d.length());         // ? d gets some char not for printing
        StdOut.println(hashCode(c));
        StdOut.println(hashCode(d));

    }

}