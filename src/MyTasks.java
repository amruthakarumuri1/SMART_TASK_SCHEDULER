
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MyTasks {

    private TaskManager manager;

    public MyTasks(TaskManager manager) {
        this.manager = manager;
    }

    public VBox show() {

        VBox layout = new VBox(12);
        layout.setPadding(new Insets(20));

        Label heading = new Label("My Tasks");
        heading.setStyle(
            "-fx-font-size: 25px; -fx-font-weight: bold;"
        );

        TextField titleField = new TextField();
        titleField.setPromptText("Enter task title");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Enter description");

        DatePicker deadlinePicker = new DatePicker();

        ComboBox<String> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll("High", "Medium", "Low");
        priorityBox.setValue("Medium");

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll(
            "Study", "Work", "Personal", "Project", "Other"
        );
        categoryBox.setValue("Study");

        ComboBox<String> statusBox = new ComboBox<>();
        statusBox.getItems().addAll(
            "Pending", "In Progress", "Completed"
        );
        statusBox.setValue("Pending");

        TextField searchField = new TextField();
        searchField.setPromptText("Search tasks");

        ComboBox<String> sortBox = new ComboBox<>();
        sortBox.getItems().addAll(
            "Default Order",
            "High Priority First",
            "Low Priority First"
        );
        sortBox.setValue("Default Order");

        ListView<String> taskList = new ListView<>();
        taskList.setPrefHeight(200);

        Button addButton = new Button("Add Task");
        Button editButton = new Button("Edit Task");
        Button updateButton = new Button("Update Status");
        Button deleteButton = new Button("Delete Task");

        List<Task> displayedTasks = new ArrayList<>();

        Runnable refreshTasks = () -> {

            taskList.getItems().clear();
            displayedTasks.clear();

            List<Task> sortedTasks =
                new ArrayList<>(manager.getTasks());

            if ("High Priority First".equals(sortBox.getValue())) {
                sortedTasks.sort((a, b) ->
                    priorityValue(a.getPriority()) -
                    priorityValue(b.getPriority())
                );
            }

            if ("Low Priority First".equals(sortBox.getValue())) {
                sortedTasks.sort((a, b) ->
                    priorityValue(b.getPriority()) -
                    priorityValue(a.getPriority())
                );
            }

            String keyword = searchField.getText()
                .toLowerCase().trim();

            for (Task task : sortedTasks) {

                if (task.getTitle().toLowerCase()
                        .contains(keyword)) {

                    displayedTasks.add(task);

                    taskList.getItems().add(
                        task.getTitle() + " | " +
                        task.getPriority() + " | " +
                        task.getStatus() + " | " +
                        task.getDeadline()
                    );
                }
            }
        };

        // Add Task
        addButton.setOnAction(e -> {

            if (titleField.getText().trim().isEmpty()
                    || deadlinePicker.getValue() == null) {

                showMessage("Please enter task title and deadline!");
                return;
            }

            Task task = new Task(
                titleField.getText().trim(),
                descriptionField.getText().trim(),
                deadlinePicker.getValue(),
                priorityBox.getValue(),
                categoryBox.getValue(),
                "Pending"
            );

            manager.addTask(task);

            clearFields(
                titleField, descriptionField,
                deadlinePicker, priorityBox,
                categoryBox, statusBox
            );

            refreshTasks.run();
            showMessage("Task added successfully!");
        });

        // Select Task
        taskList.getSelectionModel()
            .selectedIndexProperty()
            .addListener((obs, oldValue, newValue) -> {

                int index = newValue.intValue();

                if (index >= 0 && index < displayedTasks.size()) {

                    Task task = displayedTasks.get(index);

                    titleField.setText(task.getTitle());
                    descriptionField.setText(task.getDescription());
                    deadlinePicker.setValue(task.getDeadline());
                    priorityBox.setValue(task.getPriority());
                    categoryBox.setValue(task.getCategory());
                    statusBox.setValue(task.getStatus());
                }
            });

        // Edit Task
        editButton.setOnAction(e -> {

            int index = taskList.getSelectionModel()
                .getSelectedIndex();

            if (index == -1) {
                showMessage("Please select a task to edit!");
                return;
            }

            if (titleField.getText().trim().isEmpty()
                    || deadlinePicker.getValue() == null) {

                showMessage("Enter task title and deadline!");
                return;
            }

            Task selectedTask = displayedTasks.get(index);

            int actualIndex =
                manager.getTasks().indexOf(selectedTask);

            Task updatedTask = new Task(
                titleField.getText().trim(),
                descriptionField.getText().trim(),
                deadlinePicker.getValue(),
                priorityBox.getValue(),
                categoryBox.getValue(),
                statusBox.getValue()
            );

            manager.updateTask(actualIndex, updatedTask);

            refreshTasks.run();

            showMessage("Task edited successfully!");
        });

        // Update Status
        updateButton.setOnAction(e -> {

            int index = taskList.getSelectionModel()
                .getSelectedIndex();

            if (index == -1) {
                showMessage("Please select a task!");
                return;
            }

            Task selectedTask = displayedTasks.get(index);

            int actualIndex =
                manager.getTasks().indexOf(selectedTask);

            manager.updateStatus(
                actualIndex,
                statusBox.getValue()
            );

            refreshTasks.run();

            showMessage("Status updated successfully!");
        });

        // Delete Task
        deleteButton.setOnAction(e -> {

            int index = taskList.getSelectionModel()
                .getSelectedIndex();

            if (index == -1) {
                showMessage("Please select a task!");
                return;
            }

            Task selectedTask = displayedTasks.get(index);

            int actualIndex =
                manager.getTasks().indexOf(selectedTask);

            manager.deleteTask(actualIndex);

            clearFields(
                titleField, descriptionField,
                deadlinePicker, priorityBox,
                categoryBox, statusBox
            );

            refreshTasks.run();

            showMessage("Task deleted successfully!");
        });

        // Search
        searchField.textProperty().addListener(
            (obs, oldValue, newValue) -> refreshTasks.run()
        );

        // Sort
        sortBox.setOnAction(e -> refreshTasks.run());

        layout.getChildren().addAll(
            heading,
            new Label("Task Title"),
            titleField,
            new Label("Description"),
            descriptionField,
            new Label("Deadline"),
            deadlinePicker,
            new Label("Priority"),
            priorityBox,
            new Label("Category"),
            categoryBox,
            addButton,
            new Label("Search Tasks"),
            searchField,
            new Label("Sort Tasks"),
            sortBox,
            new Label("Your Tasks"),
            taskList,
            new Label("Select New Status"),
            statusBox,
            editButton,
            updateButton,
            deleteButton
        );

        refreshTasks.run();

        return layout;
    }

    private int priorityValue(String priority) {

        switch (priority) {
            case "High":
                return 1;
            case "Medium":
                return 2;
            case "Low":
                return 3;
            default:
                return 4;
        }
    }

    private void clearFields(
        TextField title,
        TextField description,
        DatePicker deadline,
        ComboBox<String> priority,
        ComboBox<String> category,
        ComboBox<String> status
    ) {

        title.clear();
        description.clear();
        deadline.setValue(null);
        priority.setValue("Medium");
        category.setValue("Study");
        status.setValue("Pending");
    }

    private void showMessage(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}




