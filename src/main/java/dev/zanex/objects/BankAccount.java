package dev.zanex.objects;

import dev.zanex.program.Main;
import dev.zanex.types.BankAccountType;

import java.util.List;

public class BankAccount {
    private final String accountNumber;
    private final String bankCode;
    private final AccountHolder accountHolder;
    private final BankAccountType accountType;
    private double balance;
    private double overdraftLimit;
    private double accountFees;

    public BankAccount(String accountNumber, String firstName, String lastName, BankAccountType accountType, String bankCode, double overdraftLimit, double accountFees) {
        this.accountNumber = accountNumber;
        this.accountHolder = new AccountHolder(firstName, lastName);
        this.accountType = accountType;
        this.bankCode = bankCode;
        this.overdraftLimit = overdraftLimit;
        this.accountFees = accountFees;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public BankAccountType getAccountType() {
        return accountType;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public double getAccountFees() {
        return accountFees;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && (balance + overdraftLimit) >= amount) {
            balance -= amount;
        } else {
            System.out.println("Invalid withdraw amount or insufficient funds.");
        }
    }

    public void printStatement() {
        List<String> output = List.of(
                "&7Account Holder: &f" + accountHolder.getFirstName() + " " + accountHolder.getLastName(),
                "&7Account Number: &f" + accountNumber,
                "&7Bank Code: &f" + bankCode,
                "&7Account Type: &f" + accountType,
                "&7Balance: &f" + balance,
                "&7Overdraft Limit: &f" + overdraftLimit,
                "&7Account Fees: &f" + accountFees
        );

         Main.getOutputHandler().printTable("Account Statement", output);
    }
}