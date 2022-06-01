package SGA.Controller.robot;

import SGA.Business.IArmazemLN;
import SGA.Controller.Redirect;
import SGA.SistemaGestaoArmazemApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class LoginRobotController {
    private final IArmazemLN iArmazemLN = SistemaGestaoArmazemApplication.getIArmazemLN();
    @FXML
    public Button loginButton;
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button back;

    public void login() {
        try {
            int id = Integer.parseInt(usernameField.getText());
            String password = passwordField.getText();

            iArmazemLN.iniciarSessaoRobot(id, password);

            Preferences prefs = Preferences.userNodeForPackage(SistemaGestaoArmazemApplication.class);
            prefs.putInt("loggedInId", id);

            Redirect.redirectTo(back, "/views/robot/robot.fxml");

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Invalid credentials");
            alert.showAndWait();
        }
    }

    @FXML
    public void back() {
        ((Stage) back.getScene().getWindow()).close();
    }
}
