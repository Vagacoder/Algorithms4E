package javasrc.ch04_2;

/*
* 4.2.37 Arithmetic expressions. 
* Write a class that evaluates DAGs that represent arithmetic expressions. Use 
* a vertex-indexed array to hold values corresponding to each vertex. Assume that 
* values corresponding to leaves (vertex with outdegree 0) have been established. 
* Describe a family of arithmetic expressions with the property that the size of 
* the expression tree is exponentially larger than the size of the corresponding 
* DAG (so the running time of your program for the DAG is proportional to the 
* logarithm of the running time for the tree).
* 

! Have met some difficulty to implement DAG. Has no idea how DAG should look like.
! the code below are based on Bigjava ch15 ExpressionCalculator.java for expression evaluation

! Hoorah!, done with a simple version (numbers are 1 digit integer) on ArithmeticExpression2.java
! need add a nested loop to detect multi-digit integer.

*/

import javasrc.ch01_3.LinkedListStack;
import javasrc.ch03_4.SeparateChainingHashST;
import lib.*;

public class ArithmeticExpression {
    
    
    private Digraph expression;
    private String[] ops;
    private SeparateChainingHashST<String, Integer> table;
    
    public ArithmeticExpression(String integerExpression){

        // * parse expression string
        LinkedListStack<Integer> operands = new LinkedListStack<>();
        LinkedListStack<Character> operators = new LinkedListStack<>();
        int cur = 0;

        while(cur < integerExpression.length()){
            char c = integerExpression.charAt(cur);
            
            if(Character.isDigit(c)){
                String value = "";
                value += c;
                cur++;

                while(cur < integerExpression.length()){
                    c = integerExpression.charAt(cur);
                    if(Character.isDigit(c)){
                        value += c;
                        cur++;
                    }else{
                        operands.push(Integer.parseInt(value));
                        break;
                    }
                }

                if(cur == integerExpression.length()){
                    operands.push(Integer.parseInt(value));
                }

                continue;
            } else if(isOperator(c)){
                operators.push(c);
            } else if(c == '('){
                operators.push(c);
            } else if( c== ')'){
                operators.push(c);
            } else if(Character.isWhitespace(c)){
                // do nothing
            } else{
                throw new IllegalArgumentException("Not a number, operator or parenthesis");
            }

            cur++;
        }
        
        // * for debug =================
        while (!operands.isEmpty()){
            StdOut.println(operands.pop());
        }

        while (!operators.isEmpty()){
            StdOut.println(operators.pop());
        }
        // * ===========================

        // * construct symbol digraph
        // ! here is some difficulty to implement

        int v = operands.size() + operators.size();
        this.expression = new Digraph(v);
        this.ops = new String[v];
        this.table = new SeparateChainingHashST<>(v);
        int index = 0;

        while(!operands.isEmpty()){
            int op2 = operands.pop();
            if(operands.isEmpty()){
                throw new Error("Wrong expression, can not get operand 1");
            }
            int op1 = operands.pop();
            char operator = operators.pop();
            
        }


    }

    private boolean isOperator(char c){
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }


    public static void main(String[] args){
        // ArithmeticExpression ae1 = new ArithmeticExpression("3 +4 * 5/ 6-7");
        ArithmeticExpression ae1 = new ArithmeticExpression("3 +4 * 5/ 6-7 % 85 +94 *103/ 112-120");
    }
}