

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Navigation navigation = new Navigation();
        navigation.show(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


