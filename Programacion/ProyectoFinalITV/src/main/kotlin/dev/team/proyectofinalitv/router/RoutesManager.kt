package dev.team.proyectofinalitv.router

import dev.team.proyectofinalitv.AppMain
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Modality
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

    private const val logoPath = "image/logo-white.png"

    enum class TypeView(val pathFxml: String) {
        MAIN("views/main-view.fxml"),
        CREAR_CITA("views/crear-cita-view.fxml"),
        ACERCA_DE("views/acerca-view.fxml"),
        UPDATE_CITA("views/modificar-cita-view.fxml"),
    }

    fun initMainView(stage: Stage) {
        logger.debug { "Inicializando MainStage" }

        // Cargamos el fxml para poder leerlo del resources (es estático)
        val fxmlLoader = FXMLLoader(getResource(TypeView.MAIN.pathFxml))

        // Configuración de la escena
        val scene = Scene(fxmlLoader.load(), 978.0, 700.0)
        stage.title = "ITV"
        stage.isResizable = false

        // Añadir icono desde los recursos
        val iconStream = getResourceAsStream(logoPath)
        val icon = Image(iconStream)
        stage.icons.add(icon)

        // Control de Stages y Escenas
        stage.scene = scene
        _mainStage = stage
        _activeStage = stage

        // Mostrarla
        _mainStage.show()
    }

    // Ventana (AcercaDe): Será ventana nueva Modal
    fun initAcercaDeStage() {
        logger.debug { "Inicializando AcercaDeStage" }

        // Cargamos el fxml para poder leerlo del resources (es estático)
        val fxmlLoader = FXMLLoader(getResource(TypeView.ACERCA_DE.pathFxml))

        // Configuración de la escena
        val scene = Scene(fxmlLoader.load(), 492.0, 328.0)
        // Es nueva ventana = nueva stage
        val stage = Stage()
        stage.title = "Acerca De AEA"
        stage.isResizable = false

        // Añadir icono desde los recursos
        val iconStream = getResourceAsStream(logoPath)
        val icon = Image(iconStream)
        stage.icons.add(icon)

        // Control de Stages y Escenas
        stage.scene = scene
        stage.initOwner(_mainStage)
        stage.initModality(Modality.WINDOW_MODAL)

        // Mostrarla
        stage.show()
    }

    // Ventana de edición: ventana nueva Modal
    fun initEditarCita() {
        logger.debug { "Inicializando Edición de Cita" }

        // Cargamos el fxml para poder leerlo del resources (es estático)
        val fxmlLoader = FXMLLoader(getResource(TypeView.UPDATE_CITA.pathFxml))

        // Configuración de la escena
        val scene = Scene(fxmlLoader.load(), 536.0, 604.0)
        // Es nueva ventana = nueva stage
        val stage = Stage()
        stage.title = "Edición de cita"
        stage.isResizable = false

        // Añadir icono desde los recursos
        val iconStream = getResourceAsStream(logoPath)
        val icon = Image(iconStream)
        stage.icons.add(icon)

        // Control de Stages y Escenas
        stage.scene = scene
        stage.initOwner(_mainStage)
        stage.initModality(Modality.WINDOW_MODAL)

        // Mostrarla
        stage.show()
    }

    // Ventana de creación de cita: ventana modal
    fun initCrearCita() {
        logger.debug { "Inicializando Creación de Cita" }

        // Cargamos el fxml para poder leerlo del resources (es estático)
        val fxmlLoader = FXMLLoader(getResource(TypeView.CREAR_CITA.pathFxml))

        // Configuración de la escena
        val scene = Scene(fxmlLoader.load(), 520.0, 620.0)
        // Es nueva ventana = nueva stage
        val stage = Stage()
        stage.title = "Creación de cita"
        stage.isResizable = false

        // Añadir icono desde los recursos
        val iconStream = getResourceAsStream(logoPath)
        val icon = Image(iconStream)
        stage.icons.add(icon)

        // Control de Stages y Escenas
        stage.scene = scene
        stage.initOwner(_mainStage)
        stage.initModality(Modality.WINDOW_MODAL)

        // Mostrarla
        stage.show()
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
