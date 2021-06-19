package javasrc.ch05_5;

/*
 * Hex Dump. 
 * 
 */

import lib.*;

public class HexDump {
    
    private HexDump (){}

    public static void main(String[] args){
        int bytesPerLine;
        if (args.length > 0){
            bytesPerLine = Integer.parseInt(args[0]);
        } else {
            bytesPerLine = 16;
        }

        int i;
        for (i = 0; !BinaryStdIn.isEmpty(); i++){
            if (bytesPerLine == 0) {
                BinaryStdIn.readChar();
                continue;
            }
            if (i == 0) {
                StdOut.printf("");
            } else if ( i% bytesPerLine == 0){
                StdOut.printf("\n", i);
            } else {
                StdOut.print(" ");
            }
            char c = BinaryStdIn.readChar();
            StdOut.printf("%02x", c & 0xff);
        }
        if (bytesPerLine !=0){
            StdOut.println();
        }
        StdOut.println((i*8)+" bits");
    }
}
