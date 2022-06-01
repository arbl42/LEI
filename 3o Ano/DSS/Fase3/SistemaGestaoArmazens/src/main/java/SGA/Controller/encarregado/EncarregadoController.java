package SGA.Controller.encarregado;

import SGA.Controller.Redirect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class EncarregadoController {
    public Button back;

    @FXML
    public void back() {
        ((Stage) back.getScene().getWindow()).close();
    }

    @FXML
    public void verOrdensTransporte() {
        try {
            Redirect.redirectTo(back, "/views/gestor/listOrdemTransporte.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verRobots() {
        try {
            Redirect.redirectTo(back, "/views/gestor/informacaoRobots.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
