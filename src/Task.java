

import java.time.LocalDate;

public class Task {

    private String title;
    private String description;
    private LocalDate deadline;
    private String priority;
    private String category;
    private String status;

    public Task(String title, String description,
                LocalDate deadline, String priority,
                String category, String status) {

        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.category = category;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getPriority() {
        return priority;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}