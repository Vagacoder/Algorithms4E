package javasrc.ch01_3;

import lib.StdOut;
import lib.StdIn;

public class EvaluatePostfix{

    public static void main(String[] args){

        LinkedListStack<String> operators = new LinkedListStack<>();
        LinkedListStack<String> values = new LinkedListStack<>();

        while(!StdIn.isEmpty()){
            String token = StdIn.readString();
            if(token.equals("+") || token.equals("-")||token.equals("*")||token.equals("/")){
                String operand2 = values.pop();
                String operand1 = values.pop();
                values.push("( "+ operand1 + " " + token + " " + operand2 +" ) ");
            } else{
                values.push(token);
            }
        }

        StdOut.println(values.pop());

    }
}