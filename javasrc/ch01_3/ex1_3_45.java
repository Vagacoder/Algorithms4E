package javasrc.ch01_3;

/*
1.3.45 Stack generability. 
Suppose that we have a sequence of intermixed push and pop operations as with our 
test stack client, where the integers 0, 1, ..., N-1 in that order (push directives) 
are intermixed with N minus signs (pop directives). 

#1. Devise an algorithm that 
determines whether the intermixed sequence causes the stack to under-flow. (You 
may use only an amount of space independent of N—you cannot store the
integers in a data structure.) 

#2. Devise a linear-time algorithm that determines whether a given permutation can be 
generated as output by our test client (depending on where the pop directives occur).

*/

import lib.StdOut;

public class ex1_3_45{


    public static void main(String[] args){
        LinkedListStack<Integer> st = new LinkedListStack<>();
        String[] input = {"0", "-", "1", "2", "3", "-","-","-","-"};
        int N = input.length;

        int pushNumber = 0;
        int popNumber = 0;
        for (int i = 0; i< N; i++){
            String token = input[i];

            // solution 1, determine stack underflow by testing return of pop()
            // if(token != "-"){
            //     st.push(Integer.parseInt(token));
            // }else{
            //     Integer popped = st.pop();
            //     if (popped != null){
            //         StdOut.println(popped);
            //     } else{
            //         StdOut.println("Stack underflow!");
            //     }
            // }

            // solution 2. by size()
            // skip implementation

            // solution 3. test outside variable

            if(token != "-"){
                st.push(Integer.parseInt(token));
                pushNumber++;
            } else {
                popNumber++;
            }
            if(popNumber > pushNumber){
                StdOut.println("stack underflow");
            }
        }
    }
}