package bank;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Common behaviour for all accounts. Subclasses decide how much may be withdrawn.
 * The balance is private and only changes through deposit and withdraw.
 */
public abstract class Account {
    private final String id;
    private final String owner;
    private BigDecimal balance = BigDecimal.ZERO.setScale(2);

    protected Account(String id, String owner) {
        this.id = id;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    protected static BigDecimal checkAmount(BigDecimal amount) throws InvalidAmountException {
        if (amount == null || amount.signum() <= 0) {
            throw new InvalidAmountException("Amount must be more than zero.");
        }
        if (amount.scale() > 2 && amount.stripTrailingZeros().scale() > 2) {
            throw new InvalidAmountException("Amount cannot have more than two decimal places (paise).");
        }
        return amount.setScale(2, RoundingMode.UNNECESSARY);
    }

    public void deposit(BigDecimal amount) throws InvalidAmountException {
        balance = balance.add(checkAmount(amount));
    }

    public void withdraw(BigDecimal amount) throws BankException {
        BigDecimal value = checkAmount(amount);
        BigDecimal available = availableToWithdraw();
        if (value.compareTo(available) > 0) {
            throw new InsufficientFundsException(id, value, available);
        }
        balance = balance.subtract(value);
    }

    /** How much can be withdrawn right now under this account's rules. */
    public abstract BigDecimal availableToWithdraw();
}
