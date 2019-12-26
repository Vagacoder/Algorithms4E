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


public class TextEditorBuffer{

    LinkedListStack<Character> buffer = new LinkedListStack<>();
    LinkedListStack<Character> temp = new LinkedListStack<>();
    // cursor:  1) cursor ranges from 0 to buffer.size(), both are inclusive;
    //          2) when curosr = buffer.size(), it points to a null;
    //          3) insert is always before cursor;
    //          4) delete does nothing when cursor = buffer.size();
    int cursor;    

    public TextEditorBuffer(){
        this.cursor = 0;
    }

    public int size(){
        return buffer.size();
    }

    // insert to BEFORE the cursor
    public void insert(char c){
        this.buffer.push(c);
    }

    public static void main(String[] args){

    }
}