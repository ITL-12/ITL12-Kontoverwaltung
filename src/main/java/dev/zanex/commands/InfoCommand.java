package dev.zanex.commands;

import dev.zanex.objects.BankAccount;
import dev.zanex.objects.Command;
import dev.zanex.program.Main;

import java.util.List;
import java.util.Scanner;

public class InfoCommand extends Command {
    public InfoCommand() {
        super("info", "A command to get information about a bank account.");
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
            Main.getOutputHandler().printTable("Accounts", List.of("&c" + account.getAccountNumber() + " &f- &7" + account.getAccountHolder().getFullName() + " &f- &3" + account.getAccountType().name()));
        }
    }
}
