
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class Reminders {

    private TaskManager manager;

    public Reminders(TaskManager manager) {
        this.manager = manager;
    }

    public VBox show() {

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(25));

        Label heading = new Label("Task Reminders");

        heading.setStyle(
            "-fx-font-size: 26px;" +
            "-fx-font-weight: bold;"
        );

        Label todayLabel = new Label("Today's Date: " + LocalDate.now());

        ListView<String> reminderList = new ListView<>();
        reminderList.setPrefHeight(350);

        int upcoming = 0;
        int today = 0;
        int overdue = 0;

        for (Task task : manager.getTasks()) {

            if ("Completed".equalsIgnoreCase(task.getStatus())) {
                continue;
            }

            LocalDate deadline = task.getDeadline();

            String message;

            if (deadline.isBefore(LocalDate.now())) {
                message = "OVERDUE";
                overdue++;
            }
            else if (deadline.isEqual(LocalDate.now())) {
                message = "DUE TODAY";
                today++;
            }
            else {
                message = "UPCOMING";
                upcoming++;
            }

            reminderList.getItems().add(
                message + " | " +
                task.getTitle() + " | " +
                deadline + " | " +
                task.getStatus()
            );
        }

        Label summary = new Label(
            "Overdue: " + overdue +
            "     Due Today: " + today +
            "     Upcoming: " + upcoming
        );

        if (reminderList.getItems().isEmpty()) {
            reminderList.getItems().add("No pending reminders!");
        }

        layout.getChildren().addAll(
            heading,
            todayLabel,
            summary,
            new Label("Your Reminders:"),
            reminderList
        );

        return layout;
    }
}

