package javasrc.ch05_1;

/*
* 5.1.7 Develop an implementation of key-indexed counting that makes use of an 
* array of Queue objects.
* 
*/

import javasrc.ch01_3.LinkedListQueue;
import lib.*;

public class QueueLSD {

    public static void sort(String[] a, int W) {
        int N = a.length;
        int R = 256;
        String[] aux = new String[N];

        for (int d = W - 1; d >= 0; d--) {

            LinkedListQueue<String>[] count = new LinkedListQueue[R];

            for (int i = 0; i < N; i++) {
                int countIndex = a[i].charAt(d);
                if (count[countIndex] == null) {
                    count[countIndex] = new LinkedListQueue<String>();
                }
                count[countIndex].enqueue(a[i]);
            }

            int index = 0;
            for (int i = 0; i < R; i++) {
                if (count[i] != null) {
                    while (!count[i].isEmpty()) {
                        aux[index++] = count[i].dequeue();
                    }
                }
            }

            for(int i = 0; i < N; i++){
                a[i] = aux[i];
            }
        }

    }

    public static void main(String[] args) {
        StdOut.println("1. test words3.txt");
        String filename = "data/words3.txt";
        String[] input = new In(filename).readAllStrings();
        StdOut.println("Before sort");
        for (String s : input) {
            StdOut.println(s);
        }

        QueueLSD.sort(input, input[0].length());
        StdOut.println("\nAfter sort");
        for (String s : input) {
            StdOut.println(s);
        }

        // * test #2
        StdOut.println("\n2. test plates.txt");
        filename = "data/plates.txt";
        input = new In(filename).readAllStrings();
        StdOut.println("Before sort");
        for(String s: input){
            StdOut.println(s);
        }

        QueueLSD.sort(input, input[0].length());
        StdOut.println("\nAfter sort");
        for(String s: input){
            StdOut.println(s);
        }
    }
}