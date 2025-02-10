package dev.zanex.commands;

import dev.zanex.objects.BankAccount;
import dev.zanex.objects.Command;
import dev.zanex.objects.CreditAccount;
import dev.zanex.program.Main;
import dev.zanex.types.BankAccountType;

import java.time.LocalDate;
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

        if(accountType.equals(BankAccountType.CREDIT.name())) {
            System.out.println("Enter a account number:");
            BankAccount account = Main.getAccountHandler().getAccounts().get(scanner.nextLine());

            System.out.println("Enter a credit amount:");
            double creditLimit = Double.parseDouble(scanner.nextLine());

            new CreditAccount(account, creditLimit, LocalDate.now().plusDays(360));

            Main.getOutputHandler().println("&7[&bCREDIT&7] &7You have successfully taken a loan. Pay it back before " + LocalDate.now().plusDays(360));
        } else {
            Main.getAccountHandler().createAccount(firstName, lastName, BankAccountType.valueOf(accountType));
        }
    }
}