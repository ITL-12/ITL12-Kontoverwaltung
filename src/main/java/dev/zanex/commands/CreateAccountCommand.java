package dev.zanex.commands;

import dev.zanex.objects.Command;
import dev.zanex.program.Main;
import dev.zanex.types.BankAccountType;

import java.util.Scanner;


public class CreateAccountCommand extends Command {
    public CreateAccountCommand() {
        super("create", "A command to create a bank account.");
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();

        System.out.println("Enter account type (GIRO/SAVINGS/CREDIT):");
        String accountType = scanner.nextLine();

        Main.getAccountHandler().createAccount(firstName, lastName, BankAccountType.valueOf(accountType));
    }
}