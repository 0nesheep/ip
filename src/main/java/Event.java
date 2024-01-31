import java.io.FileWriter;
import java.io.IOException;

public class Event extends Task {
    String from;
    String to;
    public Event(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("%s %s (from: %s to: %s)", "[E]", super.toString(), this.from, this.to);
    }

    @Override
    public void writeToData(String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, true);
        String writeData = String.format("%s|%s|%s|%s\n", "E", super.dataString(), this.from, this.to);
        fileWriter.write(writeData);
        fileWriter.close();
    }
}
