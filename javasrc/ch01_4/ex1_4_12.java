package javasrc.ch01_4;

import javasrc.ch01_3.LinkedListStack;

/*
1.4.12. Write a program that, given two sorted arrays of N int values, prints all 
elements that appear in both arrays, in sorted order. 

The running time of your program should be proportional to N in the worst case.

*/

import lib.StdOut;

public class ex1_4_12 {

    public static void printCommonElements(int[] a, int[] b) {
        int alen = a.length;
        int blen = b.length;

        LinkedListStack<Integer> stack = new LinkedListStack<>();
        int curA = 0;
        int curB = 0;

        while (curA < alen && curB < blen) {
            int elementA = a[curA];
            int elementB = b[curB];
            if (elementA == elementB) {
                if (stack.peek() != null) {
                    if (stack.peek() != elementA) {
                        StdOut.println(elementA);
                        stack.push(elementA);
                        curA++;
                        curB++;
                    }
                } else {
                    StdOut.println(elementA);
                    stack.push(elementA);
                    curA++;
                    curB++;
                }
            } else if (elementA < elementB) {
                curA++;
            } else {
                curB++;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = { 2, 3, 4, 5, 6, 7, 12 };
        int[] b = { 2, 4, 6, 8, 10, 12, 14};
        printCommonElements(a, b);
    }
}
