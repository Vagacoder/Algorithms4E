package javasrc.ch02_1;

/*
This Card class is for card sorting in ex 2.1.13, 2.1.14, 2.1.23.

*/

import lib.StdOut;

enum Suit {
    SPADES, HEARTS, CLUBS, DIAMONDS
}

public class Card implements Comparable<Card> {
    private Suit suit;
    private int number;

    // Parameter String suit: "spades", "hearts", "clubs" or "diamonds"
    // Parameter int number: 1 (A) to 10, 11(J), 12(Q), 13(K).
    public Card(String suit, int number) {
        if (suit.equals("spades")) {
            this.suit = Suit.SPADES;
        } else if (suit.equals("hearts")) {
            this.suit = Suit.HEARTS;
        } else if (suit.equals("clubs")) {
            this.suit = Suit.CLUBS;
        } else if (suit.equals("diamonds")) {
            this.suit = Suit.DIAMONDS;
        } else {
            throw new IllegalArgumentException();
        }
        this.number = number;
    }

    public String check() {
        return (suit.toString().substring(0, 1) + number);
    }

    @Override
    public int compareTo(Card that) {
        int thisSuit = convertSuitToInt(this.suit);
        int thatSuit = convertSuitToInt(that.suit);
        if (thisSuit < thatSuit) {
            return -1;
        } else if (thisSuit > thatSuit) {
            return 1;
        } else {
            if (this.number < that.number) {
                return -1;
            } else if (this.number > that.number) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private int convertSuitToInt(Suit a) {
        if (a.equals(Suit.SPADES)) {
            return 0;
        } else if (a.equals(Suit.HEARTS)) {
            return 1;
        } else if (a.equals(Suit.CLUBS)) {
            return 2;
        } else {
            return 3;
        }
    }

    public static void main(String[] args) {
        Card c1 = new Card("spade", 1);
        StdOut.println(c1.check());
    }

}