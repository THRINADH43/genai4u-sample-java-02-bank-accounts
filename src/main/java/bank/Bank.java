package bank;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Account> accounts = new HashMap<>();

    public void open(Account account) {
        if (accounts.containsKey(account.getId())) {
            throw new IllegalArgumentException("Account " + account.getId() + " already exists.");
        }
        accounts.put(account.getId(), account);
    }

    public Account find(String id) throws AccountNotFoundException {
        Account a = accounts.get(id);
        if (a == null) {
            throw new AccountNotFoundException(id);
        }
        return a;
    }

    /** Moves money between two accounts. If the withdrawal fails, nothing changes. */
    public void transfer(String fromId, String toId, BigDecimal amount) throws BankException {
        if (fromId.equals(toId)) {
            throw new BankException("Cannot transfer to the same account.");
        }
        Account from = find(fromId);
        Account to = find(toId);
        from.withdraw(amount);  // throws before anything changes if not allowed
        to.deposit(amount);
    }
}
