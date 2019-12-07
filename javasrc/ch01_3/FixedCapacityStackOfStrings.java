package javasrc.ch01_3;

import lib.StdOut;
import lib.StdIn;

public class FixedCapacityStackOfStrings{

    private String[] a;
    private int N;

    public FixedCapacityStackOfStrings(int capacity){
        a = new String[capacity];
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public int size(){
        return N;
    }

    public void push(String item){
        a[N++] = item;
    }

    public String pop(){
        return a[--N];
    }

    public static void main(String[] args){
        FixedCapacityStackOfStrings s = new FixedCapacityStackOfStrings(100);
        while(!StdIn.isEmpty()){
            String input = StdIn.readString();
            if(!input.equals("-")){
                s.push(input);
            } else if (!s.isEmpty()){
                StdOut.print(s.pop() + " ");
            } else {
                StdOut.print("(Stack is empty)");
            }
        }

        StdOut.println("(" + s.size() + " left in stack)");
    }
}