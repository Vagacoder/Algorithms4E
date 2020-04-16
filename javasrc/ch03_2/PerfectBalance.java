package javasrc.ch03_2;

import javasrc.ch02_2.Merge;

/*
* 3.2.25 Perfect balance. Write a program that inserts a set of keys into an 
initially empty BST such that the tree produced is equivalent to binary search, 
in the sense that the sequence of compares done in the search for any key in the 
BST is the same as the sequence of compares used by binary search for the same 
key.

*/

import lib.*;

public class PerfectBalance<Key extends Comparable<Key>>{

    BST<Key, Integer> bst = new BST<>();

    public void insertArray(Key[] arr){
        Merge.sort(arr);
        insertArray(arr, 0, arr.length-1);
    }

    private void insertArray(Key[] arr, int low, int high){
        if(low > high){
            return;
        }
        int mid = (low + high)/2;
        bst.put(arr[mid], mid);
        insertArray(arr, low, mid-1);
        insertArray(arr, mid+1, high);
    }

    private void print(){
        bst.print();
    }

    public static void main(String[] args){
        String[] arr = {"A", "B", "C", "D", "E", "F", "G"};
        PerfectBalance<String> pb = new PerfectBalance<>();
        pb.insertArray(arr);
        pb.print();
    }
}