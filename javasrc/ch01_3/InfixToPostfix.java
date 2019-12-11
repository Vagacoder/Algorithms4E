package javasrc.ch01_3;

/*
Exercise 1.3.10 Write a filter InfixToPostfix that converts an arithmetic 
expression from infix to postfix.
*/

import lib.StdOut;
import lib.StdIn;

public class InfixToPostfix {

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
                values.push(operand1+ " " + operand2+ " " + operator + " ");
            }else if (token.equals("(")){

            } else {
                values.push(token);
            }
        }

        StdOut.println(values.pop());
    }
}