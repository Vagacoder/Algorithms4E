package javasrc.ch02_2;

import javasrc.ch01_3.LinkedListQueue;
import lib.In;
import lib.StdOut;

public class BottomUpQueueMerge {

    private static LinkedListQueue<LinkedListQueue<Comparable>> mainQ = new LinkedListQueue<>();

    public static void sort(Comparable[] a) {

        for(int i = 0 ; i < a.length; i++){
            LinkedListQueue<Comparable> element = new LinkedListQueue<>();
            element.enqueue(a[i]);
            BottomUpQueueMerge.mainQ.enqueue(element);
        }

        while(mainQ.size() > 1){
            LinkedListQueue<Comparable> element1 = mainQ.dequeue();
            LinkedListQueue<Comparable> element2 = mainQ.dequeue();
            LinkedListQueue<Comparable> newElement = merge(element1, element2);
            mainQ.enqueue(newElement);
        }

        LinkedListQueue<Comparable> resultQ = mainQ.dequeue();
        for(int i = 0; i< a.length; i++){
            a[i] = resultQ.dequeue();
        }
    }

    private static LinkedListQueue<Comparable> merge(LinkedListQueue<Comparable> q1, LinkedListQueue<Comparable> q2){
        LinkedListQueue<Comparable> result = new LinkedListQueue<>();

        while(!q1.isEmpty() || !q2.isEmpty()){
            if(q1.isEmpty()){
                result.enqueue(q2.dequeue());
            } else if (q2.isEmpty()){
                result.enqueue(q1.dequeue());
            } else {
                Comparable temp1 = q1.peekTop();
                Comparable temp2 = q2.peekTop();
                if(less(temp1, temp2)){
                    result.enqueue(q1.dequeue());
                } else {
                    result.enqueue(q2.dequeue());
                }
            }
        }

        return result;
    }


    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    // Print the array, on a single line.
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++)
            StdOut.print(a[i] + " ");
        StdOut.println();
    }

    // Test whether the array entries are in order.
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1]))
                return false;
        return true;
    }

    public static boolean check() {

        // test integer
        Integer[] a = { 2, 4, 5, 0, 9, 1, 3, 8, 6, 7 };
        sort(a);
        show(a);
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }

        // test String
        String[] b = { "bed", "bug", "dad", "yes", "zoo", "now", "for", "tip", "ilk", "dim", "tag", "jot", "sob", "nob",
                "sky" };
        sort(b);
        show(b);
        for (int i = 0; i < b.length - 1; i++) {
            if (less(b[i + 1], b[i])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // Read strings from standard input, sort them, and print.
        // String[] a = In.readStrings();
        // sort(a);
        // assert isSorted(a);
        // show(a);

        StdOut.println(check());
    }
}