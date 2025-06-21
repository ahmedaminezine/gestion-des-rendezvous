package net.zine.supmtiproject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.zine.supmtiproject.DAO.UserDao;
import net.zine.supmtiproject.DAO.rendezvousDao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationController extends clientMainController {

    @FXML private TextField patientNameField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> timePicker;
    @FXML private TextArea reasonField;
    @FXML private Label infoLabel;
    private int idclient =2;

    private rendezvousDao rendezvousDao = new rendezvousDao();
    private UserDao userDao = new UserDao();

    private final List<String> allHours = Arrays.asList(
            "08:00", "09:00", "10:00", "11:00",
            "14:00", "15:00", "16:00", "17:00"
    );

    @FXML
    private void initialize() {
        datePicker.setOnAction(event -> updateAvailableHours());
    }

    private void updateAvailableHours() {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate == null) return;

        List<String> reserved = getReservedHoursForDate(selectedDate);

        List<String> available = allHours.stream()
                .filter(hour -> !reserved.contains(hour))
                .collect(Collectors.toList());

        timePicker.getItems().clear();
        timePicker.getItems().addAll(available);

        if (available.isEmpty()) {
            infoLabel.setText("Aucune heure disponible pour cette date.");
        } else {
            infoLabel.setText("");
        }
    }

    // Simule les heures déjà prises pour une date
    private List<String> getReservedHoursForDate(LocalDate date) {
        // À remplacer par une vraie requête base de données
        if (date.equals(LocalDate.now().plusDays(1))) {
            return List.of("10:00", "14:00", "15:00");
        }
        return List.of();
    }

    @FXML
    private void confirmAppointment() throws SQLException {
        LocalDate selectedDate = datePicker.getValue();
        String selectedTime = timePicker.getValue();
        List<String> hours = rendezvousDao.invalidHoure(selectedDate);
        if (hours.contains(selectedTime)) {
            infoLabel.setText("ce temps est reserve");
        }
        else {

            rendezvousDao.insertrendezvous(idclient,selectedDate, selectedTime, reasonField.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null); // optional
            alert.setContentText("redez-vous reserve");
            alert.showAndWait();
        }

    }
}

