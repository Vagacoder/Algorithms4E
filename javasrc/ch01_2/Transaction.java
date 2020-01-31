package javasrc.ch01_2;

/*
1.2.13 Using our implementation of Date as a model p ( age 91), develop an implementation 
 of Transaction.

2.1.21 Comparable transactions. Using our code for Date (page 247) as a model, ex-
pand your implementation of Transaction (Exercise 1.2.13) so that it implements
Comparable , such that transactions are kept in order by amount.
*/

import lib.StdOut;

public class Transaction implements Comparable<Transaction>{

    private final int amountInCent;
    private Date transDate;
    private String acctFrom;
    private String acctTo;

    public Transaction(String date){
        this.amountInCent = 0;
        this.acctFrom = "";
        this.acctTo = "";
        this.transDate = new Date(date);
    }

    public Transaction(int amountInCent, String date, String acctFrom, String acctTo){
        this.amountInCent = amountInCent;
        this.acctFrom = acctFrom;
        this.acctTo = acctTo;
        this.transDate = new Date(date);
    }

    public String toString(){
        int dollar = this.amountInCent /100;
        int cent = this.amountInCent %100;
        String centString = "" + cent;
        if(cent<10){
            centString = "0" + centString;
        }
        return ("From: " + this.acctFrom + " transfer $" + dollar + "." + centString + 
        " to: " + this.acctTo + " at " + this.transDate);
    }

    @Override
    public int compareTo(Transaction that) {
        return Integer.compare(this.amountInCent, that.amountInCent);
    }

}