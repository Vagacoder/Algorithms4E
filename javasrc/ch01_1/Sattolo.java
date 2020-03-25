package javasrc.ch01_1;

/*
* Web Ex. 1.1.1. Sattolo's algorithm. 

Write a program Sattolo.java that generates a unifomly distributed cycle of length N 
using Sattolo's algorithm. 

? https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#Sattolo.27s_algorithm

*/

import lib.*;

public class Sattolo{

    private Sattolo(){}

    public static void shuffle(Object[] a){
        for (int i = a.length-1; i > 1; i--){
            int j = StdRandom.uniform(0, i);
            Object temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }

    }

    public static void main(String[] args){
    
        int max = 10;
        Integer[] a = new Integer[10];
        for(int i = 0; i < max; i++){
            a[i] = i+1;
        }
        shuffle(a);
        for(int i = 0; i < max; i++){
            StdOut.print(a[i] + ", ");
        }
    }
}