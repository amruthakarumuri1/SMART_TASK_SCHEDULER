
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class Reports {

    private TaskManager manager;

    public Reports(TaskManager manager) {
        this.manager = manager;
    }

    public VBox show() {

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(25));

        Label heading = new Label("Task Reports");
        heading.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        int total = manager.getTasks().size();
        int completed = 0;
        int pending = 0;
        int overdue = 0;

        for (Task task : manager.getTasks()) {

            if ("Completed".equalsIgnoreCase(task.getStatus())) {
                completed++;
            } else {
                pending++;

                if (task.getDeadline() != null &&
                    task.getDeadline().isBefore(LocalDate.now())) {
                    overdue++;
                }
            }
        }

        Label totalLabel = new Label("Total Tasks: " + total);
        Label completedLabel = new Label("Completed Tasks: " + completed);
        Label pendingLabel = new Label("Pending Tasks: " + pending);
        Label overdueLabel = new Label("Overdue Tasks: " + overdue);

        double progress = total == 0 ? 0 : (double) completed / total;

        ProgressBar progressBar = new ProgressBar(progress);
        progressBar.setMaxWidth(300);

        Label progressLabel = new Label(
            "Completion: " + Math.round(progress * 100) + "%"
        );

        layout.getChildren().addAll(
            heading,
            totalLabel,
            completedLabel,
            pendingLabel,
            overdueLabel,
            progressLabel,
            progressBar
        );

        return layout;
    }
}

