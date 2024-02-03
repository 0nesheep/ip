import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage (String filePath) {
        this.filePath = filePath;
    }

    public TaskList load() throws DukeException{
        TaskList list = new TaskList();
        //load previous tasks
        File directory = new File("./data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        File file = new File(filePath);
        Scanner fileSc = null;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new DukeFileException("Error creating the file : " + e.getMessage());
            }
        }
        try {
            fileSc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new DukeFileException("Storage data file does not exist: " + e.getMessage());
        }
        loadTasks(fileSc, list);
        fileSc.close();

        return list;
    }

    private static void loadTasks (Scanner scanner, TaskList list) throws InvalidCommandException {
        while (scanner.hasNext()) {
            String currentTask = scanner.nextLine();
            String[] taskDetails = currentTask.split("\\|");
            Task newTask = null;
            switch (taskDetails[0]) {
            case "T":
                newTask = new ToDo(taskDetails[2]);
                break;
            case "D":
                newTask = new Deadline(taskDetails[2], taskDetails[3]);
                break;
            case "E":
                newTask = new Event(taskDetails[2], taskDetails[3], taskDetails[4]);
                break;
            }
            if (newTask != null) {
                if (taskDetails[1].equals("true")) {
                    newTask.markDone();
                } else {
                    newTask.markNotDone();
                }
                list.addStoredTask(newTask);
            }
        }
    }
}
