
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Navigation {

    private TaskManager manager;
    private BorderPane mainLayout;

    public Navigation() {
        manager = new TaskManager();
        mainLayout = new BorderPane();
    }

    public void show(Stage stage) {

        // Sidebar
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(25, 15, 25, 15));
        sidebar.setPrefWidth(210);
        sidebar.getStyleClass().add("sidebar");

        // Application title
        Label heading = new Label("SMART TASKS");
        heading.getStyleClass().add("sidebar-title");

        Label subtitle = new Label("Task Management");
        subtitle.setStyle(
            "-fx-text-fill: #A5A8CE;" +
            "-fx-font-size: 11px;"
        );

        // Navigation buttons
        Button dashboard = createButton("Dashboard");
        Button tasks = createButton("My Tasks");
        Button calendar = createButton("Calendar");
        Button reports = createButton("Reports");
        Button reminders = createButton("Reminders");
        Button settings = createButton("Settings");

        Label menuLabel = new Label("MAIN MENU");
        menuLabel.setStyle(
            "-fx-text-fill: #8589B3;" +
            "-fx-font-size: 10px;" +
            "-fx-font-weight: bold;"
        );

        sidebar.getChildren().addAll(
            heading,
            subtitle,
            menuLabel,
            dashboard,
            tasks,
            calendar,
            reports,
            reminders,
            settings
        );

        // Navigation actions
        dashboard.setOnAction(e -> showDashboard());
        tasks.setOnAction(e -> showMyTasks());
        calendar.setOnAction(e -> showCalendar());
        reports.setOnAction(e -> showReports());
        reminders.setOnAction(e -> showReminders());
        settings.setOnAction(e -> showSettings());

        // Main layout
        mainLayout.setLeft(sidebar);

        // Default page
        showDashboard();

        // Scene
        Scene scene = new Scene(mainLayout, 1150, 750);

        // Load CSS
        var css = getClass().getResource("/style.css");

        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        } else {
            System.out.println("style.css not found!");
        }

        stage.setTitle("Smart Task Scheduler");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    // Create sidebar button
    private Button createButton(String text) {

        Button button = new Button(text);

        button.setPrefWidth(180);
        button.setPrefHeight(42);
        button.setAlignment(Pos.CENTER_LEFT);

        button.getStyleClass().add("sidebar-button");

        return button;
    }

    // Dashboard
    private void showDashboard() {

        Dashboard page = new Dashboard(manager);

        mainLayout.setCenter(page.show());
    }

    // My Tasks
    private void showMyTasks() {

        MyTasks page = new MyTasks(manager);

        mainLayout.setCenter(page.show());
    }

    // Calendar
    private void showCalendar() {

        TaskCalendar page = new TaskCalendar(manager);

        mainLayout.setCenter(page.show());
    }

    // Reports
    private void showReports() {

        Reports page = new Reports(manager);

        mainLayout.setCenter(page.show());
    }

    // Reminders
    private void showReminders() {

        Reminders page = new Reminders(manager);

        mainLayout.setCenter(page.show());
    }

    // Settings
    private void showSettings() {

        Settings page = new Settings();

        mainLayout.setCenter(page.show());
    }
}




 




