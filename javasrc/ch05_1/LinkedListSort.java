package javasrc.ch05_1;

/*
 * 5.1.16 Linked-list sort. Develop a sort implementation that takes a linked list 
 * of nodes with String key values as argument and rearranges the nodes so that 
 * they appear in sorted order (returning a link to the node with the smallest 
 * key). Use 3-way string quicksort.

 ? Idea: 
 ? 1, turn linkedlist to string array
 ? 2, sort string array using 3-way string quicksort
 ? 3, copy string array back to linkedlist

 */

import lib.*;

public class LinkedListSort {

    static class StringNode {
        String key;
        StringNode next;
    }

    public static void sort(StringNode first) {
        int length = 0;
        StringNode cur = first;

        while (cur != null) {
            length++;
            cur = cur.next;
        }

        if (length == 0) {
            return;
        }

        String[] a = new String[length];
        cur = first;

        for (int i = 0; i < length; i++) {
            a[i] = cur.key;
            cur = cur.next;
        }

        Quick3String.sort(a);
        first.key = a[0];
        cur = first;

        for (int i = 1; i < length; i++) {
            StringNode newNode = new StringNode();
            newNode.key = a[i];
            cur.next = newNode;
            cur = cur.next;
        }
    }

    public static void main(String[] args) {
        // * test #1
        StdOut.println("1. test words3.txt");
        String filename = "data/words3.txt";
        String[] input = new In(filename).readAllStrings();
        StdOut.println("Before sort");

        StringNode first = new StringNode();
        StringNode cur = first;

        for (int i = 0; i < input.length; i++) {
            String s = input[i];
            StdOut.println(s);
            cur.key = s;
            if (i != input.length - 1) {
                cur.next = new StringNode();
                cur = cur.next;
            }
        }

        LinkedListSort.sort(first);
        StdOut.println("\nAfter sort");
        cur = first;
        while (cur != null) {
            StdOut.println(cur.key);
            cur = cur.next;
        }

    }

}