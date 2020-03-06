package javasrc.ch02_4;

/* 
* A priority queue client, reverse of TopM
*/

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Transaction;
import lib.*;

public class BottomM {

    public static void main(String[] args){

        int M = Integer.parseInt(args[0]);
        MaxPQ<Transaction> pq = new MaxPQ<>(M + 1);

        while(StdIn.hasNextLine()){
            String newLine = StdIn.readLine();
            try{
                pq.insert(new Transaction(newLine));
            } catch(Exception e){
                e.printStackTrace();
            }

            if(pq.size() > M){
                pq.delMax();
            }
        }

        Stack<Transaction> stack = new Stack<>();
        while(!pq.isEmpty()){
            stack.push(pq.delMax());
        }

        for (Transaction t: stack){
            StdOut.println(t);
        }
    }
}