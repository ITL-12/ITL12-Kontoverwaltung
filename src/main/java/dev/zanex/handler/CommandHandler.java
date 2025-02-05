package dev.zanex.handler;

import dev.zanex.commands.*;
import dev.zanex.objects.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CommandHandler implements Runnable {
    private static final String prefix = "~$";
    private boolean running;
    private final Map<String, Command> commands = new HashMap<>();

    public CommandHandler() {
        this.running = true;
        Thread thread = new Thread(this);
        thread.start();

        HelpCommand help = new HelpCommand();
        commands.put(help.getName(), help);

        CreateAccountCommand createAccount = new CreateAccountCommand();
        commands.put(createAccount.getName(), createAccount);

        ListCommand list = new ListCommand();
        commands.put(list.getName(), list);

        InfoCommand info = new InfoCommand();
        commands.put(info.getName(), info);

        ManageCommand manage = new ManageCommand();
        commands.put(manage.getName(), manage);
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.print(prefix + " ");
            String command = scanner.nextLine();

            handleCommand(command);
        }

        scanner.close();
    }

    public List<Command> getCommands() {
        return List.copyOf(commands.values());
    }

    private void handleCommand(String command) {
        if(commands.containsKey(command.toLowerCase())) {
            commands.get(command.toLowerCase()).execute();
            return;
        }

        System.out.println("Command not found.");
    }

    public void stop() {
        this.running = false;
    }
}