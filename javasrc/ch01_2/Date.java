package javasrc.ch01_2;

public class Date {

    private final int month;
    private final int day;
    private final int year;

    public Date(int m, int d, int y) {
        this.month = m;
        this.day = d;
        this.year = y;
    }

    public int month() {
        return this.month;
    }

    public int day() {
        return this.day;
    }

    public int year() {
        return this.year;
    }

    // Override toString() from Object class
    public String toString() {
        return month() + "/" + day() + "/" + year();
    }

    // Override operator "="
    public boolean equals(Object x) {
        if (this == x)
            return true; // 1. same reference
        if (x == null)
            return false; // 2. target is null
        if (this.getClass() != x.getClass())
            return false; // 3. not same class
        Date that = (Date) x; // cast to this class, step 3 guarantee it will be successful
        if (this.day != that.day)
            return false; // 4. compare instance variables
        if (this.month != that.month)
            return false;
        if (this.year != that.year)
            return false;
        return true; // pass all 4 tests above
    }
}