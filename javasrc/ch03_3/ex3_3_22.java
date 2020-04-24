package javasrc.ch03_3;

import javasrc.ch03_2.BST;

/*
* 3.3.22 Find a sequence of keys to insert into a BST and into a red-black BST such that
the height of the BST is less than the height of the red-black BST, or prove that no such
sequence is possible.

*/

import lib.*;

public class ex3_3_22{

    public static void main(String[] args){
        String raw = "H L J N I K O M D B F A G F C";
        String[] input = raw.split(" ");
        BST<String, Integer> bs = new BST<>();
        RedBlackBST<String, Integer> rb = new RedBlackBST<>();
        for(int i = 0; i < input.length; i++){
            bs.put(input[i], i);
            rb.put(input[i], i);
        }
        bs.print();
        StdOut.println(bs.height());
        rb.print();
        StdOut.println(rb.pureHeight());
    }
}