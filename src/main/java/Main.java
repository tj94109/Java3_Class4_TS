import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        String fxmlPath = "/fxml/sample.fxml";
        FXMLLoader loader = new FXMLLoader( getClass().getResource( fxmlPath ) );
        Parent root = loader.load();

        primaryStage.setTitle("Tweet Stuff");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();

        Controller c = loader.getController();
        c.load();

    }


    public static void main(String[] args) {
        launch(args);
    }
}