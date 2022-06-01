package SGA.Controller.gestor;

import SGA.Business.IArmazemLN;
import SGA.Business.Utilizador;
import SGA.SistemaGestaoArmazemApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CriarEncarregadoController {

    private final IArmazemLN iArmazemLN = SistemaGestaoArmazemApplication.getIArmazemLN();
    @FXML
    public Button back;
    @FXML
    public TextField nome;
    @FXML
    public TextField password;

    @FXML
    public void back() {
        ((Stage) back.getScene().getWindow()).close();
    }

    @FXML
    public void criarEncarregado() {
        try {
            Utilizador encarregado = iArmazemLN.criarEncarregado(
                    nome.getText(),
                    password.getText()
            );
            success(encarregado);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void success(Utilizador encarregado) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Encarregado criado!");
        alert.setContentText("ID do novo Encarregado: " + encarregado.key());
        alert.showAndWait();
    }
}
