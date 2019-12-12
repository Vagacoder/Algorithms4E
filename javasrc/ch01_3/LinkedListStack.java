package javasrc.ch01_3;

import java.util.Iterator;

import lib.StdIn;
import lib.StdOut;

public class LinkedListStack<T> implements Iterable<T> {

    private class Node {
        T item;
        Node next;
    }

    private Node first;
    private int sizeOfStack;

    public boolean isEmpty() {
        // return this.sizeOfStack == 0;
        return first == null;
    }

    public int size() {
        return this.sizeOfStack;
    }

    public void push(T newItem) {
        Node newNode = new Node();
        newNode.item = newItem;
        newNode.next = this.first;
        this.first = newNode;
        this.sizeOfStack++;
    }

    public T pop() {
        Node result = this.first;
        this.first = this.first.next;
        this.sizeOfStack--;
        return result.item;
    }

    public T peek(){
        return this.first.item;
    }

    public T removeLast(){
        Node cursor = first;
        if (first == null){
            return null;
        }

        while(cursor.next.next != null){
            cursor = cursor.next;
        }

        T result = cursor.item;
        cursor.next = null;
        this.sizeOfStack--;
        return result;
    }

    public T delete(int k) {
        if(k >= this.sizeOfStack){
            return null;
        }
        Node cursor = first;
        for (int i =0; i< k-1; i++){
            cursor = cursor.next;
        }
        Node temp = cursor.next;
        cursor.next = temp.next;
        this.sizeOfStack--;
        temp.next = null;
        return temp.item;
    }

    public boolean find(LinkedListStack<T> stack, T value){

        Node cursor = stack.first;
        if(cursor == null){
            return false;
        } else if (cursor.item.equals(value)){
            return true;
        }

        while(cursor.next != null){
            cursor= cursor.next;
            if (cursor.item.equals(value)){
                return true;
            }
        }
        return false;
    }

    public T removeAfter(Node a){
        if(a == null || a.next == null){
            return null;
        }

        Node cursor = first;
        if(cursor.equals(a)){
            this.first= a.next;
            a.next = null;
            this.sizeOfStack--;
            return a.item;
        }
        while(cursor.next != null){
            cursor = cursor.next;
            if(cursor.equals(a)){
                cursor.next = a.next;
                a.next = null;
                this.sizeOfStack--;
                return a.item;
            }
        }
        return null;
    }

    public boolean insertAfter(Node a, Node b){
        if(a == null || b == null){
            return false;
        }
        Node cursor = first;
        if(cursor.equals(a)){
            b.next = cursor.next;
            cursor.next = b;
            this.sizeOfStack++;
            return true;
        }
        while(cursor.next != null){
            cursor = cursor.next;
            if(cursor.equals(a)){
                b.next = cursor.next;
                cursor.next = b;
                this.sizeOfStack++;
                return true;
            }
        }
        return false;
    }

    public int remove(T value){
        int number = 0;

        if(first == null){
            return number;
        }

        Node cursor = first;
        if(cursor.item.equals(value)){
            first = first.next;
            this.sizeOfStack--;
            number++;
        }
        while(cursor.next != null){
            cursor = cursor.next;
            if (cursor.item.equals(value)){
                Node temp = cursor.next;
                cursor.next = temp.next;
                this.sizeOfStack--;
                number++;
                temp.next = null;
            }
        }
        return number;
    }

    public static void main(String[] args) {
        LinkedListStack<String> s = new LinkedListStack<>();
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            if (!input.equals("-")) {
                s.push(input);
            } else {
                if (s.isEmpty()) {
                    StdOut.println("(Stack is empty");
                } else {
                    StdOut.println(s.pop());
                }
            }
        }

        StdOut.println("(" + s.size() + " left in stack)");
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<T> {

        private Node cursor = first;

        @Override
        public boolean hasNext() {
            return this.cursor != null;
        }

        @Override
        public T next() {
            T result = this.cursor.item;
            this.cursor = this.cursor.next;
            return result;
        }
    }
}
