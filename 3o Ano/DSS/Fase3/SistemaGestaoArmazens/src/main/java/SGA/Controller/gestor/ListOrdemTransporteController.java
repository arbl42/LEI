package SGA.Controller.gestor;

import SGA.Business.IArmazemLN;
import SGA.Business.Localizacao;
import SGA.Business.OrdemTransporte;
import SGA.SistemaGestaoArmazemApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ListOrdemTransporteController {
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
        ObservableList<TableColumn> observableList = table.getColumns();

        observableList.get(0).setCellValueFactory(
                new PropertyValueFactory<Localizacao, String>("id")
        );
        observableList.get(1).setCellValueFactory(
                new PropertyValueFactory<Localizacao, String>("estado")
        );
        observableList.get(2).setCellValueFactory(
                new PropertyValueFactory<Localizacao, String>("tipo")
        );
        observableList.get(3).setCellValueFactory(
                new PropertyValueFactory<Localizacao, String>("Localizacao_id")
        );
        observableList.get(4).setCellValueFactory(
                new PropertyValueFactory<Localizacao, String>("Robot_id")
        );
        observableList.get(5).setCellValueFactory(
                new PropertyValueFactory<Localizacao, String>("Gestor_id")
        );


        table.setItems(getOrdensTransporte());
        table.refresh();
    }

    private ObservableList<OrdemTransporte> getOrdensTransporte() {
        ObservableList<OrdemTransporte> ordemTransportes = FXCollections.observableArrayList();
        ordemTransportes.addAll(iArmazemLN.listOrdensTransporte().values());
        return ordemTransportes;
    }
}
