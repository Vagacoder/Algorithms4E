package javasrc.ch02_1;

/*
2.1.13 Deck sort. 
Explain how you would put a deck of cards in order by suit (in the order spades, 
hearts, clubs, diamonds) and by rank within each suit, with the restriction that 
the cards must be laid out face down in a row, and the only allowed operations 
are to check the values of two cards and to exchange two cards (keeping them face 
down).
*/

import lib.StdOut;
import lib.StdRandom;

public class Decksort {

    public static Card[] generateCardSetNoJoker() {
        Card[] fullset = new Card[52];
        String suit = "";
        for (int i = 0; i < 4; i++) {
            switch (i) {
            case 0:
                suit = "spades";
                break;
            case 1:
                suit = "hearts";
                break;
            case 2:
                suit = "clubs";
                break;
            case 3:
                suit = "diamonds";
                break;
            }
            for (int j = 0; j < 13; j++) {
                fullset[i * 13 + j] = new Card(suit, j + 1);
            }
        }
        return fullset;
    }

    public static void shuffleSet(Card[] set) {
        int N = set.length;
        for (int i = 0; i < N; i++) {
            int r = i + StdRandom.uniform(N - i);
            Card temp = set[i];
            set[i] = set[r];
            set[r] = temp;
        }
    }

    public static void printDeck(Card[] set){
        for (Card card : set) {
            StdOut.println(card.check());
        }
    }

    public static void main(String[] args) {
        Card[] deck = generateCardSetNoJoker();
        StdOut.println("1. new card set ...");
        printDeck(deck);
        StdOut.println("\n2. shuffled card set ...");
        shuffleSet(deck);
        printDeck(deck);
        StdOut.println("\n3. Insertion sort card set ...");
        Insertion.sort(deck);
        printDeck(deck);
    }
}