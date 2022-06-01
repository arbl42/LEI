package SGA.Controller.gestor;

import SGA.Business.IArmazemLN;
import SGA.Business.Utilizador;
import SGA.SistemaGestaoArmazemApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CriarRobotController {

    private final IArmazemLN iArmazemLN = SistemaGestaoArmazemApplication.getIArmazemLN();
    @FXML
    public Button back;
    @FXML
    public TextField password;

    @FXML
    public void back() {
        ((Stage) back.getScene().getWindow()).close();
    }

    @FXML
    public void criarRobot() {
        try {
            Utilizador robot = iArmazemLN.criarRobot(
                    password.getText()
            );
            success(robot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void success(Utilizador robot) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Robot criado!");
        alert.setContentText("ID do novo Robot: " + robot.key());
        alert.showAndWait();
    }
}
