package dev.zanex.commands;

import dev.zanex.objects.BankAccount;
import dev.zanex.objects.Command;
import dev.zanex.program.Main;

import java.util.Scanner;

public class TransferMoneyCommand extends Command {
    public TransferMoneyCommand() {
        super("transfer", "Transfer money between accounts.");
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        Main.getOutputHandler().print("Account Number: ");
        String accountNumber = scanner.nextLine();

        if(Main.getAccountHandler().getAccounts().get(accountNumber) == null) {
            Main.getOutputHandler().println("Account not found.");
        } else {
            BankAccount account = Main.getAccountHandler().getAccounts().get(accountNumber);
            BankAccount accountTo = null;

            Main.getOutputHandler().print("Transfer to: ");
            String accountNumberTo = scanner.nextLine();
            accountTo = Main.getAccountHandler().getAccounts().get(accountNumberTo);

            if(accountTo == null) {
                Main.getOutputHandler().println("Account not found.");
            } else {
                Main.getOutputHandler().print("Amount: ");
                double amount = Double.parseDouble(scanner.nextLine());

                account.transfer(accountTo, amount);
            }
        }
    }
}
