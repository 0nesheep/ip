package yippee.commands;
import yippee.Storage;
import yippee.TaskList;
import yippee.Ui;
import yippee.exceptions.InvalidCommandException;
import yippee.exceptions.YippeeException;

/**
 * Represents commands to exit the chatbot.
 */
public class ExitCommand extends Command {
    public ExitCommand() {
        super(true);
    }

    /**
     * {@inheritDoc}
     * @param tasks TaskList of active tasks
     * @param ui Ui instance to print responses.
     * @param storage Storage instance to store any data.
     * @throws InvalidCommandException If command is of invalid format.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws InvalidCommandException {
        storage.resetSave();
        storage.storeData(tasks);
        TaskList savedData = null;
        try {
            savedData = storage.load();
            assert savedData == tasks : "Saved data not the same as tasklist";
            return ui.endCommands();
        } catch (YippeeException e) {
            return ui.printError(e);
        }

    }
}
