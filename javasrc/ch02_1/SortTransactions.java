package javasrc.ch02_1;

/*
2.1.22 Transaction sort test client. Write a class SortTransactions that consists of a
static method main() that reads a sequence of transactions from standard input, sorts
them, and prints the result on standard output (see Exercise 1.3.17).
*/
import java.util.ArrayList;
import javasrc.ch01_2.Transaction;
import lib.StdOut;
import lib.StdIn;

public class SortTransactions{

    public static Transaction[] readTransactions(){
        ArrayList<Transaction> result = new ArrayList<>();
        while(!StdIn.isEmpty()){
            String line = StdIn.readLine();
            String[] tran = line.split(",");
            int amountInCent = Integer.parseInt(tran[0]);
            result.add(new Transaction(amountInCent,tran[3], tran[1], tran[2]));
        }
        return result.toArray(new Transaction[0]);
    }
    public static void main(String[] args){
        Transaction[] trans = readTransactions();
        Shell.sort(trans);
        for (Transaction tran: trans){
            StdOut.println(tran);
        }

    }
}