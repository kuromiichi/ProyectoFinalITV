package dev.team.proyectofinalitv

import dev.team.proyectofinalitv.config.AppConfig
import dev.team.proyectofinalitv.controllers.ProbandoCosillasController
import dev.team.proyectofinalitv.di.ModuleKoin
import dev.team.proyectofinalitv.repositories.CitaRepositoryImpl
import dev.team.proyectofinalitv.services.database.DatabaseManagerImpl
import javafx.application.Application
import javafx.stage.Stage
import org.koin.core.context.startKoin

class AppMain : Application() {
    override fun start(stage: Stage) {

        // Dependencias resueltas con Koin
        startKoin {
            printLogger()
            modules(ModuleKoin)
        }

        // A partir de aquí son pruebas
        val cont = ProbandoCosillasController()
        cont.onSaveCita()
        
        val configApp = AppConfig()
        val db = DatabaseManagerImpl(configApp)

        val repoCita = CitaRepositoryImpl(db)

        println(repoCita.findAll().size)
        println(repoCita.findById(1))
        println(repoCita.deleteById(1))

        println(repoCita.findByMatricula("123ABC"))
        println(repoCita.findAll().size)
        println(repoCita.findAll().joinToString("\n"))


        // Inicio del enrutador
        /*
         val fxmlLoader = FXMLLoader(AppMain::class.java.getResource("hello-view.fxml"))
         val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
         stage.title = "Hello!"
         stage.scene = scene
         stage.show()*/
    }
}

fun main() {
    Application.launch(AppMain::class.java)
}
