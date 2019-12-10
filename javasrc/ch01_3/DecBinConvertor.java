package javasrc.ch01_3;

import lib.StdOut;
import lib.StdIn;

public class DecBinConvertor{

    public static void Dec2Bin(int a){
        LinkedListStack<Integer> stack = new LinkedListStack<>();

        while(a >0){
            stack.push(a % 2);
            a /= 2;
        }

        for(int i: stack){
            StdOut.print(i);
        }
        StdOut.println();
    }

    public static void main(String[] args){
        Dec2Bin(8);
    }
}