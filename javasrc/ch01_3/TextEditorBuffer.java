package javasrc.ch01_3;

/*
1.3.44 Text editor buffer. 
Develop a data type for a buffer in a text editor that implements the following API:

    Buffer(): create an empty buffer
void insert(char c): insert c at the cursor position
char get(): character at the cursor position
char delete(): delete and return the character at the cursor
void left(int k): move the cursor k positions to the left
void right(int k): move the cursor k positions to the right
int size(): number of characters in the buffer

Hint : Use two stacks.

*/

import lib.StdOut;

public class TextEditorBuffer {

    LinkedListStack<Character> buffer = new LinkedListStack<>();
    LinkedListStack<Character> temp = new LinkedListStack<>();
    // cursor mechanism:
    // 1) cursor ranges from 0 to buffer.size(), both are inclusive;
    // 2) when cursor == 0, it points to first element of buffer;
    // 3) when cursor == buffer.size(), it points the first empty position after
    // buffer, which is null;
    // 4) insert is always before cursor; after insertion cursor++
    // 5) deletion happens at cursor position; after deletetion cursor does not
    // move;
    // 6) delete does nothing when cursor = buffer.size(), which means at end of
    // buffer;
    int cursor;

    public TextEditorBuffer() {
        this.cursor = 0;
    }

    public int size() {
        return buffer.size();
    }

    // insert to BEFORE the cursor
    public void insert(char c) {
        this.buffer.push(c);
        this.cursor++;
    }

    // get character which is one BEFORE cursor position
    public char get() {
        if (this.size() == 0 || cursor == 0) {
            return '\u0000';
        }
        // calculate distance betweem the end of buffer and cursor
        int popNumber = this.buffer.size() - this.cursor;
        for (int i = 0; i < popNumber; i++) {
            this.temp.push(this.buffer.pop());
        }
        char result;

        if (this.buffer.peek() == null) {
            result = '\u0000';
        } else {
            result = this.buffer.peek();
        }
        while (!this.temp.isEmpty()) {
            this.buffer.push(this.temp.pop());
        }

        return result;
    }

    public void left(int k) {
        this.cursor -= k;
        if (this.cursor < 0) {
            this.cursor = 0;
        }
    }

    public void right(int k) {
        this.cursor += k;
        if (this.cursor > this.buffer.size()) {
            this.cursor = this.buffer.size();
        }
    }

    // delete the char pointed by cursor; if cursor at end of buffer, which points
    // null, delete nothing
    public char delete() {
        // calculate distance betweem the end of buffer and cursor
        int popNumber = this.buffer.size() - this.cursor;
        if (popNumber == 0) {
            return '\u0000';
        } else if (popNumber == 1) {
            return this.buffer.pop();
        } else {
            for (int i = 0; i < popNumber - 1; i++) {
                this.temp.push(this.buffer.pop());
            }
            char result = this.buffer.pop();
            this.buffer.push(this.temp.pop());
            return result;
        }
    }

    public static void main(String[] args) {
        TextEditorBuffer teb = new TextEditorBuffer();
        StdOut.println("1. test get() from empty buffer or beginning of buffer ...");
        StdOut.println(teb.get());
        StdOut.println("1. end of test ...");

        StdOut.println("2. test insert() and get() ");
        teb.insert('a');
        StdOut.println(teb.get());
        teb.insert('b');
        StdOut.println(teb.get());
        teb.insert('\u2514');
        StdOut.println(teb.get());
        teb.insert('c');
        StdOut.println(teb.get());
        teb.insert('d');
        StdOut.println(teb.get());
        teb.insert('\u00AE');
        StdOut.println(teb.get());
        StdOut.println("2. end of test ...");

        StdOut.println("3. test left() and right() ");
        teb.left(1); // get d
        StdOut.println(teb.get());
        teb.left(3);
        StdOut.println(teb.get());
        teb.left(1);
        StdOut.println(teb.get());
        teb.left(1);
        StdOut.println(teb.get());
        teb.left(3);
        StdOut.println(teb.get());
        teb.right(1);
        StdOut.println(teb.get());
        teb.right(4);
        StdOut.println(teb.get());
        teb.right(4);
        StdOut.println(teb.get());
        teb.left(2);
        StdOut.println(teb.get());
        StdOut.println("3. end of test ...");

        StdOut.println("4. test delete() ... ");
        StdOut.println(teb.delete());
        StdOut.println(teb.get());
        teb.left(1);
        StdOut.println(teb.get());
        teb.right(1);
        StdOut.println(teb.get());
        teb.right(1);
        StdOut.println(teb.get());
        teb.left(5);
        StdOut.println(teb.get());

        StdOut.println("4. end of test ...");
    }
}