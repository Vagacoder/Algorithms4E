package javasrc.ch01_3;

import lib.StdOut;
import lib.StdIn;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizableArrayStack<T> implements Iterable<T> {

    @SuppressWarnings("unchecked")
    private T[] a = (T[]) new Object[1];
    private int N = 0;

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void push(T newItem) {
        if (N == a.length) {
            resize(N * 2);
        }
        a[N++] = newItem;
    }

    public T pop() {
        T item = a[--N];
        a[N] = null;
        if (N > 10 && N <= a.length / 4) {
            resize(a.length / 2);
        }
        return item;
    }

    public T peek() {
        return a[N - 1];
    }

    public Iterator<T> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<T> {

        private int cursor = N;

        @Override
        public boolean hasNext() {
            return cursor > 0;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Stack is empty.");
            }
            return a[--cursor];
        }

    }

    // Helper function
    private void resize(int newSize) {
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Object[newSize];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        this.a = temp;
    }

    // Tester
    public static void main(String[] args) {
        ResizableArrayStack<String> s = new ResizableArrayStack<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                s.push(item);
            } else {
                if (!s.isEmpty()) {
                    StdOut.print(s.pop() + " ");
                } else {
                    StdOut.println("\n(Stack is empty)");
                }
            }
        }
        StdOut.println("(" + s.size() + " left in stack)");
        for(String item: s){
            StdOut.println(item);
        }
    }

}