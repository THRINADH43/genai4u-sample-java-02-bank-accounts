package bank;

import java.math.BigDecimal;

public class InsufficientFundsException extends BankException {
    public InsufficientFundsException(String accountId, BigDecimal requested, BigDecimal available) {
        super("Account " + accountId + " cannot pay " + requested + "; it can pay at most " + available + ".");
    }
}
