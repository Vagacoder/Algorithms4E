/******************************************************************************
 *  Compilation:  javac BinarySearch.java
 *  Execution:    java BinarySearch whitelist.txt < input.txt
 *  Dependencies: In.java StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/11model/tinyW.txt
 *                https://algs4.cs.princeton.edu/11model/tinyT.txt
 *                https://algs4.cs.princeton.edu/11model/largeW.txt
 *                https://algs4.cs.princeton.edu/11model/largeT.txt
 *
 *  % java BinarySearch tinyW.txt < tinyT.txt
 *  50
 *  99
 *  13
 *
 *  % java BinarySearch largeW.txt < largeT.txt | more
 *  499569
 *  984875
 *  295754
 *  207807
 *  140925
 *  161828
 *  [367,966 total values]
 *  
 ******************************************************************************/

package javasrc.ch01_1;

import java.util.Arrays;
import lib.StdIn;
import lib.StdOut;
import edu.princeton.cs.algs4.In;
public class BinarySearch {

  public static int rank(int key, int[] a) {
    int lo = 0;
    int hi = a.length - 1;
    while (lo <= hi) {
      int mid = lo + (hi - lo) / 2;
      if (key < a[mid]) {
        hi = mid - 1;
      } else if (key > a[mid]) {
        lo = mid + 1;
      } else {
        return mid;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    int[] whitelist = new In(args[0]).readAllInts();

    Arrays.sort(whitelist);

    while (!StdIn.isEmpty()) {
      int key = StdIn.readInt();
      if (rank(key, whitelist) == -1) {
        StdOut.println(key);
      }
    }
  }
}
