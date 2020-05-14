package javasrc.ch3_5;

import java.io.File;
import javasrc.ch03_4.SeparateChainingHashST;
import lib.*;

/*
* File indexing P.501
*/

public class FileIndex {

    public static void main(String[] args) {
        SeparateChainingHashST<String, SETx<File>> st = new SeparateChainingHashST<>();

        for (String filename : args) {
            File file = new File(filename);
            In in = new In(file);
            while (!in.isEmpty()) {
                String word = in.readString();
                if (st.get(word) == null) {
                    st.put(word, new SETx<File>());
                }
                SETx<File> set = st.get(word);
                set.add(file);
            }
        }

        while (!StdIn.isEmpty()) {
            String query = StdIn.readString();
            SETx<File> files = st.get(query);
            if (files != null) {
                for (File file : files) {
                    StdOut.println("  " + file.getName());
                }
            }
        }
    }

}