# Bank account system

Bank accounts modelled with object-oriented Java: savings and current accounts with their
own rules, deposits, withdrawals and transfers, custom exceptions, and `BigDecimal` for money.

## Build, test and run

You need Java 17+ and Maven.

```
mvn test
mvn compile
java -cp target/classes bank.BankDemo
```

## Design

- `Account` is abstract. It keeps the balance private and does deposits and withdrawals.
  Each subclass answers one question, `availableToWithdraw()`, so the rules live in one place.
- `SavingsAccount` keeps a minimum balance of Rs 1,000 and allows 3 withdrawals a month.
- `CurrentAccount` may go below zero down to its overdraft limit: Rs 5,000 by default, or another
  limit given to the constructor.
- `Bank` holds the accounts and does transfers. It withdraws first, so a refused transfer
  changes nothing.

## Rules and exceptions

| Exception                    | When |
|------------------------------|------|
| `InvalidAmountException`     | amount is zero, negative, or has more than 2 decimal places |
| `InsufficientFundsException` | a withdrawal or transfer is above what the account allows |
| `AccountNotFoundException`   | an unknown account id |
| `BankException`              | the base class; also monthly withdrawal limit and same-account transfer |

Money is always `BigDecimal` with 2 decimal places, never `double`, so 0.10 + 0.20 is
exactly 0.30 (there is a test for this).

## Tests

`src/test/java/bank/AccountTest.java` has 10 JUnit 5 tests covering normal deposits,
withdrawals and transfers and the failing cases above.
