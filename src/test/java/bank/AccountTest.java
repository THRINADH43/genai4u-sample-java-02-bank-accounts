package bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountTest {
    private Bank bank;
    private SavingsAccount savings;
    private CurrentAccount current;

    private static BigDecimal rs(String s) {
        return new BigDecimal(s);
    }

    @BeforeEach
    void setUp() throws BankException {
        bank = new Bank();
        savings = new SavingsAccount("S", "Asha");
        current = new CurrentAccount("C", "Shop", rs("500"));
        bank.open(savings);
        bank.open(current);
        savings.deposit(rs("3000"));
    }

    @Test
    void depositAddsExactly() throws BankException {
        savings.deposit(rs("0.10"));
        savings.deposit(rs("0.20"));
        assertEquals(rs("3000.30"), savings.getBalance());
    }

    @Test
    void depositRejectsZeroNegativeAndFractionsOfPaise() {
        assertThrows(InvalidAmountException.class, () -> savings.deposit(rs("0")));
        assertThrows(InvalidAmountException.class, () -> savings.deposit(rs("-5")));
        assertThrows(InvalidAmountException.class, () -> savings.deposit(rs("1.005")));
    }

    @Test
    void savingsKeepsMinimumBalance() throws BankException {
        savings.withdraw(rs("2000"));
        assertEquals(rs("1000.00"), savings.getBalance());
        assertThrows(InsufficientFundsException.class, () -> savings.withdraw(rs("0.01")));
    }

    @Test
    void savingsLimitsWithdrawalsPerMonth() throws BankException {
        savings.withdraw(rs("10"));
        savings.withdraw(rs("10"));
        savings.withdraw(rs("10"));
        assertThrows(BankException.class, () -> savings.withdraw(rs("10")));
        savings.startNewMonth();
        savings.withdraw(rs("10"));
        assertEquals(rs("2960.00"), savings.getBalance());
    }

    @Test
    void currentCanUseOverdraftButNotMore() throws BankException {
        current.withdraw(rs("500"));
        assertEquals(rs("-500.00"), current.getBalance());
        assertThrows(InsufficientFundsException.class, () -> current.withdraw(rs("1")));
    }

    @Test
    void currentAccountOverdraftDefaultsTo5000() throws BankException {
        CurrentAccount shop = new CurrentAccount("C2", "Kumar Stores");
        shop.withdraw(rs("5000"));
        assertEquals(rs("-5000.00"), shop.getBalance());
        assertThrows(InsufficientFundsException.class, () -> shop.withdraw(rs("0.01")));
    }

    @Test
    void transferMovesMoney() throws BankException {
        bank.transfer("S", "C", rs("750.25"));
        assertEquals(rs("2249.75"), savings.getBalance());
        assertEquals(rs("750.25"), current.getBalance());
    }

    @Test
    void failedTransferChangesNothing() {
        assertThrows(InsufficientFundsException.class, () -> bank.transfer("S", "C", rs("2500")));
        assertEquals(rs("3000.00"), savings.getBalance());
        assertEquals(rs("0.00"), current.getBalance());
    }

    @Test
    void transferToUnknownAccount() {
        assertThrows(AccountNotFoundException.class, () -> bank.transfer("S", "X", rs("1")));
        assertEquals(rs("3000.00"), savings.getBalance());
    }

    @Test
    void transferToSameAccount() {
        assertThrows(BankException.class, () -> bank.transfer("S", "S", rs("1")));
    }
}
