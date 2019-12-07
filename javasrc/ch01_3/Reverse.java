package javasrc.ch01_3;

import java.util.Stack;

import lib.StdIn;
import lib.StdOut;

public class Reverse{

    public static void main(String[] args){
        Stack<Integer> stack = new Stack<Integer>();
        StdOut.println("Please enter integers, press Ctrl-d to end");
        while(!StdIn.isEmpty()){
            stack.push(StdIn.readInt());
        }

        StdOut.println("Iterating stack gives the same order of input: ");
        for(int i: stack){
            StdOut.println(i);
        }

        StdOut.println("Popping from stack gives reversed order of input:");
        while(stack.size() > 0){
            StdOut.println(stack.pop());
        }
    }
}