package net.zine.supmtiproject.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class clientMainController {
    private int id;
    private String username;
    @FXML
    private Label UserLabel;
    @FXML
    private AnchorPane MainPanel;

    public void getuser(int id, String username) {
        this.id = id;
        this.username = username;
        UserLabel.setText(username);


    }
    public void gotoreservation(ActionEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("/net/zine/supmtiproject/reservationForm.fxml"));
            MainPanel.getChildren().setAll(view);
            System.out.println(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
