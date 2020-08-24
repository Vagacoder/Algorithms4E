package javasrc.ch05_1;

/*
 * 5.1.12 Alphabet. Develop an implementation of the Alphabet API that is given 
 * on page 698 and use it to develop LSD and MSD sorts for general alphabets.
 * 
 * 
 */

public class Alphabet {

    private String alphabet;
    
    // * constructor, create a new alphabet from chars in s
    public Alphabet(String s){

    }

    // * convert index to corresponding alphabet char
    public char toChar(int index){
        return 0;
    }

    // * convert c to an index between 0 and R–1
    public int toIndex(char c){
        return -1;
    }

    // * is c in the alphabet?
    public boolean contains(char c){
        return false;
    }

    // * radix (number of characters in alphabet)
    public int R(){
        return -1;
    }

    // * number of bits to represent an index
    public int lgR(){
        return -1;
    }

    // * convert s to base-R integer
    public int[] toIndices(String s){
        return null;
    }

    // * convert base-R integer to string over this alphabet
    public String toChars(int[] indices){
        return "";
    }

    public static void main(String[] args){
        
    }
}