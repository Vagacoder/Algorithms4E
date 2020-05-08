package javasrc.ch03_4;

import lib.StdOut;

/*
* 3.4.4 Find Perfect Hash Function. Write a program to find values of a and M, 
with M as small as possible, such that the hash function (a * k) % M for transforming 
the kth letter of the alphabet into a table index produces distinct values 
(no collisions) for the keys S E A R C H X M P L. 

The result is known as a perfect hash function.

*/

public class ex3_4_4 {

    public static void main(String[] args) {

        // * a = 1 is good for every M >= 26
        for (int a = 2; a < 1000; a++) {
            // * M < 26, must have collision for 26 letters
            for (int M = 26; M < 1000; M++) {

                // * setup a temperary hash table, its full of values of 0, which is
                // * count of that hash (= i)
                int[] temp = new int[M];
                for (int i = 0; i < M; i++) {
                    temp[i] = 0;
                }

                // * calculate hash for 1-26 (alphabet orders), and update count
                // * of that hash
                for (int k = 1; k <= 26; k++) {
                    int hash = (a * k) % M;
                    temp[hash]++;
                }

                // * check if count > 1, which means collision
                boolean isPerfect = true;
                for (int k = 0; k < M; k++) {
                    if (temp[k] > 1) {
                        isPerfect = false;
                        break;
                    }
                }

                // * if no collisiont, print a and M, and only for smallest M
                if (isPerfect) {
                    StdOut.println("a: " + a + " M: " + M);
                    break;
                }
            }
        }
    }

}