package dev.zanex.commands;

import dev.zanex.objects.Command;
import dev.zanex.program.Main;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "Displays this message.");
    }

    @Override
    public void execute() {
        List<String> helps = new ArrayList<>();

        Main.getCommandHandler().getCommands().forEach(
            command -> helps.add(command.getHelp())
        );

        Main.getOutputHandler().printTable("Help", helps);
    }
}
