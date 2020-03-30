package javasrc.ch02_2;

/*
* Algorithm 2.4 Bottom-up mergesort P. 278

* Proposition H. Bottom-up mergesort uses between 1⁄2 N lg N and N lg N compares
and at most 6N lg N array accesses to sort an array of length N.

*/

/*
* Improvement #1 : skip merge() when array is already sorted();
* 2.2.8 Suppose that Algorithm 2.4 is modified to skip the call on merge() whenever
a[mid] <= a[mid+1]. Prove that the number of compares used to mergesort a sorted
array is linear.

*/

import lib.In;
import lib.StdOut;

public class MergeBU {

  private static Comparable[] aux;

  public static void sort(Comparable[] a) {
    int N = a.length;
    aux = new Comparable[N];
    for (int size = 1; size < N; size *= 2) {
      for (int low = 0; low < N - size; low += (size * 2)) {
        merge(a, low, low + size - 1, Math.min(low + size + size - 1, N - 1));
      }
    }
  }

  private static void merge(Comparable[] a, int low, int mid, int high) {
    int i = low, j = mid + 1;

    // * Improvement #1 : skip merge() when array is already sorted();
    if (!less(a[j], a[mid])) {
      return;
    }

    for (int k = low; k <= high; k++) {
      aux[k] = a[k];
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
    }
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
    sort(a);
    for (int i = 0; i < a.length - 1; i++) {
      if (a[i] > a[i + 1]) {
        return false;
      }
    }

    // test String
    String[] b = { "bed", "bug", "dad", "yes", "zoo", "now", "for", "tip", "ilk", "dim", "tag", "jot", "sob", "nob",
        "sky" };
    sort(b);
    for (int i = 0; i < b.length - 1; i++) {
      if (less(b[i + 1], b[i])) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) { // Read strings from standard input, sort them, and print.
    // String[] a = In.readStrings();
    // sort(a);
    // assert isSorted(a);
    // show(a);

    StdOut.println(check());
  }
}