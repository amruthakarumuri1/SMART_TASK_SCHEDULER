
import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private List<Task> tasks;

    // Load saved tasks when the application starts
    public TaskManager() {
        tasks = FileHandler.loadTasks();
    }

    // Add a new task
    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }

    // Get all tasks
    public List<Task> getTasks() {
        return tasks;
    }

    // Edit an existing task
    public void updateTask(int index, Task task) {
        if (index >= 0 && index < tasks.size()) {
            tasks.set(index, task);
            saveTasks();
        }
    }

    // Update task status
    public void updateStatus(int index, String status) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).setStatus(status);
            saveTasks();
        }
    }

    // Delete a task
    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            saveTasks();
        }
    }

    // Search tasks by title
    public List<Task> searchTask(String keyword) {
        List<Task> result = new ArrayList<>();

        if (keyword == null) {
            return result;
        }

        for (Task task : tasks) {
            if (task.getTitle() != null &&
                task.getTitle().toLowerCase()
                    .contains(keyword.toLowerCase())) {
                result.add(task);
            }
        }

        return result;
    }

    // Save tasks to the file
    private void saveTasks() {
        FileHandler.saveTasks(tasks);
    }
}


 

