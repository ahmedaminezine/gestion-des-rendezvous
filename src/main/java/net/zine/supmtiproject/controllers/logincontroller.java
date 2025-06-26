package net.zine.supmtiproject.controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import net.zine.supmtiproject.DAO.UserDao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class logincontroller {

    @FXML
    private Button Login;
    @FXML
    private TextField userNameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Label ErrorLabel;
    @FXML
    private AnchorPane loginData;

    public void login(ActionEvent event) throws SQLException {
        UserDao userDao = new UserDao();
        ResultSet user = userDao.validateLogin(userNameText.getText(), passwordText.getText());
        if(user.next()){
            if(userDao.getMedcineId(user.getInt("id")) != 0){
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/net/zine/supmtiproject/medecin.fxml"));
                    Parent root = fxmlLoader.load();
                    MedecinController controller = fxmlLoader.getController();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                    controller.initialize(user.getInt("id"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/net/zine/supmtiproject/Home.fxml"));
                    Parent root = fxmlLoader.load();
                    clientMainController controller = fxmlLoader.getController();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                    controller.getuser(user.getInt("id"), user.getString("username"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        else {
            ErrorLabel.setStyle("-fx-text-fill: red;");
            ErrorLabel.setText("invalid login, please try again !");
        }

    }

    public void signup(ActionEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("/net/zine/supmtiproject/signup.fxml"));
            loginData.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}