package javasrc.ch01_2;

// This is for excise 1.2.13

public class SimpleTransaction {

    private final String who;
    private final Date when;
    private final double amount;

    public SimpleTransaction(String who, Date when, double amount) {
        this.who = who;
        this.when = when;
        this.amount = amount;
    }

    public String who() {
        return this.who;
    }

    public Date when() {
        return this.when;
    }

    public double amount() {
        return this.amount;
    }

    public String toString() {
        return who() + ", " + when() + ", $" + amount();
    }

    public boolean equals(Object x) {
        if (this == x) {
            return true;
        }
        if (x == null) {
            return false;
        }
        if (this.getClass() != x.getClass()) {
            return false;
        }
        SimpleTransaction that = (SimpleTransaction) x;
        if (this.who != that.who)
            return false;
        if (this.when != that.when)
            return false;
        if (this.amount != that.amount)
            return false;
        return true;
    }
}
