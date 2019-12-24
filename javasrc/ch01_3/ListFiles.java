package javasrc.ch01_3;

/*
1.3.43 Listing files. 

A folder is a list of files and folders. Write a program that takes the name of 
a folder as a command-line argument and prints out all of the files contained
in that folder, with the contents of each folder recursively listed (indented) 
under that folder’s name. 

Hint : Use a queue, and see java.io.File.
*/

import lib.StdOut;
import java.io.File;

public class ListFiles {

    // Solution 1: Not using queue
    public static void listAllFiles1(String pathName) {
        File thisPath = new File(pathName);
        if (thisPath.isFile()) {
            StdOut.println(pathName);
        } else {
            listAllFilesWithIndent(pathName, 0);
        }
    }

    // helper for solution 1
    private static void listAllFilesWithIndent(String folderName, int level) {
        File thisFolder = new File(folderName);

        String[] fileList = thisFolder.list();
        for (String fileName : fileList) {
            String leadingSpace = "";
            for (int i = 0; i < level; i++) {
                if ( i == level -1){
                    leadingSpace += (" " + "\u2514" + "------");
                }else {
                leadingSpace += "\t";
                }
            }
            StdOut.println(leadingSpace + fileName);
            File thisFile = new File(folderName + File.separator + fileName);
            if (thisFile.isDirectory()) {
                listAllFilesWithIndent(folderName + File.separator + fileName, level + 1);
            }
        }
    }

    // Solution 2: using queue
    public static void listAllFiles2(String pathName) {

    }

    public static void main(String[] args) {

        // StdOut.println(File.pathSeparator);
        // StdOut.println(File.separator);

        // Test for solution 1
        StdOut.println("1. testing solution 1 ...");
        ListFiles.listAllFiles1("./");
        // ListFiles.listAllFiles1("./README.md");
        // ListFiles.listAllFiles("./data");
        // ListFiles.listAllFiles("./javasrc");
        StdOut.println("End of 1 ... \n");

    }
}