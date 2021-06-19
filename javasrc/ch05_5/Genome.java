package javasrc.ch05_5;

import javasrc.ch05_1.Alphabet;

/*
 * Genome, P.820 
 * 
 * example: 
 */

import lib.*;

public class Genome {

    private Genome() {}

    // * Read a sequence of 8-bit extended ASCII characters over the alphabet
    // * {A,C,G,T} from standard input; compress them to 2-bit/char; then write
    // * to standard output
    public static void compress() {
        Alphabet DNA = new Alphabet("ACTG");
        String s = BinaryStdIn.readString();
        int N = s.length();
        BinaryStdOut.write(N);

        for (int i = 0; i < N; i++){
            int d = DNA.toIndex(s.charAt(i));
            BinaryStdOut.write(d, DNA.lgR());
        }

        BinaryStdOut.close();
    }

    // * Read a binary sequence from standard input; convert each 2-bit to 8-bit
    // * extended ASCII cha over alphabet {A,C,G,T} and write the result to stdout
    public static void expand() {
        Alphabet DNA = new Alphabet("ACTG");
        int w = DNA.lgR();
        int N = BinaryStdIn.readInt();

        for(int i = 0; i < N; i++){
            char c = BinaryStdIn.readChar(w);
            BinaryStdOut.write(DNA.toChar(c), 8);
        }

        BinaryStdOut.close();
    }

    public static void main(String[] args){
        if (args[0].equals("-")) {
            compress();
        }
        if (args[0].equals("+")) {
            expand();
        }
    }
}
