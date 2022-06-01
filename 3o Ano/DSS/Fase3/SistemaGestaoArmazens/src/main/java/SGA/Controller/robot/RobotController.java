package SGA.Controller.robot;

import SGA.Business.IArmazemLN;
import SGA.Business.Localizacao;
import SGA.Business.OrdemTransporte;
import SGA.Controller.Redirect;
import SGA.SistemaGestaoArmazemApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class RobotController {
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
        table.setRowFactory(tv -> {
            TableRow<OrdemTransporte> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    OrdemTransporte rowData = row.getItem();

                    Preferences prefs = Preferences.userNodeForPackage(SistemaGestaoArmazemApplication.class);
                    prefs.putInt("ordemTransporte", rowData.getId());

                    try {
                        Redirect.redirectTo(back, "/views/robot/opcoesOrdemTransporte.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        ObservableList<TableColumn> observableList = table.getColumns();

        observableList.get(0).setCellValueFactory(
                new PropertyValueFactory<Localizacao, String>("id")
        );
        observableList.get(1).setCellValueFactory(
                new PropertyValueFactory<Localizacao, String>("tipo")
        );
        observableList.get(2).setCellValueFactory(
                new PropertyValueFactory<Localizacao, String>("Localizacao_id")
        );
        observableList.get(3).setCellValueFactory(
                new PropertyValueFactory<Localizacao, String>("Gestor_id")
        );


        table.setItems(getOrdensTransportePendentes());
        table.refresh();
    }

    private ObservableList<OrdemTransporte> getOrdensTransportePendentes() {
        ObservableList<OrdemTransporte> ordensTransporte = FXCollections.observableArrayList();
        ordensTransporte.addAll(iArmazemLN.ordensTransportePendentes().values());
        return ordensTransporte;
    }
}
