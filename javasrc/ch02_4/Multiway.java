package javasrc.ch02_4;

/*
* Multiway merge priority-queue client
*/

/******************************************************************************
 *  Compilation:  javac Multiway.java
 *  Execution:    java Multiway input1.txt input2.txt input3.txt ...
 *  Dependencies: IndexMinPQ.java In.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/24pq/m1.txt
 *                https://algs4.cs.princeton.edu/24pq/m2.txt
 *                https://algs4.cs.princeton.edu/24pq/m3.txt
 * 
 *  Merges together the sorted input stream given as command-line arguments
 *  into a single sorted output stream on standard output.
 *
 *  % more m1.txt 
 *  A B C F G I I Z
 *
 *  % more m2.txt 
 *  B D H P Q Q
 * 
 *  % more m3.txt 
 *  A B E F J N
 *
 *  % java Multiway m1.txt m2.txt m3.txt 
 *  A A B B B C D E F F G H I I J N P Q Q Z 
 *
 ******************************************************************************/

import lib.*;

public class Multiway{

    public static void merge(In[] streams){
        int N = streams.length;
        IndexMinPQ<String> pq = new IndexMinPQ<>(N);

        for(int i = 0; i < N; i++){
            if(!streams[i].isEmpty()){
                pq.insert(i, streams[i].readString());
            }
        }

        while(!pq.isEmpty()){
            StdOut.println(pq.minKey());
            int i = pq.delMin();
            if(!streams[i].isEmpty()){
                pq.insert(i, streams[i].readString());
            }
        }
    }

    public static void main(String[] args){
        int N = args.length;
        In[] streams = new In[N];
        for(int i = 0; i < N; i++){
            streams[i] = new In(args[i]);
        }
        merge(streams);
    }
}