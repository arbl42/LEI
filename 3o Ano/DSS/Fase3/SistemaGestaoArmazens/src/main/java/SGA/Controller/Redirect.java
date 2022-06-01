package SGA.Controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Redirect {
    public static void redirectTo(Parent parent, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(Redirect.class.getResource(fxml));

        Stage stage = new Stage();
        stage.initOwner(parent.getScene().getWindow());
        stage.setScene(new Scene(loader.load()));

        stage.setOnCloseRequest(e -> Platform.exit());

        stage.show();
    }
}
