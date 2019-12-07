package javasrc.ch01_3;

import lib.StdOut;
import lib.StdIn;

public class FixedCapacityStack<T> {

    private T[] a;
    private int N;

    public FixedCapacityStack(int capacity) {
        @SuppressWarnings("unchecked")
        T[] a = (T[]) new Object[capacity];
        this.a = a;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void push(T item) {
        a[N++] = item;
    }

    public T pop() {
        return a[--N];
    }

    public static void main(String[] args) {
        FixedCapacityStack<String> s = new FixedCapacityStack<>(100);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                s.push(item);
            } else if (s.isEmpty()) {
                StdOut.println("(Stack is empty)");
            } else {
                StdOut.println(s.pop());
            }
        }
        StdOut.println("(" + s.size() + " left in stack)");
    }
}