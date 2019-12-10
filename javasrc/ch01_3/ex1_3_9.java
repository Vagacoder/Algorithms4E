package javasrc.ch01_3;

import lib.StdIn;
import lib.StdOut;

public class ex1_3_9{

    public static void main(String[] args){

        LinkedListStack<String> operators = new LinkedListStack<>();
        LinkedListStack<String> values = new LinkedListStack<>();

        while(!StdIn.isEmpty()){

            String token = StdIn.readString();
            if(token.equals("+") || token.equals("-")||token.equals("*")||token.equals("/")){
                operators.push(token);
            } else if (token.equals(")")){
                String operand2 = values.pop();
                String operator = operators.pop();
                String operand1 = values.pop();
                // StdOut.print("(" + operand1 + operator + operand2 + ")");
                values.push("(" + operand1 + operator + operand2 + ")");
            }else {
                values.push(token);
            }
        }
        StdOut.println(values.pop());
    }



}