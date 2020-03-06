package javasrc.ch02_4;

/*
* A priority-queue client, P. 311

* How to use:
1) input file
% more tinyBatch.txt
Turing      6/17/1990   644.08
vonNeumann  3/26/2002   4121.85
Dijkstra    8/22/2007   2678.40
vonNeumann  1/11/1999   4409.74
......

2) CLI input
% java TopM 5 < tinyBatch.txt
Thompson    2/27/2000   4747.08
vonNeumann  2/12/1994   4732.35
vonNeumann  1/11/1999   4409.74
Hoare       8/18/1992   4381.21
.......
*/

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Transaction;
import lib.*;

public class TopM {

    public static void main(String[] args) {

        // Print the top M lines in the input stream.
        int M = Integer.parseInt(args[0]);
        MinPQ<Transaction> pq = new MinPQ<Transaction>(M + 1);
        while (StdIn.hasNextLine()) {
            String newLine = StdIn.readLine();
            pq.insert(new Transaction(newLine));

            if (pq.size() > M) {
                pq.delMin();
            }
        }

        Stack<Transaction> stack = new Stack<Transaction>();
        while (!pq.isEmpty()) {
            stack.push(pq.delMin());
        }

        for (Transaction t : stack) {
            StdOut.println(t);
        }
    }
}