package SGA.Controller.robot;

import SGA.Business.IArmazemLN;
import SGA.Business.Localizacao;
import SGA.Business.Robot;
import SGA.SistemaGestaoArmazemApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RobotsInfoController {
    private final IArmazemLN iArmazemLN = SistemaGestaoArmazemApplication.getIArmazemLN();
    @FXML
    public Button back;
    @FXML
    public TableView table;

    @FXML
    public void back() {
        ((Stage) back.getScene().getWindow()).close();
    }

    @FXML
    public void initialize() {
        table.setEditable(true);
        table.getSelectionModel().cellSelectionEnabledProperty().set(true);

        ObservableList<TableColumn> observableList = table.getColumns();

        observableList.get(0).setCellValueFactory(
                new PropertyValueFactory<Localizacao, String>("id")
        );
        observableList.get(1).setCellValueFactory(
                new PropertyValueFactory<Localizacao, String>("estado")
        );


        table.setItems(getRobots());
        table.refresh();
    }

    private ObservableList<Robot> getRobots() {
        ObservableList<Robot> robots = FXCollections.observableArrayList();
        robots.addAll(iArmazemLN.gerirRobots().values());
        return robots;
    }
}
