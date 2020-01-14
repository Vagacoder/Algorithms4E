package javasrc.ch02_1;

/*
Algorithm 2.1 Selection sort P.249

*/
/******************************************************************************
 *  Compilation:  javac Selection.java
 *  Execution:    java  Selection < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   https://algs4.cs.princeton.edu/21elementary/tiny.txt
 *                https://algs4.cs.princeton.edu/21elementary/words3.txt
 *   
 *  Sorts a sequence of strings from standard input using selection sort.
 *   
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java Selection < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *    
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *  
 *  % java Selection < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
 *
 ******************************************************************************/

import java.util.Comparator;

import lib.StdIn;
import lib.StdOut;

public class SelectionSort {

   private SelectionSort() {
   }

   // Rearranges the array in ascending order, using the natural order.
   public static void sort(Comparable[] a) {
      int n = a.length;
      for (int i = 0; i < n; i++) {
         int minIndex = i;
         for (int j = i + 1; j < n; j++) {
            if (less(a[j], a[minIndex])) {
               minIndex = j;
            }
         }
         exch(a, i, minIndex);
         assert isSorted(a, 0, i);
      }
      assert isSorted(a);
   }

   // Rearranges the array in ascending order, using a comparator.
   public static void sort(Object[] a, Comparator comparator) {
      int n = a.length;
      for (int i = 0; i < n; i++) {
         int minIndex = i;
         for (int j = i + 1; j < n; j++) {
            if (less(comparator, a[j], a[minIndex]))
               minIndex = j;
         }
         exch(a, i, minIndex);
         assert isSorted(a, comparator, 0, i);
      }
      assert isSorted(a, comparator);
   }

   // Helpers
   private static boolean less(Comparable v, Comparable w) {
      return v.compareTo(w) < 0;
   }

   private static boolean less(Comparator comparator, Object v, Object w) {
      return comparator.compare(v, w) < 0;
   }

   private static void exch(Object[] a, int i, int j) {
      Object temp = a[i];
      a[i] = a[j];
      a[j] = temp;
   }

   private static boolean isSorted(Comparable[] a){
      return isSorted(a, 0, a.length-1);
   }

   private static boolean isSorted(Comparable[] a, int low, int high){
      for(int i = low+1; i< high; i++){
         if(less(a[i], a[i-1])){
            return false;
         }
      }
      return true;
   }

   private static boolean isSorted(Object[] a, Comparator comparator) {
      return isSorted(a, comparator, 0, a.length - 1);
  }

   private static boolean isSorted(Object[] a, Comparator comparator, int low, int high){
      for (int i = low + 1; i <=high; i++){
         if(less(comparator, a[i], a[i-1])){
            return false;
         }
      }
      return true;
   }

   private static void show(Comparable[] a){
      for(int i = 0; i< a.length; i++){
         StdOut.println(a[i]);
      }
   }

   public static void main(String[] args){
      String[] a = StdIn.readAllStrings();
      SelectionSort.sort(a);
      show(a);
   }
}