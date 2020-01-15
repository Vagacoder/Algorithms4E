package javasrc.ch02_1;

/*
Algorithm 2.2 Insertion sort P. 251

*/

/******************************************************************************
 *  Compilation:  javac Insertion.java
 *  Execution:    java Insertion < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   https://algs4.cs.princeton.edu/21elementary/tiny.txt
 *                https://algs4.cs.princeton.edu/21elementary/words3.txt
 *  
 *  Sorts a sequence of strings from standard input using insertion sort.
 *
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java Insertion < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *
 *  % java Insertion < words3.txt
 *  all bad bed bug dad ... yes yet zoo   [ one string per line ]
 *
 ******************************************************************************/

 import java.util.Comparator;

 public class InsertionSort{

    private InsertionSort(){}

    public static void sort(Comparable[] a){
        int n = a.length;
        for( int i = 1; i< n; i++){
            for(int j = i; j > 0 && less(a[j],a[j-1]); j--){
                exch(a, j, j-1);
            }
            assert isSorted(a, 0, i);
        }
        assert isSorted(a);
    }

    public static void sort(Comparable[] a, int low, int high){
        for (int i = low + 1; i< high; i++){
            for(int j = i; j>low & less(a[j], a[j-1]); j--){
                exch(a, j, j-1);
            }
        }
        assert isSorted(a, low, high);
    }

    pu

 }