package yippee.commands;
import yippee.Storage;
import yippee.TaskList;
import yippee.Ui;
import yippee.exceptions.InvalidCommandException;

/**
 * Represents commands parsed from user input.
 */
public abstract class Command {
    private boolean isExit;
    public Command(boolean isExit) {
        this.isExit = isExit;
    }

    public boolean isExit() {
        return this.isExit;
    }
    public abstract String execute(TaskList tasks, Ui ui, Storage storage) throws InvalidCommandException;
}
