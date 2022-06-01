package SGA.Controller.encarregado;

import SGA.Business.IArmazemLN;
import SGA.Controller.Redirect;
import SGA.SistemaGestaoArmazemApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginEncarregadoController {
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

            iArmazemLN.iniciarSessaoEncarregado(id, password);

            Redirect.redirectTo(back, "/views/encarregado/encarregado.fxml");

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Invalid credentials");
            alert.showAndWait();
        }
    }

    private void redirectTo(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        Stage stage = new Stage();
        stage.initOwner(loginButton.getScene().getWindow());
        stage.setScene(new Scene(loader.load()));
        stage.showAndWait();
    }

    @FXML
    public void back() {
        ((Stage) back.getScene().getWindow()).close();
    }
}
