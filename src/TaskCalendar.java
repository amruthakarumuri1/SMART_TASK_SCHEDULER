
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDate;
import java.time.YearMonth;

public class TaskCalendar {

    private TaskManager manager;
    private YearMonth currentMonth = YearMonth.now();

    private Label monthLabel;
    private GridPane calendarGrid;
    private ListView<String> taskList;
    private Label selectedDateLabel;

    public TaskCalendar(TaskManager manager) {
        this.manager = manager;
    }

    public VBox show() {

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label heading = new Label("Task Calendar");
        heading.setStyle(
            "-fx-font-size: 25px; -fx-font-weight: bold;"
        );

        Button previous = new Button("Previous Month");
        Button next = new Button("Next Month");

        monthLabel = new Label();

        HBox navigation = new HBox(
            15, previous, monthLabel, next
        );

        navigation.setAlignment(Pos.CENTER);

        calendarGrid = new GridPane();
        calendarGrid.setHgap(8);
        calendarGrid.setVgap(8);

        String[] days = {
            "Mon", "Tue", "Wed", "Thu",
            "Fri", "Sat", "Sun"
        };

        for (int i = 0; i < 7; i++) {
            Label day = new Label(days[i]);
            calendarGrid.add(day, i, 0);
        }

        selectedDateLabel = new Label("Select a date");

        taskList = new ListView<>();
        taskList.setPrefHeight(180);

        previous.setOnAction(e -> {
            currentMonth = currentMonth.minusMonths(1);
            refreshCalendar();
        });

        next.setOnAction(e -> {
            currentMonth = currentMonth.plusMonths(1);
            refreshCalendar();
        });

        root.getChildren().addAll(
            heading,
            navigation,
            calendarGrid,
            selectedDateLabel,
            taskList
        );

        refreshCalendar();

        return root;
    }

    private void refreshCalendar() {

        monthLabel.setText(currentMonth.toString());

        calendarGrid.getChildren().removeIf(
            node -> GridPane.getRowIndex(node) != null
                && GridPane.getRowIndex(node) > 0
        );

        int start = currentMonth.atDay(1)
            .getDayOfWeek().getValue() - 1;

        for (int day = 1;
             day <= currentMonth.lengthOfMonth();
             day++) {

            final int selectedDay = day;

            LocalDate date = currentMonth.atDay(day);

            Button button = new Button(
                String.valueOf(day)
            );

            button.setMinSize(55, 45);

            boolean hasTasks = manager.getTasks().stream()
                .anyMatch(task ->
                    task.getDeadline().equals(date)
                );

            if (hasTasks) {
                button.setStyle(
                    "-fx-background-color: #A5D6A7;"
                );
            }

            int position = start + day - 1;

            calendarGrid.add(
                button,
                position % 7,
                position / 7 + 1
            );

            button.setOnAction(e ->
                showTasks(currentMonth.atDay(selectedDay))
            );
        }
    }

    private void showTasks(LocalDate date) {

        selectedDateLabel.setText(
            "Tasks for: " + date
        );

        taskList.getItems().clear();

        for (Task task : manager.getTasks()) {

            if (task.getDeadline().equals(date)) {

                taskList.getItems().add(
                    task.getTitle() + " - " +
                    task.getStatus()
                );
            }
        }

        if (taskList.getItems().isEmpty()) {
            taskList.getItems().add("No tasks for this date.");
        }
    }
}

