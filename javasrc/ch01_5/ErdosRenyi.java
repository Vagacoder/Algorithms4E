package javasrc.ch01_5;

import edu.princeton.cs.algs4.StdRandom;

/* 
1.5.17 Random connections. 
Develop a UF client ErdosRenyi that takes an integer value N from the command line, 
generates random pairs of integers between 0 and N-1, calling connected() to 
determine if they are connected and then union() if not (as in our development 
client), looping until all sites are connected, and printing the number of 
connections generated. 

Package your program as a static method count() that takes N as argument and 
returns the number of connections and a main() that takes N from the command 
line, calls count(), and prints the returned value.

*/

import lib.StdIn;
import lib.StdOut;

public class ErdosRenyi {

    private static int count(int n) {
        int result = 0;
        WeightedQuickUnionUF wq = new WeightedQuickUnionUF(n);
        while (wq.count() > 1) {
            int p = StdRandom.uniform(0, n);
            int q = StdRandom.uniform(0, n);
            while (p == q){
                q = StdRandom.uniform(0, n);
            }
            wq.union(p, q);
            result++;
            StdOut.println("#"+ result + ". p: " + p + "; q: " + q + " components: " + wq.count());
        }
        return result;
    }

    public static void main(String[] args) {

        StdOut.print("Please enter number of sites: ");
        int n = StdIn.readInt();
        while (n <= 0) {
            StdOut.print("Please enter integer > 0 : ");
            n = StdIn.readInt();
        }
        StdOut.println(count(n) + " pairs of connections generated");
    }

}