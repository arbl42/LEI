package SGA.Controller.robot;

import SGA.Business.IArmazemLN;
import SGA.Controller.Redirect;
import SGA.SistemaGestaoArmazemApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class OpcoesOrdemTransporteController {
    private final IArmazemLN iArmazemLN = SistemaGestaoArmazemApplication.getIArmazemLN();
    public Button back;

    @FXML
    public void back() {
        ((Stage) back.getScene().getWindow()).close();
    }

    private void redirectTo(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        Stage stage = new Stage();
        stage.initOwner(back.getScene().getWindow());
        stage.setScene(new Scene(loader.load()));
        stage.showAndWait();
    }

    @FXML
    public void acceptedOrdTransp() {
        Preferences prefs = Preferences.userNodeForPackage(SistemaGestaoArmazemApplication.class);
        int id_ordem = prefs.getInt("ordemTransporte", 0);
        int id_robot = prefs.getInt("loggedInId", 0);
        iArmazemLN.aceitarOrdTransp(id_ordem, id_robot);
        try {
            redirectTo("/views/robot/robot.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rejectedOrdTransp() {
        Preferences prefs = Preferences.userNodeForPackage(SistemaGestaoArmazemApplication.class);
        int id_ordem = prefs.getInt("ordemTransporte", 0);
        int id_robot = prefs.getInt("loggedInId", 0);
        iArmazemLN.rejeitarOrdTransp(id_ordem, id_robot);
        try {
            Redirect.redirectTo(back, "/views/robot/robot.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
