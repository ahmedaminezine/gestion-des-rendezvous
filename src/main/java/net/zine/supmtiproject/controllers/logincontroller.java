package net.zine.supmtiproject.controllers;
import javafx.fxml.FXMLLoader;
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

        if(userDao.validateLogin(userNameText.getText(), passwordText.getText()).next()){
            ErrorLabel.setStyle(ErrorLabel.getStyle()+" -fx-text-fill: #002aff;");
            ErrorLabel.setText("Veuillez entrer votre mot de passe !");
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