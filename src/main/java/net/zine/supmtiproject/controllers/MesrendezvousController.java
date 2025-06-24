package net.zine.supmtiproject.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.zine.supmtiproject.DAO.MesrendezvouDao;
import net.zine.supmtiproject.DAO.UserDao;
import net.zine.supmtiproject.Model.RendezVous;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class MesrendezvousController {
    @FXML
    private TableColumn<RendezVous, Date> dateColumn;
    @FXML
    private TableColumn<RendezVous, String> heureColumn;
    @FXML
    private TableColumn<RendezVous, String> maladieColumn;
    @FXML
    private TableColumn<RendezVous, String> medcineClumn;

    @FXML
    private TableView<RendezVous> TableRendezVous;

    private int idpersonne;
//    private  int i =13;
//    private UserDao userDao;
//    private MesrendezvouDao mesrendezvouDao;

    public MesrendezvousController(int idpersonne){
        this.idpersonne = idpersonne;
    }
    @FXML
    private void initialize() {
        try {
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            heureColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));
            maladieColumn.setCellValueFactory(new PropertyValueFactory<>("maladie"));
            medcineClumn.setCellValueFactory(new PropertyValueFactory<>("medcine"));

            UserDao userDao = new UserDao();
            MesrendezvouDao mesrendezvouDao = new MesrendezvouDao();
            int ClientId = userDao.getClientId(idpersonne);
            ResultSet resultSet = mesrendezvouDao.getRendezVousByIdClient(ClientId);
            ObservableList<RendezVous> list = FXCollections.observableArrayList();

            while (resultSet.next()) {
                Date date = resultSet.getDate("date_rendezvous");
                String horaire = resultSet.getString("horaire");
                String maladie = resultSet.getString("maladie");
                int idmedcine = resultSet.getInt("id_medcine");
                String medcine = userDao.getmedcineNameById(idmedcine);

                RendezVous rdv = new RendezVous(date, horaire, maladie, medcine);
                list.add(rdv);
            }

            TableRendezVous.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
