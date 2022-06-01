package SGA.Controller;

import SGA.Business.IArmazemLN;
import SGA.Business.Localizacao;
import SGA.SistemaGestaoArmazemApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class LocalizacaoPaletesController {
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
                new PropertyValueFactory<Localizacao, String>("seccao")
        );
        observableList.get(2).setCellValueFactory(
                new PropertyValueFactory<Localizacao, String>("prateleira")
        );


        table.setItems(getLocalizacoes());
        table.refresh();
    }

    private ObservableList<Localizacao> getLocalizacoes() {
        ObservableList<Localizacao> localizacoes = FXCollections.observableArrayList();
        localizacoes.addAll(iArmazemLN.gerirPaletes().values());
        return localizacoes;
    }
}

