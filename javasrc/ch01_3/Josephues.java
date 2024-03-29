package javasrc.ch01_3;

/*
1.3.37 Josephus problem. 
In the Josephus problem from antiquity, N people are in dire straits and agree to 
the following strategy to reduce the population. They arrange themselves in a 
circle (at positions numbered from 0 to N-1) and proceed around the circle,
eliminating every Mth person until only one person is left. Legend has it that 
Josephus figured out where to sit to avoid being eliminated. 

Write a Queue client Josephus that takes M and N from the command line and prints
 out the order in which people are eliminated (and thus would show Josephus 
 where to sit in the circle).

% java Josephus 2 7
1 3 5 0 4 2 6

*/

import lib.StdOut;

public class Josephues{

    public static void main(String[] args){
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        LinkedListQueue<Integer> q = new LinkedListQueue<>();
        
        for (int i=0; i< n; i++){
            q.enqueue(i);
        }

        while(q.size()>0){
            for(int i = 0; i< m-1; i++){
                q.enqueue(q.dequeue());
            }
            StdOut.print(q.dequeue() + " ");
        }
        StdOut.println();
    }
}