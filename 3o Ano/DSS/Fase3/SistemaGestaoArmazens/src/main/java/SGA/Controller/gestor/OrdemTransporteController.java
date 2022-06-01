package SGA.Controller.gestor;

import SGA.Business.IArmazemLN;
import SGA.SistemaGestaoArmazemApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class OrdemTransporteController {
    private final IArmazemLN iArmazemLN = SistemaGestaoArmazemApplication.getIArmazemLN();
    @FXML
    public Button back;
    @FXML
    public CheckBox entrega;
    @FXML
    public CheckBox recolha;
    @FXML
    public TextField seccao;
    @FXML
    public TextField prateleira;

    @FXML
    public void back() {
        ((Stage) back.getScene().getWindow()).close();
    }

    @FXML
    private void criarOrdemTransporte() {
        if (
                entrega.isSelected() && recolha.isSelected() ||
                        !entrega.isSelected() && !recolha.isSelected()
        ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The form contains errors!");
            alert.showAndWait();

            return;
        }

        Preferences prefs = Preferences.userNodeForPackage(SistemaGestaoArmazemApplication.class);
        int Gestor_id = prefs.getInt("loggedInId", 0);

        try {
            iArmazemLN.criarOrdemTransporte(
                    entrega.isSelected() ? "Entrega" : "Recolha",
                    Integer.parseInt(seccao.getText()),
                    Integer.parseInt(prateleira.getText()),
                    Gestor_id
            );
            success();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The form contains errors!");
            alert.showAndWait();
        }
    }

    private void success() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Ordem de Transporte em processamento!");
        alert.showAndWait();
    }
}
