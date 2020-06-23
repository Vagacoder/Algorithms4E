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

! This is a simple experiemental version, numbers are from 0-9 (single digit integer).

*/

import javasrc.ch01_3.LinkedListStack;
import lib.*;

public class ArithmeticExpression2 {

    private String[] tokens;
    private Digraph expression;

    public ArithmeticExpression2(String intExpression) {
        intExpression = intExpression.replaceAll("\\s", "");
        tokens = intExpression.split("");
        expression = new Digraph(tokens.length);

        // * Note: integer in these 3 stacks are INDEX of tokens
        LinkedListStack<Integer> operandsI = new LinkedListStack<>();
        LinkedListStack<Integer> operatorsI = new LinkedListStack<>();
        LinkedListStack<Integer> usedOperatorsI = new LinkedListStack<>();

        int i = 0;
        Main:
        while(i < tokens.length) {
            char c = tokens[i].charAt(0);
            // StdOut.println(c);

            // ! for operands
            if (Character.isDigit(c)) {
                operandsI.push(i);
            }
            // !  for operators
            else {
                // * first operator, always push
                if (operatorsI.isEmpty()) {
                    operatorsI.push(i);
                }
                // * if there is/are operator, compare precedence
                else {
                    int oldOperatorI = operatorsI.pop();
                    char oldOperator = tokens[oldOperatorI].charAt(0);

                    // * if this operator > previous operator, push both
                    if (precedence(c) > precedence(oldOperator)) {
                        operatorsI.push(oldOperatorI);
                    }
                    // * if this op <= previous op, evaluate previous op wiht operands
                    else {

                        // * special case no operand in stack, pop 2 used operators
                        if(operandsI.isEmpty()){
                            int usedOperator1I = usedOperatorsI.pop();
                            int usedOperator2I = usedOperatorsI.pop();

                            this.expression.addEdge(oldOperatorI, usedOperator2I);
                            this.expression.addEdge(oldOperatorI, usedOperator1I);
                            usedOperatorsI.push(oldOperatorI);

                            // ! current operator need re-evaluation, skip i++
                            continue;
                        }


                        int operand1I = operandsI.pop();
                        // int operand1 = Integer.parseInt(tokens[operand1I]);
                        
                        // * check # of used operator
                        // * if no used operator, pop 2 operands, 
                        if (usedOperatorsI.isEmpty()) {
                            // * since need 2nd operands, check stack is empty or not
                            if (operandsI.isEmpty()) {
                                throw new Error("Stack of operands is empty, can not evaluate expression");
                            }

                            int operand2I = operandsI.pop();
                            // int operand2 = Integer.parseInt(tokens[operand2I]);

                            this.expression.addEdge(oldOperatorI, operand2I);
                            this.expression.addEdge(oldOperatorI, operand1I);
                            usedOperatorsI.push(oldOperatorI);
                        }

                        // * if has used operator, compare this op with 1st used operator
                        else{
                            int usedOperatorI = usedOperatorsI.peek();
                            char usedOperator = tokens[usedOperatorI].charAt(0);

                            // * compare previous op with 1st used operator
                            // * if previous op > useed op, pop 2 operands
                            if(precedence(oldOperator) > precedence(usedOperator)){
                                // * since need 2nd operands, check stack is empty or not
                                if (operandsI.isEmpty()) {
                                    throw new Error("Stack of operands is empty, can not evaluate expression");
                                }

                                int operand2I = operandsI.pop();
                                this.expression.addEdge(oldOperatorI, operand2I);
                                this.expression.addEdge(oldOperatorI, operand1I);
                                usedOperatorsI.push(oldOperatorI);

                                // ! current operator need re-evaluation, skip i++
                                continue;
                            }
                            // * if previous op <= used op, pop 1 operand, 1 used operator, 
                            else{
                                usedOperatorsI.pop(); // ! discard, already have usedOperator;

                                this.expression.addEdge(oldOperatorI, usedOperatorI);
                                this.expression.addEdge(oldOperatorI, operand1I);
                                usedOperatorsI.push(oldOperatorI);

                                // ! current operator need re-evaluation, skip i++
                                continue;
                            }
                        }

                    }

                    // * push current operator into stack
                    operatorsI.push(i);
                }

            }
            i++;
        }

        // * after loop, clean stacks
        if(!operatorsI.isEmpty()){
            int operatorI = operatorsI.pop();

            // * 2 used operators left
            if(operandsI.isEmpty()){
                int usedOperator1I = usedOperatorsI.pop();
                int usedOperator2I = usedOperatorsI.pop();
                this.expression.addEdge(operatorI, usedOperator2I);
                this.expression.addEdge(operatorI, usedOperator1I);
            }
            // * 2 operands left
            else if( usedOperatorsI.isEmpty()){
                int operand1I = operandsI.pop();
                int operand2I = operandsI.pop();
                this.expression.addEdge(operatorI, operand2I);
                this.expression.addEdge(operatorI, operand1I);
            }
            // * 1 operand, 1 used operator left 
            else{
                int usedOperatorI = usedOperatorsI.pop();
                int operandI = operandsI.pop();
                this.expression.addEdge(operatorI, operandI);
                this.expression.addEdge(operatorI, usedOperatorI);
            }
        }

    }

    private int precedence(char ch) {
        if (ch == '+' || ch == '-') {
            return 1;
        } else if (ch == '*' || ch == '/' || ch == '%') {
            return 2;
        } else if (ch == '^') {
            return 3;
        } else {
            return 0;
        }
    }

    public Digraph getDigraph(){
        return this.expression;
    }

    public String[] getTokens(){
        return this.tokens;
    }

    public static void main(String[] args) {
        ArithmeticExpression2 ae2 = new ArithmeticExpression2("3 +\t4-5* 6\n/ 7 + 8");
        Digraph expression = ae2.getDigraph();
        String[] tokens = ae2.getTokens();

        StdOut.println("Check whether all digits are leaves, (outdegree = 0)");
        for(int i = 0; i < expression.V(); i++ ){
            StdOut.print(tokens[i] + ": ");
            for(int w: expression.adj(i)){
                StdOut.print(tokens[w] + ",");
            }
            StdOut.println();
        }
    }
}