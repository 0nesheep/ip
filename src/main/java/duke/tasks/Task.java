package duke.tasks;
import java.io.FileWriter;
import java.io.IOException;
public class Task {
    private boolean isDone;
    private String name;

    public Task(String name) {
        this.isDone = false;
        this.name = name;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        String checkBox = isDone ? "[X]" : "[ ]";
        return String.format("%s %s", checkBox, name);
    }

    public String dataString() {
        return String.format("%s|%s", this.isDone, this.name);
    }

    public void writeToData(String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, true);
        String writeData = dataString();
        fileWriter.write(writeData);
        fileWriter.close();
    }
}
