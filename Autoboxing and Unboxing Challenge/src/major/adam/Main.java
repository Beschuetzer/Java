package major.adam;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Customer adam = new Customer("adam", 123);
        Branch branch1 = new Branch("branch1");
        Bank bank1 = new Bank("bank1");

        //customer testing
        adam.getTransactions();
        adam.addTransaction(-123);
        adam.addTransaction(124);
        adam.getTransactions();

        //branch testing
        branch1.newCustomer("tom", 10033);
        branch1.getCustomers();
        branch1.newCustomer("t2om", 10033);
        branch1.newCustomer("t2om", 10033);
        branch1.addCustomerTransaction("tom", 333);

        //bank testing
        System.out.println("Bank testing----------------------");
        bank1.addBranch("branch1");
        bank1.addCustomer("branch1", "tom", 110);
        bank1.addCustomer("branch2", "tom", 100);
        bank1.listCustomers("branch1", true);
        bank1.listCustomers("branch1", false);
        bank1.listCustomers("branch2", false);
        ;

        ArrayList<Customer> customers = branch1.getCustomers();
        customers.forEach(customer -> {
            System.out.println(customer.getName() + ": ");
            System.out.println(customer.getTransactions());
        });

    }
}

