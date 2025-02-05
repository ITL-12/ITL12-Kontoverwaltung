package dev.zanex.program;

import dev.zanex.commands.HelpCommand;
import dev.zanex.handler.BankAccountHandler;
import dev.zanex.handler.CommandHandler;
import dev.zanex.handler.OutputHandler;

public class Main {
    private static final OutputHandler outputHandler = new OutputHandler();
    private static final BankAccountHandler accountHandler = new BankAccountHandler();
    private static CommandHandler commandHandler;

    public static void main(String[] args) {
        System.out.println("Welcome to the Bank Account System!");
        System.out.println("Type 'help' for a list of commands.");
        commandHandler = new CommandHandler();
    }

    public static BankAccountHandler getAccountHandler() {
        return accountHandler;
    }

    public static OutputHandler getOutputHandler() {
        return outputHandler;
    }

    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }
}