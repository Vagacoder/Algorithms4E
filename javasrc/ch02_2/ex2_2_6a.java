package javasrc.ch02_2;

/*
* 2.2.6 Write a program to compute the exact value of the number of array accesses used
by top-down mergesort and by bottom-up mergesort. Use your program to plot the val-
ues for N from 1 to 512, and to compare the exact values with the upper bound 6N lg N.

* Improved version, using static variable for counting
*/

import lib.StdOut;
import lib.StdRandom;

public class ex2_2_6a {

  private static Comparable[] aux;
  private static int accessNumber = 0;

  // * Merge sort Top-down
  public static void sortTd(Comparable[] a) {
    aux = new Comparable[a.length];
    accessNumber = 0;
    sortTd(a, 0, a.length - 1);
    StdOut.println(accessNumber);
  }

  private static void sortTd(Comparable[] a, int low, int high) {
    if (high <= low) {
      return;
    }
    int mid = (low + high) / 2;
    sortTd(a, low, mid);
    sortTd(a, mid + 1, high);
    merge(a, low, mid, high);
  }

  // * Merge sort Botton-up
  public static void sortBu(Comparable[] a) {
    int N = a.length;
    accessNumber = 0;
    aux = new Comparable[N];
    for (int size = 1; size < N; size *= 2) {
      for (int low = 0; low < N - size; low += (size * 2)) {
        merge(a, low, low + size - 1, Math.min(low + size + size - 1, N - 1));
      }
    }
    StdOut.println(accessNumber);
  }

  private static void merge(Comparable[] a, int low, int mid, int high) {
    int i = low, j = mid + 1;

    for (int k = low; k <= high; k++) {
      aux[k] = a[k];
      accessNumber += 2;
    }

    for (int k = low; k <= high; k++) {
      if (i > mid) {
        a[k] = aux[j++];
      } else if (j > high) {
        a[k] = aux[i++];
      } else if (less(aux[j], aux[i])) {
        a[k] = aux[j++];
      } else {
        a[k] = aux[i++];
      }
      accessNumber += 2;
    }
  }

  public static int getAN(){
      return accessNumber;
  }

  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  private static void exch(Comparable[] a, int i, int j) {
    Comparable t = a[i];
    a[i] = a[j];
    a[j] = t;
  }

  private static void show(Comparable[] a) { // Print the array, on a single line.
    for (int i = 0; i < a.length; i++)
      StdOut.print(a[i] + " ");
    StdOut.println();
  }

  public static boolean isSorted(Comparable[] a) { // Test whether the array entries are in order.
    for (int i = 1; i < a.length; i++)
      if (less(a[i], a[i - 1]))
        return false;
    return true;
  }

  /*
   * 2.1.16 Certification. Write a check() method that calls sort() for a given
   * array.
   */

  public static boolean check() {

    // test integer
    Integer[] a = { 2, 4, 5, 0, 9, 1, 3, 8, 6, 7 };
    sortBu(a);
    for (int i = 0; i < a.length - 1; i++) {
      if (a[i] > a[i + 1]) {
        return false;
      }
    }

    // test String
    String[] b = { "bed", "bug", "dad", "yes", "zoo", "now", "for", "tip", "ilk", "dim", "tag", "jot", "sob", "nob",
        "sky" };
    sortBu(b);
    for (int i = 0; i < b.length - 1; i++) {
      if (less(b[i + 1], b[i])) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {

    StdOut.println("1. testing using check() ...");
    StdOut.println(check());
    
    StdOut.println("\n2. testing on 1 to 512 elements arrays ...");
    for (int i = 1; i <= 512; i *= 2) {
      Double[] arr = new Double[i];
      Double[] arr1 = new Double[i];
      for (int j = 0; j < i; j++) {
        double number = StdRandom.uniform();
        arr[j] = number;
        arr1[j] = number;
      }
      sortTd(arr);
      StdOut.printf("For N = %d, Top-down access array: %d;\n", i, getAN());
      sortBu(arr1);
      StdOut.printf("For N = %d, Bottom-up acces array: %d.\n", i, getAN());
      StdOut.println("6NlgN is: " + (6 * i * Math.log(i)));

    }
  }

}