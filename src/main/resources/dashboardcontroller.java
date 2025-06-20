package controllers;

import app.DBConnection;
import models.RendezVous;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

class DashboardController {

    @FXML private TableView<RendezVous> rendezVousTable;
    @FXML private TableColumn<RendezVous, LocalDateTime> colDateHeure;
    @FXML private TableColumn<RendezVous, String> colDescription;
    @FXML private TableColumn<RendezVous, String> colStatut;

    private ObservableList<RendezVous> rendezVousList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colDateHeure.setCellValueFactory(new PropertyValueFactory<>("dateHeure"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        loadRendezVous();
    }

    private void loadRendezVous() {
        rendezVousList.clear();
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM rendezvous ORDER BY date_heure DESC");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RendezVous rdv = new RendezVous(
                        rs.getInt("id"),
                        rs.getInt("utilisateur_id"),
                        rs.getTimestamp("date_heure").toLocalDateTime(),
                        rs.getString("description"),
                        rs.getString("statut")
                );
                rendezVousList.add(rdv);
            }
            rendezVousTable.setItems(rendezVousList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNewRdv() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/rendezvous_form.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Nouveau Rendez-vous");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Après fermeture de la fenêtre, recharger la liste
            loadRendezVous();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

