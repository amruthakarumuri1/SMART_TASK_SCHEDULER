
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class FileHandler {

    private static final String FILE_NAME = "data/tasks.txt";

    private static String encode(String value) {
        return Base64.getEncoder().encodeToString(
            value.getBytes(StandardCharsets.UTF_8)
        );
    }

    private static String decode(String value) {
        return new String(
            Base64.getDecoder().decode(value),
            StandardCharsets.UTF_8
        );
    }

    public static void saveTasks(List<Task> tasks) {
        try {
            Path path = Paths.get(FILE_NAME);
            Files.createDirectories(path.getParent());

            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                for (Task task : tasks) {
                    String line =
                        encode(task.getTitle()) + "," +
                        encode(task.getDescription()) + "," +
                        task.getDeadline() + "," +
                        encode(task.getPriority()) + "," +
                        encode(task.getCategory()) + "," +
                        encode(task.getStatus());

                    writer.write(line);
                    writer.newLine();
                }
            }

            System.out.println("Tasks saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        Path path = Paths.get(FILE_NAME);

        if (!Files.exists(path)) {
            return tasks;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);

                if (data.length == 6) {
                    Task task = new Task(
                        decode(data[0]),
                        decode(data[1]),
                        java.time.LocalDate.parse(data[2]),
                        decode(data[3]),
                        decode(data[4]),
                        decode(data[5])
                    );

                    tasks.add(task);
                }
            }

        } catch (IOException | RuntimeException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }
}

