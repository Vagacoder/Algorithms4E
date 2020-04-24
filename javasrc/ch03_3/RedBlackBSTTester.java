package javasrc.ch03_3;

/*
* 3.3.21 Create a test client for RedBlackBST, based on your solution to Exercise 3.2.10
*/

import lib.*;

public class RedBlackBSTTester{

    public static void main(String[] args){
        String[] str = {"S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E"};
        int number = str.length;
        RedBlackBST<String, Integer> rb = new RedBlackBST<>();
        for(int i = 0; i< number; i++){
            rb.put(str[i], i);
        }

        rb.print();
        for(int i = 0; i< number; i++){
            StdOut.println(str[i] + ": " +rb.get(str[i]));
        }

    }
}