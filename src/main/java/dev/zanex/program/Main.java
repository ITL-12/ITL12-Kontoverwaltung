package dev.zanex.program;

import dev.zanex.handler.BankAccountHandler;
import dev.zanex.handler.CommandHandler;
import dev.zanex.handler.CreditAccountHandler;
import dev.zanex.handler.OutputHandler;

public class Main {
    private static final OutputHandler outputHandler = new OutputHandler();
    private static final BankAccountHandler accountHandler = new BankAccountHandler();
    private static CreditAccountHandler creditAccountHandler = new CreditAccountHandler();
    private static CommandHandler commandHandler;

    public static void main(String[] args) {
        System.out.println("Welcome to the Bank Account System!");
        System.out.println("Type 'help' for a list of commands.");
        commandHandler = new CommandHandler();
    }

    public static OutputHandler getOutputHandler() {
        return outputHandler;
    }

    public static BankAccountHandler getAccountHandler() {
        return accountHandler;
    }

    public static CreditAccountHandler getCreditAccountHandler() {
        return creditAccountHandler;
    }

    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }
}