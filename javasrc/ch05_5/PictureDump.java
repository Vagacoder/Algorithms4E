package javasrc.ch05_5;


/*
* Picture Dump. 
* 
*/

import java.awt.Color;
import lib.*;

public class PictureDump {
    
    private PictureDump(){}

    public static void main(String[] args){

        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);

        Picture picture = new Picture(width, height);
        for (int row = 0; row < height; row++){
            for (int col = 0; col < width; col++){
                if (!BinaryStdIn.isEmpty()){
                    boolean bit = BinaryStdIn.readBoolean();
                    if (bit){
                        picture.set(col, row, Color.BLACK);
                    } else {
                        picture.set(col, row, Color.WHITE);
                    }
                } else {
                    picture.set(col, row, Color.RED);
                }
            }
        }
        picture.show();
    }
}
