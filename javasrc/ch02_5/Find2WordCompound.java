package javasrc.ch02_5;

/*
* 2.5.2 Write a program that reads a list of words from standard input and prints 
all two-word compound words in the list. 

For example, if "after", "thought", and "afterthought" are in the list, then 
"afterthought" is a compound word.

*/

import java.util.Arrays;
import java.util.ArrayList;
import lib.*;

public class Find2WordCompound {

    private String[] words;
    private int size;

    public Find2WordCompound(int capacity) {
        this.words = new String[capacity];
        this.size = 0;
    }

    public Find2WordCompound(String[] arr) {
        this.words = arr.clone();
        this.size = words.length;
        Arrays.sort(words, 0, size);
    }

    public void add(String newWord) {
        this.words[this.size++] = newWord;
        Arrays.sort(words, 0, size);
    }

    public ArrayList<String> findAll2WordCompound() {
        ArrayList<String> result = new ArrayList<>();
        int cur = 0;
        while (cur < this.size - 1) {
            String thisString = this.words[cur];
            String nextString = this.words[cur + 1];

            if (nextString.startsWith(thisString)) {
                String secondCompound = nextString.substring(thisString.length(), nextString.length());
                if (secondCompound.length() != 0) {
                    for (int i = 0; i < this.words.length; i++) {
                        if (this.words[i].equals(secondCompound)) {
                            result.add(nextString);
                            break;
                        }
                    }
                }
            } 
            cur++;
        }
        return result;
    }

    public static void check() {
        StdOut.println("1. Testing 3 words ... ");
        StdOut.println("1.1. Testing after thought afterthought... ");
        String[] words = { "after", "thought", "afterthought" };
        Find2WordCompound f2 = new Find2WordCompound(10);
        for (String str : words) {
            f2.add(str);
        }
        ArrayList<String> result = f2.findAll2WordCompound();
        for (String str : result) {
            StdOut.println(str);
        }

        StdOut.println("\n1.2. Testing thought after afterthought... ");
        String[] words1 = { "thought", "after", "afterthought" };
        f2 = new Find2WordCompound(words1);
        result = f2.findAll2WordCompound();
        for (String str : result) {
            StdOut.println(str);
        }

        StdOut.println("\n1.3. Testing afterthought thought after ... ");
        String[] words2 = { "afterthought", "thought", "after" };
        f2 = new Find2WordCompound(words2);
        result = f2.findAll2WordCompound();
        for (String str : result) {
            StdOut.println(str);
        }

        StdOut.println("\n2. Testing multiple words ... ");
        StdOut.println("2.1. Testing ... ");
        StdOut.println("Expect to have: ");
        StdOut.println("anybody, become, cannot diskdrive, eyeglass");
        StdOut.println("Expect Not to have: ");
        StdOut.println("another, baseball, crosswalk, duckpin, eyewitness");
        String[] words3 = { 
            "anybody", "any", "body",
            "an", "other", 
            "ball", "baseball",
            "be", "come", "become", 
            "crosswalk", "cross", 
            "can", "not", "cannot",
            "disk", "diskdrive", "drive",
            "duck", "pin",
            "glass", "eye","eyeglass",
            "witness", "eyewitness"
        };
        int repeat = 10;
        StdOut.println("Repeat " + repeat + " times");
        for (int i = 0; i < repeat; i++){
            StdOut.println("#" + (i+1));
            StdRandom.shuffle(words3);
            f2 = new Find2WordCompound(words3);
            result = f2.findAll2WordCompound();
            for (String str : result) {
                StdOut.println(str);
            }
            StdOut.println();
        }
    }

    public static void main(String[] args) {
        check();
    }
}