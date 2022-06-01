package SGA.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class WelcomeController {
    @FXML
    public Button loginButtonG;

    @FXML
    public Button loginButtonE;

    @FXML
    public Button loginButtonR;

    @FXML
    public ImageView logo;


    @FXML
    public void initialize() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("logo.png").getFile());
        Image image = new Image(file.toURI().toString());
        logo.setImage(image);
    }

    @FXML
    public void onLoginButtonClickG() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gestor/loginGestor.fxml"));

        Stage stage = new Stage();
        stage.initOwner(loginButtonG.getScene().getWindow());
        stage.setScene(new Scene(loader.load()));

        stage.showAndWait();
    }

    @FXML
    public void onLoginButtonClickE() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/encarregado/loginEncarregado.fxml"));

        Stage stage = new Stage();
        stage.initOwner(loginButtonE.getScene().getWindow());
        stage.setScene(new Scene(loader.load()));

        stage.showAndWait();
    }

    @FXML
    public void onLoginButtonClickR() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/robot/loginRobot.fxml"));

        Stage stage = new Stage();
        stage.initOwner(loginButtonR.getScene().getWindow());
        stage.setScene(new Scene(loader.load()));

        stage.showAndWait();
    }
}
