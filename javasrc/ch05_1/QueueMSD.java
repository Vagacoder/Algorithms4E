package javasrc.ch05_1;

/*
 * 5.1.11 Queue sort. Implement MSD string sorting using queues, as follows: Keep 
 * one queue for each bin. On a first pass through the items to be sorted, insert 
 * each item into the appropriate queue, according to its leading character value. 
 * Then, sort the sublists and stitch together all the queues to make a sorted 
 * whole. Note that this method does not involve keeping the count[] arrays within 
 * the recursive method.
 * 
 */

import javasrc.ch01_3.LinkedListQueue;
import javasrc.ch02_1.Insertion;
import lib.*;

public class QueueMSD {

    private static int R = 256;
    private static String[] aux;

    public static void sort(String[] a) {

        int N = a.length;
        aux = new String[N];

        sort(a, 0, N - 1, 0);
    }

    private static void sort(String[] a, int low, int high, int d) {
        if (high <= low) {
            return;
        }

        LinkedListQueue<String>[] count = new LinkedListQueue[R + 1];
        for (int i = 0; i < R + 1; i++) {
            count[i] = new LinkedListQueue<>();
        }

        for (int i = low; i <= high; i++) {
            count[charAt(a[i], d) + 1].enqueue(a[i]);
        }

        // ! NOTE: for i = 0, string length <= d, no need sort.
        for (int i = 1; i < R + 1; i++) {
            int length = count[i].size();
            if (length > 0) {
                String[] subA = new String[length];
                for (int j = 0; j < length; j++) {
                    subA[j] = count[i].dequeue();
                }
                sort(subA, 0, length - 1, d + 1);
                for (int j = 0; j < length; j++) {
                    count[i].enqueue(subA[j]);
                }
            }
        }

        int index = 0;
        for (int i = 0; i < R + 1; i++) {
            while (!count[i].isEmpty()) {
                a[index++] = count[i].dequeue();
            }
        }
    }

    private static int charAt(String s, int d) {
        if (d < s.length()) {
            return s.charAt(d);
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        // * test #1
        StdOut.println("1. test shells.txt");
        String filename = "data/shells.txt";
        String[] input = new In(filename).readAllStrings();

        // ? test empty string, charAt() return -1
        // input[0] = "";

        StdOut.println("Before sort:");
        for (String s : input) {
            StdOut.println(s);
        }

        QueueMSD.sort(input);
        StdOut.println("\nAfter sort:");
        for (String s : input) {
            StdOut.println(s);
        }


        // * test #2
        StdOut.println("2. test words3.txt");
        filename = "data/words3.txt";
        input = new In(filename).readAllStrings();
        StdOut.println("Before sort:");
        for (String s : input) {
            StdOut.println(s);
        }

        QueueMSD.sort(input);
        StdOut.println("\nAfter sort:");
        for (String s : input) {
            StdOut.println(s);
        }

        // * test #3
        StdOut.println("\n3. test plates.txt");
        filename = "data/plates.txt";
        input = new In(filename).readAllStrings();
        StdOut.println("Before sort:");
        for (String s : input) {
            StdOut.println(s);
        }

        QueueMSD.sort(input);
        StdOut.println("\nAfter sort:");
        for (String s : input) {
            StdOut.println(s);
        }
    }
}