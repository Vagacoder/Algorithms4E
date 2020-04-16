package javasrc.ch03_2;

/*
* 3.2.17 Draw the sequence of BSTs that results when you delete the keys from the 
tree of Exercise 3.2.1, one by one, in the order they were inserted.
"E A S Y Q U E S T I O N",

* 3.2.18 Draw the sequence of BSTs that results when you delete the keys from the tree
of Exercise 3.2.1, one by one, in alphabetical order.

* 3.2.19 Draw the sequence of BSTs that results when you delete the keys from the tree
of Exercise 3.2.1, one by one, by successively deleting the key at the root.

*/

import lib.*;

public class ex3_2_17{

    public static void main(String[] args){
        BST<String, Integer> bst = new BST<>();
        bst.put("E", 1);
        bst.put("A", 2);
        bst.put("S", 3);
        bst.put("Y", 4);
        bst.put("Q", 5);
        bst.put("U", 6);
        bst.put("E", 7);
        bst.put("S", 8);
        bst.put("T", 9);
        bst.put("I", 10);
        bst.put("O", 11);
        bst.put("N", 12);
        bst.print();
        // * 3.2.17
        // bst.delete("E");
        // bst.delete("A");
        // bst.delete("S");
        // bst.delete("Y");
        // bst.delete("Q");
        // bst.delete("U");
        // bst.delete("E");
        // bst.delete("S");
        // bst.delete("T");
        // bst.delete("I");
        // bst.delete("O");
        // bst.delete("N");
        // * 3.2.18
        // bst.delete("A");
        // bst.delete("E");
        // bst.delete("E");
        // bst.delete("I");
        // bst.delete("O");
        // bst.delete("Q");
        // bst.delete("N");
        // bst.delete("S");
        // bst.delete("S");
        // bst.delete("T");
        // bst.delete("U");
        // bst.delete("Y");
        // * 3.2.19
        bst.delete("E");
        bst.delete("I");
        bst.delete("N");
        bst.delete("O");
        bst.delete("Q");
        bst.delete("S");
        bst.delete("T");
        bst.delete("U");
        bst.delete("Y");
        bst.delete("A");
    }
}