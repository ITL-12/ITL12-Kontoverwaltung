package dev.zanex.objects;

import dev.zanex.program.Main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreditAccount {
    private final BankAccount holder;
    private double amount;
    private final LocalDate dueDate;
    private double fee = 5;

    public CreditAccount(BankAccount holder, double amount, LocalDate dueDate) {
        this.holder = holder;
        this.amount = amount;
        this.dueDate = dueDate;

        Main.getCreditAccountHandler().createCreditAccount(this);

        holder.deposit(amount);
        Main.getOutputHandler().println("&7[&bCREDIT&7] &aYou have received your credit of &e€ " + amount + "&a.");
    }

    public void addOverdriveFee() {
        fee *= 1.2;
        amount += fee;
    }

    public BankAccount getHolder() {
        return holder;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.amount -= amount;

            if(amount > this.amount) {
                amount -= this.amount;
                Main.getOutputHandler().println("&7[&bCREDIT&7] &aYou have successfully paid back your credit.");
            } else {
                Main.getOutputHandler().println("&7[&bCREDIT&7] &aYou have paid back &e€ " + amount + "&a.");
            }

            holder.withdraw(amount);
        } else {
            Main.getOutputHandler().println("&7[&bCREDIT&7] &cInvalid deposit amount.");
        }
    }

    public void printStatement() {
        Main.getOutputHandler().println("&7[&bCREDIT&7] &aCredit Account Statement");
        Main.getOutputHandler().println("&7[&bCREDIT&7] &aHolder: &e" + holder.getAccountHolder().getFirstName() + " " + holder.getAccountHolder().getLastName());
        Main.getOutputHandler().println("&7[&bCREDIT&7] &aAmount: &e€ " + amount);
        Main.getOutputHandler().println("&7[&bCREDIT&7] &aDue Date: &e" + dueDate);
    }

    public String getStatement() {
        List<String> statements = List.of(
                "Holder: " + holder.getAccountHolder().getFirstName() + " " + holder.getAccountHolder().getLastName(),
                "Amount: € " + amount,
                "Due Date: " + dueDate
        );

        return String.join("\n", statements);
    }
}
