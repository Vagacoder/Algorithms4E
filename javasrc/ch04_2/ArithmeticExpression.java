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

! check Bigjava ch15 ExpressionCalculator.java for expression evaluation

*/

import java.util.ArrayList;
import lib.*;

public class ArithmeticExpression {
    
    private int[] values;

    public ArithmeticExpression(String integerExpression){
        ArrayList<Integer> intValues = new ArrayList<>();
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
                        intValues.add(Integer.parseInt(value));
                        break;
                    }
                }
                if(cur == integerExpression.length()){
                    intValues.add(Integer.parseInt(value));
                }
            }else{

            }
            cur++;
        }

        StdOut.println(intValues);
        this.values = new int[intValues.size()];
        for (int i = 0; i < values.length; i++){
            values[i] = intValues.get(i);
        }
    }


    public static void main(String[] args){
        ArithmeticExpression ae1 = new ArithmeticExpression("3 +4 * 5/ 6-7");
    }
}