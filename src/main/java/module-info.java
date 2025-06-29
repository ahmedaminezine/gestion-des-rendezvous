module net.zine.supmtiproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.desktop;
    requires com.fasterxml.jackson.annotation;
    requires mysql.connector.j;

    opens net.zine.supmtiproject to javafx.fxml;
    opens net.zine.supmtiproject.controllers to javafx.fxml;
    opens net.zine.supmtiproject.Model to javafx.base;
    exports net.zine.supmtiproject;
    exports net;
    opens net to javafx.fxml;
}