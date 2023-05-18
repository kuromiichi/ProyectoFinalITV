package dev.team.proyectofinalitv

import dev.team.proyectofinalitv.config.AppConfig
import dev.team.proyectofinalitv.repositories.CitaRepositoryImpl
import dev.team.proyectofinalitv.services.database.SqlDelightClient
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class AppMain : Application() {
    override fun start(stage: Stage) {

        // ====== BORRAR DESPUES ======
        // Probando la implementación del servicio DATABASE
        val configApp = AppConfig()
        val sqld = SqlDelightClient(configApp)
        val repo = CitaRepositoryImpl(sqld)
        println(repo.findAll().size)
        println(repo.findAll().joinToString ("\n"))
        // ====== BORRAR DESPUES ======

        val fxmlLoader = FXMLLoader(AppMain::class.java.getResource("hello-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(AppMain::class.java)
}