package dev.team.proyectofinalitv.router

import dev.team.proyectofinalitv.AppMain
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import mu.KotlinLogging
import java.io.InputStream
import java.net.URL

object RoutesManager {

    private val logger = KotlinLogging.logger {}

    lateinit var app: AppMain

    private lateinit var _mainStage: Stage
    private lateinit var _activeStage: Stage

    fun getActiveStage(): Stage {
        return _activeStage
    }

    enum class TypeView(val pathFxml: String) {
        MAIN("views/main-view.fxml"),
        ACERCA_DE("views/acerca-view.fxml"),
        UPDATE_CITA("views/update-cita-view.fxml"),
    }

    fun initMainView(stage: Stage) {
        logger.debug { "Inicializando MainStage" }

        // Cargamos el fxml para poder leerlo del resources (es estático)
        val fxmlLoader = FXMLLoader(getResource(TypeView.MAIN.pathFxml))

        // Pasar instancia del controlador al FXMLLoader
        //fxmlLoader.setController(mainVieController)

        // Configuración de la escena
        val scene = Scene(fxmlLoader.load(), 808.0, 486.0)
        stage.title = "ITV"
        stage.isResizable = false

        // Añadir icono desde los recursos
        val iconStream = getResourceAsStream("icons/about.png")
        val icon = Image(iconStream)
        stage.icons.add(icon)

        // Control de Stages y Escenas
        stage.scene = scene
        _mainStage = stage
        _activeStage = stage

        // Mostrarla
        _mainStage.show()
    }

    /**
     * Para tener de manera genérica la llamada a un archivo de la carpeta resources y ahorrarnos código
     * @param resource el recurso que querremos recoger de la carpeta resources
     */
    fun getResource(resource: String): URL {
        return AppMain::class.java.getResource(resource)
            ?: throw RuntimeException("No se ha encontrado el recurso: $resource")
    }

    /**
     * Para tener de manera genérica la llamada a un archivo de la carpeta resources y ahorrarnos código,
     * en este caso como 'Stream'
     * @param resource el recurso que querremos recoger de la carpeta resources
     */
    fun getResourceAsStream(resource: String): InputStream {
        return AppMain::class.java.getResourceAsStream(resource)
            ?: throw RuntimeException("No se ha encontrado el recurso como stream: $resource")
    }
}
