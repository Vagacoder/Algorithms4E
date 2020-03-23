package javasrc.ch02_5;

/*
* 2.5.8 Write a program Frequency that reads strings from standard input and prints
the number of times each string occurs, in descending order of frequency.

*/

import javasrc.ch02_2.Merge;
import lib.*;

public class Frequency{

    class Word implements Comparable<Word>{
        String word;
        int freq;

        public Word(String word, int freq){
            this.word = word;
            this.freq = freq;
        }

        @Override
        public int compareTo(Word that) {
            return this.freq - that.freq ;
        }
    }

    private Word[] words;
    private int size;

    public Frequency(int capacity){
        this.words = new Word[capacity];
        this.size = 0;
    }

    public void add(String str){
        for (int i = 0; i < this.size; i++){
            if(this.words[i].word.equals(str)){
                this.words[i].freq++;
                return;
            }
        }
        this.words[size++] = new Word(str, 1);
    }

    public void printDescendFreq(){
        Word[] temp = this.sort();
        for(int i = this.size-1; i>=0; i--){
            StdOut.println(temp[i].word + ": " + temp[i].freq);
        }
    }
    
    private Word[] sort(){

        Word[] temp = new Word[this.size];
        
        for(int i = 0; i< this.size; i++){
            temp[i] = new Word(this.words[i].word, this.words[i].freq);
        }

        Merge.sort(temp);
        return temp;
    }



    public static void main(String[] args){
        Frequency freq = new Frequency(30);
        StdOut.println("1. test ...");
        String[] a = {"the", "like", "good", "this", "bad", "thing", 
            "happen", "good", "or", "this", "that", 
            "love", "to", "the", "things", "we",
            "do", "think", "this", "will","happen"
        };
        for(int i =0; i < a.length; i++){
            freq.add(a[i]);
        }
        freq.printDescendFreq();
    }
}