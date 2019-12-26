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

    // Solution 1: Not using queue, simple tree view ===========================
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
                if (i == level - 1) {
                    leadingSpace += (" " + "\u2514" + "------");
                } else {
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

    // Solution 2: No queue, better tree view ==================================
    public static void listAllFiles2(String pathName) {
        File thisPath = new File(pathName);
        StdOut.println(pathName);
        if (thisPath.isDirectory()) {
            listAllFilesWithIndent1(pathName, "");
        }
    }

    // helper for solution 2
    private static void listAllFilesWithIndent1(String folderName, String leadingString) {
        File thisFolder = new File(folderName);

        String[] fileList = thisFolder.list();
        int listLength = fileList.length;
        for (int i = 0; i < listLength; i++) {
            String fileName = fileList[i];
            if (i != listLength - 1) {
                StdOut.println(leadingString + " \u251C\u2500\u2500\u2500\u2500\u2500 " + fileName);
            } else {
                StdOut.println(leadingString + " \u2514\u2500\u2500\u2500\u2500\u2500 " + fileName);
            }
            File thisFile = new File(folderName + File.separator + fileName);

            if (thisFile.isDirectory()) {
                String newLeadingString = leadingString;
                if (i != listLength - 1) {
                    newLeadingString += " \u2502      ";
                } else {
                    newLeadingString += "\t";
                }
                listAllFilesWithIndent1(folderName + File.separator + fileName, newLeadingString);
            }
        }
    }

    // Solution 3: using queue =================================================
    public static void listAllFiles3(String pathName) {
        File thisPath = new File(pathName);
        if (thisPath.isFile()) {
            StdOut.println(pathName);
            return;
        }

        String[] contentList = thisPath.list();
        LinkedListQueue<String> folders = new LinkedListQueue<>();
        LinkedListQueue<String> files = new LinkedListQueue<>();
        for (String contentName : contentList) {
            File content = new File(contentName);
            if (content.isFile()) {
                files.enqueue(pathName + File.separator + contentName);
            } else if (content.isDirectory()) {
                folders.enqueue(pathName + File.separator + contentName);
            }
        }

    }

    public static void main(String[] args) {

        // StdOut.println(File.pathSeparator);
        // StdOut.println(File.separator);

        // Test for solution 1
        StdOut.println("1. testing solution 1 ...");
        // ListFiles.listAllFiles1("./");
        // ListFiles.listAllFiles1("./README.md");
        // ListFiles.listAllFiles1("./data");
        // ListFiles.listAllFiles1("./javasrc");
        StdOut.println("End of 1 ... \n");

        // Test for solution 2
        StdOut.println("2. testing solution 2 ...");
        ListFiles.listAllFiles2("./");
        // ListFiles.listAllFiles2("./README.md");
        // ListFiles.listAllFiles2("./data");
        // ListFiles.listAllFiles2("./javasrc");
        StdOut.println("End of 2 ... \n");

    }
}