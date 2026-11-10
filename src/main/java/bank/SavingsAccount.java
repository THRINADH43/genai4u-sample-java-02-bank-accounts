package bank;

import java.math.BigDecimal;

/** A savings account must keep a minimum balance and allows a few withdrawals a month. */
public class SavingsAccount extends Account {
    public static final BigDecimal MINIMUM_BALANCE = new BigDecimal("1000.00");
    public static final int FREE_WITHDRAWALS_PER_MONTH = 3;

    private int withdrawalsThisMonth = 0;

    public SavingsAccount(String id, String owner) {
        super(id, owner);
    }

    @Override
    public BigDecimal availableToWithdraw() {
        BigDecimal free = getBalance().subtract(MINIMUM_BALANCE);
        return free.signum() > 0 ? free : BigDecimal.ZERO.setScale(2);
    }

    @Override
    public void withdraw(BigDecimal amount) throws BankException {
        if (withdrawalsThisMonth >= FREE_WITHDRAWALS_PER_MONTH) {
            throw new BankException("Savings account " + getId() + " has used its "
                    + FREE_WITHDRAWALS_PER_MONTH + " withdrawals for this month.");
        }
        super.withdraw(amount);
        withdrawalsThisMonth++;
    }

    public void startNewMonth() {
        withdrawalsThisMonth = 0;
    }
}
