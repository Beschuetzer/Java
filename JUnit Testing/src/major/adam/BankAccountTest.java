package major.adam;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    private BankAccount account;
    private static int count = 0;

    @org.junit.jupiter.api.BeforeEach
    public void setup () {
        System.out.println("Setting up test... Count = " + count);
        this.account = new BankAccount("Adam", "Minor", 1000, BankAccount.AccountTypes.CHECKING);
    }

    @org.junit.jupiter.api.AfterEach
    public void finish() {
        System.out.println("Tearing down test.... Count = " + count++);
    }

    @org.junit.jupiter.api.BeforeAll
    public static void init() {
        //must be static as it runs before test class is instantiated
        System.out.println("Setting up test suite...");
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDown() {
        //must be static as it runs after test class is finished
        System.out.println("Cleaning up test suite... count = " + count++);
    }

    @org.junit.jupiter.api.Test
    void deposit_add() {
        double balance = this.account.deposit(200, true);
        assertEquals(1200, balance, 0);
        assertEquals(1200, this.account.getBalance(), 0);
    }

    @org.junit.jupiter.api.Test
    void withdraw() {
        double balance = this.account.withdraw(500, false);
        assertEquals(500, balance);
    }

    @org.junit.jupiter.api.Test()
    void withdraw_too_much_from_atm() {
        assertThrows(IllegalArgumentException.class, () -> {
            double balance = this.account.withdraw(501, false);
            fail("should have thrown illegal argument");
        });

        //older version approach
//        try {
//            double balance = this.account.withdraw(501, false);
//            fail("should have thrown illegal argument");
//
//        } catch (IllegalArgumentException e) {
//            //passes by default;
//        }
    }

    @org.junit.jupiter.api.Test
    void getBalance_deposit() {
        this.account.deposit(200, true);
        assertEquals(1200, account.getBalance(), 0);
    }

    @org.junit.jupiter.api.Test
    void getBalance_withdraw() {
        this.account.withdraw(200, true);
        assertEquals(800, this.account.getBalance(), 0);
    }

    @org.junit.jupiter.api.Test
    void isChecking_false() {
        this.account = new BankAccount("Adam", "Minor", 1000, BankAccount.AccountTypes.SAVINGS);
//        assertEquals(true, bankAccount.isChecking());
        assertFalse(this.account.isChecking(), "Account is not checking account");
        assertTrue(this.account.isSavings(), "Account is savings account");
    }

    @org.junit.jupiter.api.Test
    void isChecking_true() {
//        assertEquals(true, bankAccount.isChecking());
        assertTrue(this.account.isChecking(), "Account is not checking account");
        assertFalse(this.account.isSavings(), "Account is savings account");
    }
}