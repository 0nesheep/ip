package yippee;
import java.util.ArrayList;
import java.util.List;

import yippee.commands.Command;
import yippee.commands.CreateTaskCommand;
import yippee.commands.DeleteCommand;
import yippee.commands.ExitCommand;
import yippee.commands.ListCommand;
import yippee.commands.MarkCommand;
import yippee.exceptions.InvalidCommandException;

public class Parser {
    private static final List<String> VALID_TASKS = new ArrayList<>(List.of("todo", "deadline", "event"));
    private Ui ui;
    public Parser() {
        this.ui = new Ui();
    }

    public Command parseCommand(String command) throws InvalidCommandException {
        String[] split = command.split("\\s+", 2);
        String commandName = split[0].toLowerCase().trim();
        if (commandName.equals("bye")) {
            return new ExitCommand();
        } else if (commandName.equals("list")) {
            return new ListCommand();
        } else if (commandName.equals("mark")) {
            if (split.length == 1) {
                throw new InvalidCommandException(
                        "Wrong format! Please include the number that you want me to unmark >:(");
            }
            int number = Integer.parseInt(split[1]);
            return new MarkCommand(false, number);
        } else if (commandName.equals("unmark")) {
            if (split.length == 1) {
                throw new InvalidCommandException(
                        "Wrong format! Please include the number that you want me to unmark >:(");
            }
            int number = Integer.parseInt(split[1]);
            return new MarkCommand(true, number);
        } else if (commandName.equals("delete")) {
            if (split.length == 1) {
                throw new InvalidCommandException(
                        "Wrong format! Please include the number that you want me to unmark >:(");
            }
            int number = Integer.parseInt(split[1]);
            return new DeleteCommand(number);
        } else if (VALID_TASKS.contains(commandName)) {
            if (split.length == 1) {
                throw new InvalidCommandException("You need to tell me the task name >:0");
            }
            String taskType = split[0].toLowerCase();
            String taskName = split[1].toLowerCase();
            return new CreateTaskCommand(taskType, taskName);
        } else {
            throw new InvalidCommandException("I don't know that command :( sorry...");
        }
    }
}
