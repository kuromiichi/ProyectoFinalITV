package dev.team.proyectofinalitv

import dev.team.proyectofinalitv.di.koinModule
import dev.team.proyectofinalitv.router.RouterManager
import javafx.application.Application
import javafx.stage.Stage
import org.koin.core.context.startKoin

class AppMain : Application() {
    override fun start(stage: Stage) {
        // Inicialización del inyector de dependencias
        startKoin {
            printLogger()
            modules(koinModule)
        }

        // Carga de la ventana principal
        RouterManager.apply { app = this@AppMain }
            .run { initMainView(stage) }
    }
}

fun main() {
    Application.launch(AppMain::class.java)
}
