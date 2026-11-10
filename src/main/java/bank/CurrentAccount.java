package bank;

import java.math.BigDecimal;

/** A current account may go below zero, down to its overdraft limit. */
public class CurrentAccount extends Account {
    public static final BigDecimal DEFAULT_OVERDRAFT = new BigDecimal("5000.00");

    private final BigDecimal overdraftLimit;

    public CurrentAccount(String id, String owner) {
        this(id, owner, DEFAULT_OVERDRAFT);
    }

    public CurrentAccount(String id, String owner, BigDecimal overdraftLimit) {
        super(id, owner);
        if (overdraftLimit.signum() < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative.");
        }
        this.overdraftLimit = overdraftLimit.setScale(2);
    }

    @Override
    public BigDecimal availableToWithdraw() {
        return getBalance().add(overdraftLimit);
    }
}
