package javasrc.ch05_1;

/*
 * 5.1.12 Alphabet. Develop an implementation of the Alphabet API that is given 
 * on page 698 and use it to develop LSD and MSD sorts for general alphabets.
 * 
 * 
 */

import lib.*;

public class Alphabet {

    private char[] alphabet;
    private int R;
    
    // * constructor, create a new alphabet from chars in s
    public Alphabet(String s){

        boolean[] unicode = new boolean[Character.MAX_VALUE];
        for(int i = 0; i < s.length(); i++){    
            if(unicode[s.charAt(i)]){
                throw new IllegalArgumentException("Duplicated Character!");
            }
            unicode[s.charAt(i)] = true;
        }

        this.alphabet = s.toCharArray();
        this.R = this.alphabet.length;

    }

    // * convert index to corresponding alphabet char
    public char toChar(int index){
        return this.alphabet[index];
    }

    // * convert c to an index between 0 and R–1
    public int toIndex(char c){
        for(int i = 0; i < this.alphabet.length; i++){
            if(this.alphabet[i] == c){
                return i;
            }
        }

        return -1;
    }

    // * is c in the alphabet?
    public boolean contains(char c){
        for(int i = 0; i < this.alphabet.length; i++){
            if(this.alphabet[i] == c){
                return true;
            }
        }
        return false;
    }

    // * radix (number of characters in alphabet)
    public int R(){
        return this.R;
    }

    // * number of bits to represent an index
    public int lgR(){
        int result = 0;

        for(int x = this.R; x >=1; x = x/2){
            result++;
        }

        return result;
    }

    // * convert s to base-R integer
    public int[] toIndices(String s){
        int length = s.length();
        int[] result = new int[length];
        for(int i = 0; i< length; i++){
            result[i] = toIndex(s.charAt(i));
        }
        return result;
    }

    // * convert base-R integer to string over this alphabet
    public String toChars(int[] indices){
        String result = "";
        for(int i = 0; i< indices.length; i++){
            result += toChar(indices[i]);
        }
        return result;
    }

    public static void main(String[] args){
        String a = "ABCDE";
        Alphabet al = new Alphabet(a);
        StdOut.println("R: " + al.R());
        StdOut.println("lgR: " + al.lgR());
        StdOut.println("C to index: " + al.toIndex('C'));
        StdOut.println("3 to char: " + al.toChar(3));
        StdOut.println("contain E ? " + al.contains('E'));
        StdOut.println("contain F ? " + al.contains('F'));
        String b = "BBCABCCDC";
        StdOut.println(b + " to index: ");
        int[] bIndex = al.toIndices(b);
        for(int i =0; i < bIndex.length; i++){
            StdOut.print(bIndex[i] + ", ");
        }
        StdOut.println();
        int[] cIndex = {0, 1, 2 ,3 , 4, 3, 2, 1, 0};
        StdOut.println("String is: " + al.toChars(cIndex));

    }
}