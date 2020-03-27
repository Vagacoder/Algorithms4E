package javasrc.ch01_3;

/*
* Algorithm 1.1 Pushdown stack (resizing array implementation) 
* P.141

* Proposition E. In the resizing array implementation of Stack (Algorithm 1.1),
the average number of array accesses for any sequence of push and pop operations
starting from an empty data structure is constant in the worst case.

1.3.42 Copy a stack. 
Create a new constructor for the linked-list implementation of Stack so that

    Stack<Item> t = new Stack<Item>(s);

makes t a reference to a new and independent copy of the stack s.

*/
import lib.StdOut;
import lib.StdIn;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizableArrayStack<T> implements Iterable<T> {

    @SuppressWarnings("unchecked")
    private T[] a = (T[]) new Object[1];
    private int N = 0;

    // no need, default constructor
    public ResizableArrayStack(){
    }

    // Copy constructor
    public ResizableArrayStack(ResizableArrayStack<T> s){
        ResizableArrayStack<T> temp = new ResizableArrayStack<>();

        while(!s.isEmpty()){
            temp.push(s.pop());
        }
        while(!temp.isEmpty()){
            T tempItem = temp.pop();
            s.push(tempItem);
            this.push(tempItem);
        }

    }

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
        
        // 1. test by StdIn input
        // while (!StdIn.isEmpty()) {
        //     String item = StdIn.readString();
        //     if (!item.equals("-")) {
        //         s.push(item);
        //     } else {
        //         if (!s.isEmpty()) {
        //             StdOut.print(s.pop() + " ");
        //         } else {
        //             StdOut.println("\n(Stack is empty)");
        //         }
        //     }
        // }
        // StdOut.println("(" + s.size() + " left in stack)");
        // for(String item: s){
        //     StdOut.println(item);
        // }

        // 2. test by hard coding input
        s.push("we");
        s.push("are");
        s.push("good");
        s.push("at");
        s.push("study");
        StdOut.println("(" + s.size() + " left in stack)");

        ResizableArrayStack<String> s1 = new ResizableArrayStack<>(s);
        StdOut.println("(" + s1.size() + " left in stack)");

        StdOut.println(s1.pop());
        StdOut.println("(" + s.size() + " left in stack)");
        StdOut.println("(" + s1.size() + " left in stack)");

    }

}