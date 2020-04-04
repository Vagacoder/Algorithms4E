package javasrc.ch03_1;

/*
* 3.1.1 Write a client that creates a symbol table mapping letter grades to numerical
scores, as in the table below, then reads from standard input a list of letter grades and
computes and prints the GPA (the average of the numbers corresponding to the grades).
A+  4.33
A   4.00
A-  3.67
B+  3.33
B   3.00
B-  2.67
C+  2.33
C   2.00
C-  1.67
D   1.00
F   0.00

*/

import lib.*;

public class GPA {
    
    public static void main(String[] args){
        SequentialSearchST<String, Float> st = new SequentialSearchST<>();
        st.put("A+", 4.33f);
        st.put("A", 4f);
        st.put("A-", 3.67f);
        st.put("B+", 3.33f);
        st.put("B", 3f);
        st.put("B-", 2.67f);
        st.put("C+", 2.33f);
        st.put("C", 2f);
        st.put("C-", 1.67f);
        st.put("D", 1.0f);
        st.put("F", 0.0f);

        Float gpa = 0f;
        int number = 0;
        while(!StdIn.isEmpty()){
            String grade =StdIn.readString();
            Float gp = st.get(grade);
            gpa += gp;
            number++;
            StdOut.println(number + ". " + grade + ": " + gp);
        }
        StdOut.println("GPA: " + (gpa/number));
    }


}