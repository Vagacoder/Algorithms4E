package javasrc.ch01_3;

import java.util.Arrays;
import java.util.Iterator;

import lib.StdOut;

public class RandomBag<T> implements Iterable<T> {

    private T[] bag;
    private int sizeOfBag;
    private int capacity;

    public RandomBag(int capacity) {
        @SuppressWarnings("unchecked")
        T[] b = (T[]) new Object[capacity];
        this.bag = b;
        this.sizeOfBag = 0;
        this.capacity = capacity;
    }

    public boolean isEmpty() {
        return this.sizeOfBag == 0;
    }

    public boolean isFull() {
        return this.sizeOfBag == this.capacity;
    }

    public int size() {
        return this.sizeOfBag;
    }

    public void add(T newItem) {
        if (!this.isFull()) {
            this.bag[this.sizeOfBag++] = newItem;
        } else {
            StdOut.println("Bag is full.");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new RandomBagIterator();
    }

    private class RandomBagIterator implements Iterator<T> {

        private T[] copy = Arrays.copyOf(bag, bag.length);
        private int numberLeft = sizeOfBag;

        @Override
        public boolean hasNext() {
            return this.numberLeft > 0;
        }

        @Override
        public T next() {
            int index = (int) (Math.random() * sizeOfBag);
            while (this.copy[index] == null) {
                index = (int) (Math.random() * sizeOfBag);
            }
            T result = this.copy[index];
            this.copy[index] = null;
            this.numberLeft--;
            return result;
        }

    }

    public static void main(String[] args) {

        RandomBag<String> bg = new RandomBag<>(20);
        StdOut.println(bg.isEmpty());
        StdOut.println(bg.isFull());
        bg.add("we");
        bg.add("are");
        bg.add("good");
        bg.add("students");
        bg.add("they");
        bg.add("like");
        bg.add("work");
        bg.add("in");
        bg.add("lab");
        bg.add("as");
        bg.add("well");
        StdOut.println(bg.size());

        for (String s : bg) {
            StdOut.println(s);
        }
    }

}