package dev.team.proyectofinalitv

import dev.team.proyectofinalitv.config.AppConfig
import dev.team.proyectofinalitv.controllers.ProbandoCosillasController
import dev.team.proyectofinalitv.di.koinModule
import dev.team.proyectofinalitv.repositories.CitaRepositoryImpl
import dev.team.proyectofinalitv.router.RouterManager
import dev.team.proyectofinalitv.services.database.DatabaseManagerImpl
import javafx.application.Application
import javafx.stage.Stage
import org.koin.core.context.startKoin

class AppMain : Application() {
    override fun start(stage: Stage) {

        // Dependencias resueltas con Koin
        startKoin {
            printLogger()
            modules(koinModule)
        }

        // === PRUEBAS para trastear===
        pruebasBackEnd() // Coméntalo, si solo quieres la interfaz

        RouterManager.initMainView(stage)
    }

    private fun pruebasBackEnd() {
        val configApp = AppConfig()
        val db = DatabaseManagerImpl(configApp)
        /*        val repoProp = PropietarioRepositoryImpl(db)
                val repoVeh = VehiculoRepositoryImpl(db)
                val repoInfor = InformeRepositoryImpl(db)*/
        val repoCita = CitaRepositoryImpl(db)
        //val vm = CitaViewModel(repoProp, repoVeh, repoInfor, repoCita)
        val cont = ProbandoCosillasController()
        cont.onSaveCita()

        println(repoCita.getAll().size)
        println(repoCita.findById(1))
        println(repoCita.deleteById(1))

        println(repoCita.findByMatricula("123ABC"))
        println(repoCita.getAll().size)
        println(repoCita.getAll().joinToString("\n"))
    }
}

fun main() {
    Application.launch(AppMain::class.java)
}
