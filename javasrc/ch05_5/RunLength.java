package javasrc.ch05_5;

/*
 * Run Length. P.824
 * javac -cp "./:./lib/algs4.jar" javasrc/ch05_5/RunLength.java 
 * java -cp "./:./lib/algs4.jar" javasrc/ch05_5/BinaryDump 32 < data/q32x48.bin 
 * java -cp "./:./lib/algs4.jar" javasrc/ch05_5/BinaryDump 40 < data/4runs.bin
 * java -cp "./:./lib/algs4.jar" javasrc/ch05_5/RunLength - < data/4runs.bin | java -cp "./:./lib/algs4.jar" javasrc/ch05_5/HexDump
 * java -cp "./:./lib/algs4.jar" javasrc/ch05_5/BinaryDump 0 < data/q64x96.bin 
 * java -cp "./:./lib/algs4.jar" javasrc/ch05_5/RunLength - < data/q64x96.bin | java -cp "./:./lib/algs4.jar" javasrc/ch05_5/BinaryDump 0
 */

import lib.*;

public class RunLength {
    
    private RunLength () {}

    public static void expand() {
        boolean b = false;

        while(!BinaryStdIn.isEmpty()){
            char cnt = BinaryStdIn.readChar();
            for (int i = 0; i < cnt; i++){
                BinaryStdOut.write(b);
            }
            b = !b;
        }

        BinaryStdOut.close();
    }

    public static void compress(){
        char cnt = 0;
        boolean b = false;
        boolean old = false;

        while (!BinaryStdIn.isEmpty()){
            b = BinaryStdIn.readBoolean();
            if (b != old){
                BinaryStdOut.write(cnt, 8);
                cnt = 0;
                old = !old;
            } else {
                if (cnt == 255){
                    BinaryStdOut.write(cnt, 8);
                    cnt = 0;
                    BinaryStdOut.write(cnt, 8);
                }
            }
            cnt ++;
        }

        BinaryStdOut.write(cnt);
        BinaryStdOut.close();
    }

    public static void main(String[] args){
        if (args[0].equals("-")){
            compress();
        } else if (args[0].equals("+")){
            compress();
        } else {
            StdOut.println("Wrong parameter");
        }
    }
}
