package major.adam;

public class BankAccount {
    private String firstName;
    private String lastName;
    private double balance;

    public enum AccountTypes {
        CHECKING,
        SAVINGS,
    }

    private AccountTypes accountType;

    public BankAccount(String firstName, String lastName, double balance, AccountTypes accountType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.accountType = accountType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getBalance() {
        return balance;
    }

    public AccountTypes getAccountType() {
        return accountType;
    }

    public double deposit(double amount, boolean isBranch) {
        balance += amount;
        return balance;
    }

    public double withdraw(double amount, boolean isBranch) {
        if (amount > 500 && !isBranch) throw new IllegalArgumentException("Cannot withdraw more than 500 from an atm.");
        balance -= amount;
        return balance;
    }

    public boolean isChecking() {
        return accountType == AccountTypes.CHECKING;
    }

    public boolean isSavings() {
        return accountType == AccountTypes.SAVINGS;
    }

}
