package controllers;

import app.DBConnection;
import models.RendezVous;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class RendezVousController {

    @FXML private DatePicker datePicker;
    @FXML private TextField timeField; // format HH:mm
    @FXML private TextArea descriptionArea;

    @FXML
    private void handleSaveRdv() {
        LocalDate date = datePicker.getValue();
        String timeText = timeField.getText();
        String description = descriptionArea.getText();

        if (date == null || timeText.isEmpty() || description.isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis");
            return;
        }

        LocalTime time;
        try {
            time = LocalTime.parse(timeText);
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Erreur", "Le format de l'heure est invalide (HH:mm attendu)");
            return;
        }

        LocalDateTime dateHeure = LocalDateTime.of(date, time);

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO rendezvous (utilisateur_id, date_heure, description, statut) VALUES (?, ?, ?, ?)"
            );
            // Ici, on fixe utilisateur_id à 1 pour simplifier (à remplacer par utilisateur connecté)
            stmt.setInt(1, 1);
            stmt.setObject(2, dateHeure);
            stmt.setString(3, description);
            stmt.setString(4, "En attente");

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                showAlert(AlertType.INFORMATION, "Succès", "Rendez-vous ajouté avec succès");
                // Fermer la fenêtre
                datePicker.getScene().getWindow().hide();
            } else {
                showAlert(AlertType.ERROR, "Erreur", "Impossible d'ajouter le rendez-vous");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur", "Erreur lors de la connexion à la base");
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
