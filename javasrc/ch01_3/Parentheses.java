package javasrc.ch01_3;

import lib.StdIn;
import lib.StdOut;

public class Parentheses{
    
    public static void main(String[] args){
        LinkedListStack<String> stack = new LinkedListStack<>();
        while (!StdIn.isEmpty()){
            String token = StdIn.readString();
            if(token.equals("[") || token.equals("(") || token.equals("{")){
                stack.push(token);
            } else if(token.equals("]") || token.equals(")") || token.equals("}")){
                if(stack.isEmpty()){
                    StdOut.println("false");
                    return;
                } else {
                    String pop = stack.pop();
                    if(!(pop + token).equals("()") && !(pop + token).equals("[]") && !(pop + token).equals("{}")){
                        StdOut.println("false");
                        return;
                    }
                }
            }
        }
        if(stack.size() == 0){
            StdOut.println("true");
        } else{
            StdOut.println("false");
        }
    }

}