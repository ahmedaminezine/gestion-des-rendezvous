package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;


public class Main extends Application {
    private AppointmentManager manager = new AppointmentManager();
    private ListView<String> appointmentListView = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(" Gestion des rendez-vous");

        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Nom du client");

        DatePicker datePicker = new DatePicker();

        TextField timeTextField = new TextField();
        timeField.setPromptText("Heure (HH:MM)");

        Button addButton = new Button("Ajouter");
        addButton.setOnAction(e -> {
            String name = nameTextField.getText();
            String date = datePicker.getValue() != null ? datePicker.getValue().toString() : "";
            String time = timeTextField.getText();

            if (!name.isEmpty() && !date.isEmpty() && !time.isEmpty()) {
                Appointment app = new Appointment(name, date, time);
                manager.addAppointment(appt);
                updateListeView();
                nameField.clear();
                timeField.clear();
            }
        });

    public static void main(String[] args) {
        launch(args);
    }
}
