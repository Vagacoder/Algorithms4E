package javasrc.ch01_3;

import lib.StdIn;
import lib.StdOut;
import edu.princeton.cs.algs4.Stack;

/******************************************************************************
 *  Compilation:  javac Evaluate.java
 *  Execution:    java Evaluate
 *  Dependencies: Stack.java
 *
 *  Evaluates (fully parenthesized) arithmetic expressions using
 *  Dijkstra's two-stack algorithm.
 *
 *  % java Evaluate 
 *  ( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) ) 
 *  101.0 
 *
 *  % java Evaulate
 *  ( ( 1 + sqrt ( 5 ) ) / 2.0 ) 
 *  1.618033988749895
 *
 *
 *  Note: the operators, operands, and parentheses must be
 *  separated by whitespace. Also, each operation must
 *  be enclosed in parentheses. For example, you must write
 *  ( 1 + ( 2 + 3 ) ) instead of ( 1 + 2 + 3 ).
 *  See EvaluateDeluxe.java for a fancier version.
 *
 *
 *  Remarkably, Dijkstra's algorithm computes the same
 *  answer if we put each operator *after* its two operands
 *  instead of *between* them.
 *
 *  % java Evaluate
 *  ( 1 ( ( 2 3 + ) ( 4 5 * ) * ) + ) 
 *  101.0
 *
 *  Moreover, in such expressions, all parentheses are redundant!
 *  Removing them yields an expression known as a postfix expression.
 *  1 2 3 + 4 5 * * + 
 * 
 *
 ******************************************************************************/

public class Evaluate {

    public static void main(String[] args) {
        Stack<String> operators = new Stack<String>();
        Stack<Double> values = new Stack<Double>();

        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            if (input.equals("(")) {
                ;
            } else if (input.equals("+")) {
                operators.push(input);
            } else if (input.equals("-")) {
                operators.push(input);
            } else if (input.equals("*")) {
                operators.push(input);
            } else if (input.equals("/")) {
                operators.push(input);
            } else if (input.equals("sqrt")) {
                operators.push(input);
            } else if (input.equals(")")) {
                String operator = operators.pop();
                double value = values.pop();
                if(operator.equals("+")){
                    value = values.pop() + value;
                } else if (operator.equals("-")){
                    value = values.pop() - value;
                } else if (operator.equals("*")){
                    value = values.pop() * value;
                } else if (operator.equals("/")){
                    value = values.pop() / value;
                } else if (operator.equals("sqrt")){
                    value = Math.sqrt(value);
                }
                values.push(value);
            } else{
                values.push(Double.parseDouble(input));
            }
        }

        StdOut.println(values.pop());
    }
}