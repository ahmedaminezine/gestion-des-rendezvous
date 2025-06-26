package net.zine.supmtiproject.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import net.zine.supmtiproject.DAO.UserDao;
import net.zine.supmtiproject.DAO.rendezvousDao;
import net.zine.supmtiproject.Model.Appointment;

import java.io.IOException;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.List;


public class MedecinController {

    @FXML private DatePicker filterDatePicker;
    @FXML private TableView<Appointment> rdvTable;
    @FXML private TableColumn<Appointment, Integer> id;
    @FXML private TableColumn<Appointment, String> colName;
    @FXML private TableColumn<Appointment, String> colHour;
    @FXML private TableColumn<Appointment, LocalDate> date;
    @FXML private TableColumn<Appointment, String> colReason;
    @FXML private TableColumn<Appointment, String> colStatus;
    @FXML private TextArea reportArea;

    private UserDao userDao = new UserDao();
    private rendezvousDao rendezvousDao = new rendezvousDao();

    private int idUser;
    private int idrendezVous;
    @FXML
    public void initialize(int idu) throws SQLException {
        this.idUser = idu;
        int medcineId = userDao.getMedcineId(idUser);
        filterDatePicker.setValue(null);
        id.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPatientName()));
        colHour.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getHour()));
        date.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getDate()));
        colReason.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getReason()));
        colStatus.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCompteRendu()));

        rdvTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                idrendezVous = newVal.getId();
                if(newVal.getCompteRendu()==null){reportArea.setText("");}else {reportArea.setText(newVal.getCompteRendu());}
            }
        });
        filterDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal!=null){
                try {
                    List<Appointment> appointments = rendezvousDao.getRendezVousOfMedcineById(medcineId, newVal);
                    if (appointments != null) {
                        ObservableList<Appointment> list = FXCollections.observableArrayList(appointments);
                        rdvTable.setItems(list);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
            List<Appointment> appointments = rendezvousDao.getRendezVousOfMedcine(medcineId);
            if (appointments != null) {
                ObservableList<Appointment> list = FXCollections.observableArrayList(appointments);
                rdvTable.setItems(list);
            }
    }
    public void saveReport(ActionEvent event) throws SQLException {
        rendezvousDao.saveCompteRendu(idrendezVous, reportArea.getText());
        reportArea.setText("");
        initialize(idUser);
    }
    public void changeProfile(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/net/zine/supmtiproject/loginForm.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((MenuItem) event.getSource())
                    .getParentPopup()
                    .getOwnerWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void quitApp() {
        Platform.exit();
    }


}

