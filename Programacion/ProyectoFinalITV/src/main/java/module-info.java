module dev.team.proyectofinalitv {
    requires kotlin.stdlib;
    requires java.desktop;

    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;

    // Logger
    requires io.github.microutils.kotlinlogging;
    requires koin.logger.slf4j;
    requires org.slf4j;

    // Koin
    requires koin.core.jvm;
    requires com.google.gson;
    requires kotlin.result.jvm;
    requires java.sql;

    // Root
    opens dev.team.proyectofinalitv to javafx.fxml;
    exports dev.team.proyectofinalitv;

    // Controller
    opens dev.team.proyectofinalitv.controllers to javafx.fxml;
    exports dev.team.proyectofinalitv.controllers;
}