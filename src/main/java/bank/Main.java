package bank;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        SavingsAccount priya = new SavingsAccount("S-1", "Priya");
        CurrentAccount shop = new CurrentAccount("C-1", "Kumar Stores", new BigDecimal("5000"));
        bank.open(priya);
        bank.open(shop);
        try {
            priya.deposit(new BigDecimal("2500.50"));
            bank.transfer("S-1", "C-1", new BigDecimal("1000"));
            shop.withdraw(new BigDecimal("4000"));
            System.out.println("Priya: " + priya.getBalance() + ", Kumar Stores: " + shop.getBalance());
            bank.transfer("S-1", "C-1", new BigDecimal("600"));
        } catch (BankException e) {
            System.out.println("Refused: " + e.getMessage());
        }
    }
}
