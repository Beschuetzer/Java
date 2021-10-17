package major.adam;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Stream;


public class BankAccountTestParameterized {
    private BankAccount account;
    private double amount;
    private double expected;
    private boolean isBranch;

    @BeforeEach
    public void Setup() {
        this.account = new BankAccount("Adam", "Minor", 1000.00, BankAccount.AccountTypes.CHECKING);
    }

    @ParameterizedTest
    //using a value as the source
    @ValueSource(strings =
            { "racecar", "radar", "able was I ere I saw elba" }
    )
    @org.junit.jupiter.api.Test
    void palindromes(String candidate) {
        assertTrue(candidate.toUpperCase(Locale.ROOT).equalsIgnoreCase("racecar"));
    }

    @ParameterizedTest
    @MethodSource
    void deposit_valid(double amount, boolean isBranch, double expected) {
        double balance = this.account.deposit(amount, isBranch);
        assertEquals(expected, balance, .001);
    }

    //this method is sources as the source for deposit_valid
    public static Stream<Arguments> deposit_valid() {
        return Stream.of(
                //amount to deposit, isBranch, and final balance
                Arguments.of(100.00, true, 1100.00),
                Arguments.of(200.00, true, 1200.00),
                Arguments.of(325.14, true, 1325.14),
                Arguments.of(489.33, true, 1489.33),
                Arguments.of(1000.00, true, 2000.00)
        );
    }
}

