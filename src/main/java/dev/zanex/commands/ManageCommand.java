package dev.zanex.commands;

import dev.zanex.objects.BankAccount;
import dev.zanex.objects.Command;
import dev.zanex.program.Main;

import java.util.Scanner;

public class ManageCommand extends Command {
    public ManageCommand() {
        super("manage", "A command to manage a bank account and do things like deposit, withdraw, etc.");
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

            Main.getOutputHandler().println("Choose an action: ");
            Main.getOutputHandler().println("1. Deposit");
            Main.getOutputHandler().println("2. Withdraw");
            Main.getOutputHandler().println("3. Account Statement");
            Main.getOutputHandler().println("4. Close Account");
            String action = scanner.nextLine();

            switch(action.toLowerCase()) {
                case "1":
                case "deposit": {
                    Main.getOutputHandler().println("You chose to deposit.");

                    Main.getOutputHandler().print("Amount: ");
                    double amount = Double.parseDouble(scanner.nextLine());

                    account.deposit(amount);
                    break;
                }

                case "2":
                case "withdraw": {
                    Main.getOutputHandler().println("You chose to withdraw.");

                    Main.getOutputHandler().print("Amount: ");
                    double amount = Double.parseDouble(scanner.nextLine());

                    account.withdraw(amount);
                    break;
                }

                case "3":
                case "statement":
                case "account statement": {
                    account.printStatement();
                    break;
                }

                case "4":
                case "close account": {
                    Main.getAccountHandler().closeAccount(account.getAccountNumber());
                    Main.getOutputHandler().println("Account closed.");
                    break;
                }
            }
        }
    }
}
