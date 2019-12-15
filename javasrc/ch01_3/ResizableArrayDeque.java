package javasrc.ch01_3;

import lib.StdOut;

/*
Excercise 1.3.33. II

Deque, a double-ended queue, (pronounced "deck") is like a stack or
a queue but supports adding and removing items at both ends.

This class implements Deque using Resizable Array

boolean isEmpty(): is the deque empty?

int size(): number of items in the deque

pushLeft(T item): add an item to the left

pushRight(T item): add an item to the right

popLeft(): remove an item from the left

popRight(): remove an item from the right

print(): print values of all nodes in Deque.

*/

public class ResizableArrayDeque<T> {

    private T[] queue;
    private int numberInQueue;
    private int firstIndex;
    private int lastIndex;

    public ResizableArrayDeque() {
        @SuppressWarnings("unchecked")
        T[] q = (T[]) new Object[3];
        this.queue = q;
        this.numberInQueue = 0;
        this.firstIndex = 1;
        this.lastIndex = 1;
    }

    public boolean isEmpty() {
        return this.numberInQueue == 0;
    }

    public int size() {
        return this.numberInQueue;
    }

    private void resize(int newCapacity, int newFirstIndex) {
        assert newCapacity >= this.numberInQueue + newFirstIndex;
        @SuppressWarnings("unchecked")
        T[] newArray = (T[]) new Object[newCapacity];
        for (int i = 0; i < this.numberInQueue; i++) {
            newArray[newFirstIndex + i] = this.queue[this.firstIndex + i];
        }
        this.queue = newArray;
        this.firstIndex = newFirstIndex;
        this.lastIndex = newFirstIndex + this.numberInQueue - 1;
    }

    // add an item to the left
    public void pushLeft(T newItem) {
        // if the left is full, no more space to add new item
        if (this.firstIndex == 0) {
            resize((this.queue.length + this.numberInQueue), this.numberInQueue);
        }
        // if left is not full
        else {
            // left space is too large, shrink it
            if (this.numberInQueue > 0 && this.firstIndex > this.numberInQueue * 2) {
                resize(this.numberInQueue * 3, this.numberInQueue);
            }
        }
        // add new item to left
        this.queue[--firstIndex] = newItem;
        this.numberInQueue++;
    }

    // remove an item from the left
    public T popLeft() {
        T result = this.queue[this.firstIndex];
        this.queue[this.firstIndex++] = null;
        this.numberInQueue--;
        // left space is too large, shrink it
        if (this.numberInQueue > 0 && this.firstIndex > (this.numberInQueue * 2)) {
            resize(this.numberInQueue * 3, this.numberInQueue);
        }
        return result;
    }

    public void pushRight(T newItem) {
        // if right is full, no more space to add new item
        if (this.lastIndex == this.queue.length - 1) {
            resize((this.queue.length + this.numberInQueue), this.firstIndex);
        }
        // if right is not full
        else {
            // right space is too large, shrink it
            if (this.numberInQueue > 0 && (this.queue.length - this.lastIndex) > (this.numberInQueue * 2)) {
                resize(this.numberInQueue * 3, this.firstIndex);
            }
        }
        this.queue[++lastIndex] = newItem;
        this.numberInQueue++;
    }

    public T popRight() {
        T result = this.queue[this.lastIndex];
        this.queue[this.lastIndex--] = null;
        this.numberInQueue--;
        // right space is too large, shrink it
        if (this.numberInQueue > 0 && (this.queue.length - this.lastIndex) > (this.numberInQueue * 2)) {
            resize(this.numberInQueue * 3, this.firstIndex);
        }
        return result;
    }

    public void print() {
        for (T item : this.queue) {
            StdOut.print(item + " ");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        ResizableArrayDeque<String> deq = new ResizableArrayDeque<>();
        StdOut.println("1. Testing push and pop from left ...");
        deq.pushLeft("they");
        // deq.print();
        deq.pushLeft("are");
        // deq.print();
        deq.pushLeft("good");
        // deq.print();
        deq.pushLeft("guys");
        // deq.print();
        deq.pushLeft("too");
        deq.print();

        StdOut.println(deq.popLeft());
        deq.print();
        StdOut.println(deq.popLeft());
        deq.print();
        StdOut.println(deq.popLeft());
        deq.print();
        StdOut.println(deq.popLeft());
        deq.print();
        StdOut.println(deq.popLeft());
        deq.print();

        StdOut.println("2. Testing push and pop from right ...");
        deq.pushRight("we");
        // deq.print();
        deq.pushRight("love");
        // deq.print();
        deq.pushRight("computer");
        // deq.print();
        deq.pushRight("science");
        // deq.print();
        deq.pushRight("as");
        // deq.print();
        deq.pushRight("well");
        deq.print();

        StdOut.println(deq.popRight());
        deq.print();
        StdOut.println(deq.popRight());
        deq.print();
        StdOut.println(deq.popRight());
        deq.print();
        StdOut.println(deq.popRight());
        deq.print();
        StdOut.println(deq.popRight());
        deq.print();
        StdOut.println(deq.popRight());
        deq.print();
        StdOut.println(deq.popRight());
        deq.print();
    }
}