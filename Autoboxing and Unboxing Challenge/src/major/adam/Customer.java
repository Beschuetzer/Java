package major.adam;


import java.util.ArrayList;

public class Customer {
    private String name;
    private ArrayList<Double> transactions;

    public Customer(String name, double initialTransaction) {
        this.name = name;
        this.transactions = new ArrayList<Double>();
        this.transactions.add(Double.valueOf(initialTransaction));
    }

    public String getName() {
        return name;
    }

    public ArrayList<Double> getTransactions() {
        System.out.println("");
        for (Double transaction: transactions
             ) {
            System.out.println("transaction: " + transaction);
        }
        return transactions;
    }

    public void printTransactions() {
        for (Double transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public void addTransaction(double transaction)  {
        if (transaction <= 0) {
            System.err.println("Transactions must be larger than 0");
            return;
        }
        this.transactions.add(transaction);
    }
}
