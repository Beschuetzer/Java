package major.adam;

import java.util.ArrayList;

public class Branch {
    private String name;
    private ArrayList<Customer> customers;

    public Branch(String name) {
        this.name = name;
        customers = new ArrayList<Customer>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Customer> getCustomers() {
        System.out.println("");
        String format = "Customer name: %s";

        for (Customer customer: customers
             ) {
            System.out.println(String.format(format, customer.getName()));
        }
        return customers;
    }

    public boolean newCustomer(String customerName, double initialTransaction) {
        if (!validName(customerName) || !validTransaction(initialTransaction)) {
            System.err.println("Invalid parameter in newCustomer()");
            return false;
        }

        for(Customer customer : customers) {
            if (customer.getName() == customerName) {
                return false;
            }
        }

        customers.add(new Customer(customerName, initialTransaction));
        return true;
    }

    public boolean addCustomerTransaction (String customerName, double transaction) {
        if (!validName(customerName) || !validTransaction(transaction)) {
            System.err.println("Invalid parameter in addCustomerTransaction()");
            return false;
        }

        Customer customerToFind = findCustomer(customerName);
        if (customerToFind == null) return false;

        customerToFind.addTransaction(transaction);
        return true;
    }

    private Customer findCustomer(String customerName) {
        Customer customerToFind = null;
        for(Customer customer : customers) {
            if (customer.getName() == customerName) {
                customerToFind = customer;
            }
        }

        return customerToFind;
    }

    public static boolean validTransaction (double transaction) {
        return transaction > 0;
    }

    public static boolean validName(String customerName) {
        return customerName != "";
    }
}
