package javasrc.ch05_5;

/*
* Binary Dump. P. 814 
* 
*/

import lib.*;

public class BinaryDump {

    private BinaryDump() {}

    public static void main(String[] args){
        int width;
        if (args.length > 0){
            width = Integer.parseInt(args[0]);
        }else {
            width = 10;
        }
        int count;
        for (count = 0; !BinaryStdIn.isEmpty(); count++){
            if (width == 0){
                continue;
            }
            if (count != 0 && count % width == 0){
                StdOut.println();
            }
            if (BinaryStdIn.readBoolean()){
                StdOut.print("1");
            }else {
                StdOut.print("0");
            }
        }
        StdOut.println();
        StdOut.println(count + " bits");
    }
}
