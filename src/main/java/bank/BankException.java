package bank;

/** Base class for every rule a bank operation can break. */
public class BankException extends Exception {
    public BankException(String message) {
        super(message);
    }
}
