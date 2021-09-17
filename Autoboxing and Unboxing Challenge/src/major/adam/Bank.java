package major.adam;

import java.util.ArrayList;

public class Bank {
    private String name;
    private ArrayList<Branch> branches;

    public Bank(String name) {
        this.name = name;
        this.branches = new ArrayList<Branch>();
    }

    public boolean addBranch(String branchName) {
        if (!validName(branchName)) {
            System.err.println("Invalid branchName in Bank.addBranch()");
            return false;
        }

        branches.add(new Branch(branchName));
        return true;
    }

    public boolean addCustomer (String branchName, String customerName, double initialTransaction){
        if (!validName(branchName) || !validName(customerName) || !validTransaction(initialTransaction)) {
            System.err.println("Invalid argument passed to Bank.addCustomer()");
            return false;
        }

        Branch branchToGet = findBranch(branchName);
        if (branchToGet == null) return false;
        branchToGet.newCustomer(customerName, initialTransaction);

        return true;
    }

    public boolean addCustomerTransaction(String branchName, String customerName, double transaction) {
        if (!validName(branchName) || !validName(customerName) || !validTransaction(transaction)) {
            System.err.println("Invalid argument passed to Bank.addCustomerTransaction()");
            return false;
        }

        Branch branchToGet = findBranch(branchName);
        if (branchToGet == null) return false;

        Customer customerToGet = findCustomer(branchToGet, customerName);
        if (customerToGet == null) return false;

        customerToGet.addTransaction(transaction);
        return true;
    }

    public  boolean listCustomers(String branchName, boolean printTransactions) {
        if (!validName(branchName)) {
            System.err.println("Invalid argument passed to Bank.listCustomers()");
            return false;
        }

        Branch branchToGet = findBranch(branchName);
        if(branchToGet == null) return false;

        for (Customer customer : branchToGet.getCustomers()) {
            System.out.print(customer.getName() + " is part of branch " +  branchToGet.getName() + " with bank " + name);

            if (printTransactions) {
                System.out.println(customer.getName() + "'s transactions: ");
                customer.printTransactions();
            }
        }

        return true;
    }

    private Branch findBranch(String branchName) {
        Branch branchToGet = null;
        for (Branch branch : branches) {
            if (branch.getName() == branchName) {
                branchToGet = branch;
                break;
            }
        }

        return branchToGet;
    }

    private Customer findCustomer(Branch branch, String customerName) {
        Customer customerToGet = null;
        for (Customer customer : branch.getCustomers()) {
            if (customer.getName() == customerName) {
                customerToGet = customer;
                break;
            }
        }

        return customerToGet;
    }

    private boolean validName(String branchName) {
        return branchName != "";
    }

    public static boolean validTransaction (double transaction) {
        return transaction > 0;
    }
}
