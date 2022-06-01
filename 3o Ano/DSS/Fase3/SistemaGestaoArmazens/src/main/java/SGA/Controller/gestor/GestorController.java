package SGA.Controller.gestor;

import SGA.Controller.Redirect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GestorController {
    public Button back;

    @FXML
    public void back() {
        ((Stage) back.getScene().getWindow()).close();
    }

    @FXML
    public void novaOrdemTransporte() {
        try {
            Redirect.redirectTo(back, "/views/gestor/criarOrdemTransporte.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void verOrdensTransporte() {
        try {
            Redirect.redirectTo(back, "/views/gestor/listOrdemTransporte.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void gerirPaletes() {
        try {
            Redirect.redirectTo(back, "/views/gestor/informacaoPaletes.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void gerirRobots() {
        try {
            Redirect.redirectTo(back, "/views/gestor/informacaoRobots.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void criarEncarregado() {
        try {
            Redirect.redirectTo(back, "/views/gestor/criarEncarregado.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void criarRobot() {
        try {
            Redirect.redirectTo(back, "/views/gestor/criarRobot.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
