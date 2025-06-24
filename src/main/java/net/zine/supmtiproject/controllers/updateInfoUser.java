package net.zine.supmtiproject.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.zine.supmtiproject.DAO.UserDao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class updateInfoUser {

    private int idClient;

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @FXML
    private Label errorLabel;
    @FXML
    private TextField Password;
    @FXML
    private TextField UserName;
    @FXML
    private TextField age;
    @FXML
    private TextField city;
    @FXML
    private TextField fullName;

    public void loadData() throws SQLException {
        UserDao userDao = new UserDao();
        ResultSet rs = userDao.getClientInfo(idClient);
        if (rs.next()) {
            Password.setText(rs.getString("password"));
            UserName.setText(rs.getString("username"));
            age.setText(rs.getString("age_client"));
            city.setText(rs.getString("ville_client"));
            fullName.setText(rs.getString("full_name"));
        }
    }

    @FXML
    public void refreshPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/net/zine/supmtiproject/InfoUpdate.fxml"));
            updateInfoUser controller = new updateInfoUser();
            controller.setIdClient(idClient);
            loader.setController(controller);
            Parent root = loader.load();

            controller.loadData(); // call after loading

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void Update(ActionEvent actionEvent) throws SQLException {
        UserDao userDao = new UserDao();

        if (fullName.getText().isEmpty() || UserName.getText().isEmpty() ||
                city.getText().isEmpty() || Password.getText().isEmpty()) {
            errorLabel.setText("Please fill all fields.");
        } else if (!age.getText().matches("\\d+")) {
            errorLabel.setText("Invalid age.");
        } else {
            userDao.UpdateUser(idClient, UserName.getText(), Password.getText(),
                    fullName.getText(), city.getText(), Integer.parseInt(age.getText()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Vos informations ont été mises à jour avec succès.");
            alert.showAndWait();

            refreshPage(actionEvent);
        }
    }


}
