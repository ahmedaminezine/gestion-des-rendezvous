package net.zine.supmtiproject.controllers;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/net/zine/supmtiproject/reservationForm.fxml"));
            AnchorPane view = loader.load();

            ReservationController controller = loader.getController();

            MainPanel.getChildren().setAll(view);
            controller.idpersonne(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void gotoMesrondezVous(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/net/zine/supmtiproject/mesredezvous.fxml"));
            loader.setControllerFactory(param -> {
                if (param == MesrendezvousController.class) {
                    return new MesrendezvousController(id);
                } else {
                    try {
                        return param.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            AnchorPane view = loader.load();

            MainPanel.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void desconnected(ActionEvent event) {
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

    public void updateInfo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/net/zine/supmtiproject/InfoUpdate.fxml"));

            loader.setControllerFactory(param -> {
                if (param == updateInfoUser.class) {
                    updateInfoUser controller = new updateInfoUser();
                    controller.setIdClient(id);  // <-- your user ID
                    return controller;
                } else {
                    try {
                        return param.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            AnchorPane view = loader.load();

            // Call loadData() manually to initialize the user info
            updateInfoUser controller = loader.getController();
            controller.loadData();

            MainPanel.getChildren().setAll(view);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


}
