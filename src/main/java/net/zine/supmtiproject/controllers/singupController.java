package net.zine.supmtiproject.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import net.zine.supmtiproject.DAO.UserDao;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.SQLException;

public class singupController {
    @FXML
    private TextField age;
    @FXML
    private TextField fullName;
    @FXML
    private TextField userName;
    @FXML
    private TextField city;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmpassword;
    @FXML
    private Label errorLabel;

    UserDao userDao = new UserDao();

    public void back(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/net/zine/supmtiproject/loginForm.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signUp(ActionEvent event) throws SQLException {
        errorLabel.setStyle("-fx-text-fill: red;");
        if(fullName.getText().isEmpty() || userName.getText().isEmpty() || city.getText().isEmpty() || password.getText().isEmpty() || confirmpassword.getText().isEmpty()){
            errorLabel.setText("please fill all cases");
        }
        else if(!age.getText().matches("\\d+")){
            errorLabel.setText("inavalable age");
        }
        else if(!confirmpassword.getText().equals(password.getText())){
            errorLabel.setText("incorrect password");
        }
        else{
            userDao.insertUser(userName.getText(), password.getText(), fullName.getText(), city.getText(), Integer.parseInt(age.getText()));
            errorLabel.setStyle("-fx-text-fill: green;");
            errorLabel.setText("user added");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null); // optional
            alert.setContentText("Vous etes inscrit avec succées");
            alert.showAndWait();
            back(event);
        }

    }


}
