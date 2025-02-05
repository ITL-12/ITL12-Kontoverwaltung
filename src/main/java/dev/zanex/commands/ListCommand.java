package dev.zanex.commands;

import dev.zanex.objects.Command;
import dev.zanex.program.Main;

import java.util.ArrayList;
import java.util.List;

public class ListCommand extends Command {
    public ListCommand() {
        super("list", "Lists all accounts.");
    }

    @Override
    public void execute() {
        List<String> accounts = new ArrayList<>();

        Main.getAccountHandler().getAccounts().values().forEach(
            account -> accounts.add("&c" + account.getAccountNumber() + " &f- &7" + account.getAccountHolder().getFullName() + " &f- &3" + account.getAccountType().name())
        );

        Main.getOutputHandler().printTable("Accounts", accounts);
    }
}
