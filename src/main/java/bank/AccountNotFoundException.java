package bank;

public class AccountNotFoundException extends BankException {
    public AccountNotFoundException(String accountId) {
        super("No account with id " + accountId + ".");
    }
}
