package javasrc.ch01_2;

import lib.StdOut;
import lib.StdRandom;
import javasrc.ch01_2.Counter;

public class Rolls{
    public static void main(String[] args){
        int number = Integer.parseInt(args[0]);
        int SIDES = 6;
        Counter[] rolls = new Counter[SIDES + 1];
        for (int i = 0; i<= SIDES; i++){
            rolls[i] = new Counter(i + "'s");
        }

        for (int i = 0; i < number; i++){
            int result = StdRandom.uniform(1, SIDES+1);
            rolls[result].increment();
        }

        for (int i = 1; i <= SIDES; i++){
            StdOut.println(rolls[i]);
        }
    }
}