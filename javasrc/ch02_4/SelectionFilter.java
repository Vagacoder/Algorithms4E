package javasrc.ch02_4;

/*
* 2.4.28 Selection filter. 
Write a program similar to TopM that reads points (x, y, z) from standard input, 
takes a value M from the command line, and prints the M points that are closest 
to the origin in Euclidean distance. Estimate the running time of your client
for N = 108 and M = 104.


*/

import edu.princeton.cs.algs4.Stack;
import lib.*;

class Data implements Comparable<Data>{

    private int x;
    private int y;
    private int z;

    public Data(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString(){
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    @Override
    public int compareTo(Data that) {
        return (this.x*this.x + this.y*this.y + this.z*this.z) - 
        (that.x*that.x + that.y*that.y + that.z*that.z);
    }

}

public class SelectionFilter{


    public static void main(String[] args) throws NumberFormatException, Exception {

        int M = Integer.parseInt(args[0]);
        MaxPQ<Data> pq = new MaxPQ<>(M + 1);
        while(StdIn.hasNextLine()){
            String newLine = StdIn.readLine();
            String[] ints = newLine.split(",");
            pq.insert(new Data(Integer.parseInt(ints[0]), Integer.parseInt(ints[1]), 
            Integer.parseInt(ints[2])));

            if(pq.size() > M){
                pq.delMax();
            }
        }

        Stack<Data> stack = new Stack<>();
        while(!pq.isEmpty()){
            stack.push(pq.delMax());
        }
        
        for(Data d : stack){
            StdOut.println(d.toString());
        }
    }
}