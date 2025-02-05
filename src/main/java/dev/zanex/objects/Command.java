package dev.zanex.objects;

public abstract class Command {
    private final String name;
    private final String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getHelp() {
        return "&9" + name + " &7- &f" + description;
    }

    public String getName() {
        return this.name;
    }

    public abstract void execute();
}
