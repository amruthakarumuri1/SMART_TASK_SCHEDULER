
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class Settings {

    public VBox show() {

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(25));
        layout.setStyle("-fx-background-color: #F1F5F9;");

        Label heading = new Label("Settings");
        heading.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        Label themeLabel = new Label("Choose Application Theme");

        ComboBox<String> themeBox = new ComboBox<>();
        themeBox.getItems().addAll("Light", "Dark");
        themeBox.setValue("Light");

        Button applyButton = new Button("Apply Theme");

        Label message = new Label("");

        applyButton.setOnAction(e -> {

            if ("Dark".equals(themeBox.getValue())) {
                layout.setStyle("-fx-background-color: #1E293B;");
                heading.setStyle(
                    "-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: white;"
                );
                themeLabel.setStyle("-fx-text-fill: white;");
                message.setStyle("-fx-text-fill: lightgreen;");
                message.setText("Dark theme applied to Settings page.");
            } else {
                layout.setStyle("-fx-background-color: #F1F5F9;");
                heading.setStyle(
                    "-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: black;"
                );
                themeLabel.setStyle("-fx-text-fill: black;");
                message.setStyle("-fx-text-fill: green;");
                message.setText("Light theme applied to Settings page.");
            }
        });

        layout.getChildren().addAll(
            heading,
            themeLabel,
            themeBox,
            applyButton,
            message
        );

        return layout;
    }
}

