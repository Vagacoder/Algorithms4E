package javasrc.ch01_3;

import lib.StdOut;
import lib.StdIn;

public class ex1_3_12 {

    public static LinkedListStack<String> copy(LinkedListStack<String> s){

        LinkedListStack<String> stack1 = new LinkedListStack<>();
        LinkedListStack<String> stack2 = new LinkedListStack<>();

        for(String str : s){
            stack1.push(str);
        }

        for(String str: stack1){
            stack2.push(str);
        }

        return stack2;
    }

    public static void main(String[] args){
        LinkedListStack<String> stack = new LinkedListStack<>();
        stack.push("First");
        stack.push("Second");
        stack.push("Third");
        stack.push("Forth");
        LinkedListStack<String> newStack = copy(stack);
        while(!newStack.isEmpty()){
            StdOut.println(newStack.pop());
        }
    }
}
