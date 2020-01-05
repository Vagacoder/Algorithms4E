package javasrc.ch01_2;

/*
1.4.11 Add an instance method howMany() to StaticSETofInts (page 99) that finds the
number of occurrences of a given key in time proportional to log N in the worst case.

1.4.21 Binary search on distinct values. Develop an implementation of binary search
for StaticSETofInts (see page 99) where the running time of contains() is guar-
anteed to be ~ lg R, where R is the number of different integers in the array given as
argument to the constructor.

*/

import java.util.Arrays;
import lib.StdOut;

public class StaticSETofInts {

    private int[] a;

    public StaticSETofInts(int[] keys) {
        a = new int[keys.length];
        for (int i = 0; i < keys.length; i++) {
            a[i] = keys[i];
        }
        Arrays.sort(a);
    }

    public boolean contains(int key) {
        return rank(key) != -1;
    }

    private int rank(int key) {
        int low = 0;
        int high = a.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (key < a[mid]) {
                high = mid - 1;
            } else if (key > a[mid]) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public int howMany(int key) {
        int low = 0;
        int high = a.length - 1;
        int index = -1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (key < a[mid]) {
                high = mid - 1;
            } else if (key > a[mid]) {
                low = mid + 1;
            } else {
                index = mid;
                break;
            }
        }

        if (index < 0) {
            return 0;
        } else {
            int lowestIndex = findLowest(key, low, index);
            int highestIndex = findHighest(key, index, high);
            return highestIndex - lowestIndex + 1;
        }
    }

    private int findLowest(int key, int low, int high) {
        if (low >= high - 1) {
            return a[low] == key ? low : high;
        }
        int mid = (low + high) / 2;
        if (key > this.a[mid]) {
            return findLowest(key, mid, high);
        } else {
            return findLowest(key, low, mid);
        }
    }

    private int findHighest(int key, int low, int high) {
        if (low >= high - 1) {
            return a[high] == key ? high : low;
        }
        int mid = (low + high) / 2;
        if (key < this.a[mid]) {
            return findHighest(key, low, mid);
        } else {
            return findHighest(key, mid, high);
        }
    }

    public static void main(String[] args) {
        int[] a = { 1, 1, 2, 3, 3, 3, 4, 5, 5, 6, 6, 6, 6, 7, 8, 9, 9, 9 };
        StaticSETofInts set = new StaticSETofInts(a);
        StdOut.println("Expext 2 (of 1): " + set.howMany(1));
        StdOut.println("Expext 1 (of 2): " + set.howMany(2));
        StdOut.println("Expext 3 (of 3): " + set.howMany(3));
        StdOut.println("Expext 1 (of 4): " + set.howMany(4));
        StdOut.println("Expext 2 (of 5): " + set.howMany(5));
        StdOut.println("Expext 4 (of 6): " + set.howMany(6));
        StdOut.println("Expext 1 (of 7): " + set.howMany(7));
        StdOut.println("Expext 1 (of 8): " + set.howMany(8));
        StdOut.println("Expext 3 (of 9): " + set.howMany(9));
    }
}