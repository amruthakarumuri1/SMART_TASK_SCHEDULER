
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Dashboard {
    private TaskManager manager;

    public Dashboard(TaskManager manager) {
        this.manager = manager;
    }

    public VBox show() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(25));
        layout.setStyle("-fx-background-color: #F4F6FA;");

        Label heading = new Label("Smart Task Scheduler Dashboard");
        heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        int total = manager.getTasks().size();
        int completed = 0;
        int pending = 0;

        for (Task task : manager.getTasks()) {
            if ("Completed".equalsIgnoreCase(task.getStatus())) {
                completed++;
            } else {
                pending++;
            }
        }

        Label totalLabel = new Label("Total Tasks: " + total);
        Label completedLabel = new Label("Completed Tasks: " + completed);
        Label pendingLabel = new Label("Pending Tasks: " + pending);

        totalLabel.setStyle("-fx-font-size: 18px;");
        completedLabel.setStyle("-fx-font-size: 18px;");
        pendingLabel.setStyle("-fx-font-size: 18px;");

        layout.getChildren().addAll(
            heading, totalLabel, completedLabel, pendingLabel
        );

        return layout;
    }
}

