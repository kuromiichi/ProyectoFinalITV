package dev.team.proyectofinalitv.controllers

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dev.team.proyectofinalitv.mappers.parseTipoVehiculo
import dev.team.proyectofinalitv.models.Cita
import dev.team.proyectofinalitv.router.RoutesManager
import dev.team.proyectofinalitv.router.RoutesManager.getResourceAsStream
import dev.team.proyectofinalitv.viewmodels.CitaViewModel
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.Cursor
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.FileChooser
import javafx.util.Callback
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.format.DateTimeFormatter

class MainViewController : KoinComponent {

    private val logger = KotlinLogging.logger {}

    private val citaViewModel: CitaViewModel by inject()

    // Menu

    @FXML
    private lateinit var menuAcercaDe: MenuItem

    @FXML
    private lateinit var menuCrearCita: MenuItem

    @FXML
    private lateinit var menuExportarCitas: MenuItem


    // Logo

    @FXML
    private lateinit var logo: ImageView

    // Buscador

    @FXML
    private lateinit var textBuscadorByMatricula: TextField

    @FXML
    private lateinit var comboTipoVehiculoFiltradoTabla: ComboBox<String>

    @FXML
    lateinit var datePickerFilter: DatePicker

    // Tabla de citas

    @FXML
    private lateinit var tableCitas: TableView<Cita>

    @FXML
    private lateinit var columnId: TableColumn<Cita, Long>

    @FXML
    private lateinit var columnEstado: TableColumn<Cita, String>

    @FXML
    private lateinit var columnFecha: TableColumn<Cita, String>

    @FXML
    private lateinit var columnHora: TableColumn<Cita, String>

    @FXML
    private lateinit var columnMatricula: TableColumn<Cita, String>

    // Formulario
    //Cita

    @FXML
    private lateinit var textCitaId: TextField

    @FXML
    private lateinit var textCitaEstado: TextField

    @FXML
    private lateinit var textCitaFecha: TextField

    @FXML
    private lateinit var textCitaHora: TextField

    // Trabajador

    @FXML
    private lateinit var textTrabajadorNombre: TextField

    @FXML
    private lateinit var textTrabajadorCorreo: TextField

    @FXML
    private lateinit var textTrabajadorTelefono: TextField

    @FXML
    private lateinit var textTrabajadorEspecialidad: TextField

    // Propietario

    @FXML
    private lateinit var textPropietarioDni: TextField

    @FXML
    private lateinit var textPropietarioNombre: TextField

    @FXML
    private lateinit var textPropietarioTelefono: TextField

    @FXML
    private lateinit var textPropietarioCorreo: TextField

    // Vehículo

    @FXML
    private lateinit var textVehiculoMatricula: TextField

    @FXML
    private lateinit var textVehiculoMarca: TextField

    @FXML
    private lateinit var textVehiculoModelo: TextField

    @FXML
    private lateinit var textVehiculoMatriculacion: TextField

    @FXML
    private lateinit var textVehiculoRevision: TextField

    @FXML
    private lateinit var textVehiculoMotor: TextField

    @FXML
    private lateinit var textVehiculoTipo: TextField

    // Informe

    @FXML
    private lateinit var textInformeFrenado: TextField

    @FXML
    private lateinit var textInformeContaminacion: TextField

    @FXML
    private lateinit var textInformeInterior: TextField

    @FXML
    private lateinit var textInformeLuces: TextField

    @FXML
    private lateinit var textInformeResultado: TextField

    // Botones del formulario

    @FXML
    private lateinit var buttonEditarCita: Button

    @FXML
    private lateinit var buttonExportarCitaHtml: Button

    @FXML
    private lateinit var buttonExportarCitaJson: Button

    private lateinit var textFields: List<TextField>


    @FXML
    private fun initialize() {
        logger.debug { "Inicializando vista principal" }

        initTextFields()
        initDetails()
        initBindings()
        initEvents()
    }

    /**
     * Inicializa los campos de texto del formulario
     */
    private fun initTextFields() {
        textFields = listOf(
            textCitaId,
            textCitaEstado,
            textCitaFecha,
            textCitaHora,
            textTrabajadorNombre,
            textTrabajadorCorreo,
            textTrabajadorTelefono,
            textTrabajadorEspecialidad,
            textPropietarioDni,
            textPropietarioNombre,
            textPropietarioTelefono,
            textPropietarioCorreo,
            textVehiculoMatricula,
            textVehiculoMarca,
            textVehiculoModelo,
            textVehiculoMatriculacion,
            textVehiculoRevision,
            textVehiculoMotor,
            textVehiculoTipo,
            textInformeFrenado,
            textInformeContaminacion,
            textInformeInterior,
            textInformeLuces,
            textInformeResultado
        )
    }

    /**
     * Bloquea los campos de texto del formulario
     */
    private fun lockTextFields() {
        logger.debug { "Bloqueando campos" }

        textFields.forEach { it.isEditable = false }
    }

    /**
     * Establece la opacidad de los campos de texto del formulario
     */
    private fun setOpacity() {
        logger.debug { "Estableciendo opacidad de los campos de texto" }

        textFields.forEach { it.opacity = 0.75 }
    }

    /**
     * Inicializa los detalles de la vista (logo, bloquear campos, modificar opacidad)
     */
    private fun initDetails() {
        logger.debug { "Inicializando detalles" }

        logo.image = Image(getResourceAsStream("image/logo.png"))

        lockTextFields()
        setOpacity()
    }

    private fun initBindings() {
        logger.debug { "Inicializando bindings" }

        // Filtrado por tipo de vehículo
        comboTipoVehiculoFiltradoTabla.items =
            FXCollections.observableArrayList(citaViewModel.state.value.tipoVehiculoFilter)
        comboTipoVehiculoFiltradoTabla.selectionModel.selectFirst()

        // Tabla (selector de cita)
        tableCitas.items = FXCollections.observableArrayList(citaViewModel.state.value.citas)
        tableCitas.selectionModel.selectionMode = SelectionMode.SINGLE

        columnId.cellValueFactory = PropertyValueFactory("id")
        columnEstado.cellValueFactory = PropertyValueFactory("estado")
        columnFecha.cellValueFactory =
            Callback<TableColumn.CellDataFeatures<Cita, String>, ObservableValue<String>> {
                val fecha = it.value.fechaHora.toLocalDate()
                val fechaString = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                SimpleStringProperty(fechaString)
            }
        columnHora.cellValueFactory =
            Callback<TableColumn.CellDataFeatures<Cita, String>, ObservableValue<String>> {
                val hora = it.value.fechaHora.toLocalTime()
                val horaString = hora.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                SimpleStringProperty(horaString)
            }
        columnMatricula.cellValueFactory = PropertyValueFactory("matriculaVehiculo")

        // Observable State
        citaViewModel.state.addListener { _, oldState, newState ->
            updateTable(oldState, newState)
            updateForm(oldState, newState)
        }
    }

    private fun initEvents() {
        logger.debug { "Inicializando eventos" }

        // Menu
        // Ventana de Acerca de
        menuAcercaDe.setOnAction {
            onAcercaDeAction()
        }
        // Crear cita
        menuCrearCita.setOnAction {
            onCrearCitaAction()
        }
        // Exportar todas las cita
        menuExportarCitas.setOnAction {
            onExportAll()
        }

        tableCitas.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            if (newValue != null) {
                onCitaSeleccionada(newValue)
            }
        }

        // Buscador matrícula
        textBuscadorByMatricula.setOnKeyReleased { newValue ->
            newValue?.let { onFilterDataTable() }
        }

        // Combo
        comboTipoVehiculoFiltradoTabla.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { onFilterDataTable() }
        }

        // Buscador fecha
        datePickerFilter.valueProperty().addListener { _, _, newValue ->
            newValue?.let { onFilterDataTable() }
        }

        // Cuando el usuario quiera borrar
        datePickerFilter.editor.textProperty().addListener { _, _, newValue ->
            if (newValue.isNullOrBlank()) {
                datePickerFilter.value = null
            }
            onFilterDataTable()
        }

        // Botones
        buttonEditarCita.setOnAction {
            onEditarCitaAction()
        }
        buttonExportarCitaJson.setOnAction {
            onExportarActionJson()
        }
        buttonExportarCitaHtml.setOnAction {
            onExportarActionMarkDown()
        }
    }

    /**
     * Al introducirnos en el menú y decidir crear, al State le indicamos que vamos a crear, si no tenemos
     * y continuará el proceso de demostrar la ventana de creación de DETAILS.
     */
    private fun onCrearCitaAction() {
        logger.debug { "Creando una cita" }

        RoutesManager.initCrearCita()
    }

    /**
     * Al pulsar el botón de editar, al State le indicamos que vamos a editar, si no tenemos una cita seleccionada en la
     * tabla, no se continuará el proceso de demostrar la ventana de edición de DETAILS.
     */
    private fun onEditarCitaAction() {
        logger.debug { "Editando una cita" }

        // Si no tenemos un item seleccionado, no continuamos editando
        if (tableCitas.selectionModel.selectedItem == null) {
            return
        }

        RoutesManager.initEditarCita()
    }

    /**
     * Filtrado de la tabla en función de la matrícula, fecha y tipo de vehículo
     */
    private fun onFilterDataTable() {
        logger.debug { "Filtrando la tabla de citas" }

        val tipoSeleccionado = parseTipoVehiculo(comboTipoVehiculoFiltradoTabla.value)
        val matricula = textBuscadorByMatricula.text.trim()
        val fechaSeleccionada = datePickerFilter.value

        println(fechaSeleccionada)
        val citasFiltradas = citaViewModel.citaFilteredList(tipoSeleccionado, matricula, fechaSeleccionada)

        tableCitas.items = FXCollections.observableList(citasFiltradas)
    }

    private fun updateTable(oldState: CitaViewModel.CitaState, newState: CitaViewModel.CitaState) {
        logger.debug { "Actualizando tabla de citas" }

        if (oldState.citas != newState.citas) {
            tableCitas.selectionModel.clearSelection()
            tableCitas.items = FXCollections.observableArrayList(newState.citas)
        }
    }

    private fun updateForm(oldState: CitaViewModel.CitaState, newState: CitaViewModel.CitaState) {
        logger.debug { "Actualizando estado del formulario" }

        if (oldState.citaSeleccionada != newState.citaSeleccionada) {
            val apto = "Apto"
            val noApto = "No Apto"

            textCitaId.text = newState.citaSeleccionada.idCita
            textCitaEstado.text = newState.citaSeleccionada.estadoCita
            textCitaFecha.text = newState.citaSeleccionada.fechaCita.toString()
            textCitaHora.text = newState.citaSeleccionada.horaCita.toString()
            textTrabajadorNombre.text = newState.citaSeleccionada.nombreTrabajador
            textTrabajadorCorreo.text = newState.citaSeleccionada.correoTrabajador
            textTrabajadorTelefono.text = newState.citaSeleccionada.telefonoTrabajador
            textTrabajadorEspecialidad.text = newState.citaSeleccionada.especialidadTrabajador.lowercase()
            textPropietarioDni.text = newState.citaSeleccionada.dniPropietario
            textPropietarioNombre.text = newState.citaSeleccionada.nombrePropietario
            textPropietarioTelefono.text = newState.citaSeleccionada.telefonoPropietario
            textPropietarioCorreo.text = newState.citaSeleccionada.correoPropietario
            textVehiculoMatricula.text = newState.citaSeleccionada.matriculaVehiculo
            textVehiculoMarca.text = newState.citaSeleccionada.marcaVehiculo
            textVehiculoModelo.text = newState.citaSeleccionada.modeloVehiculo
            textVehiculoMatriculacion.text = newState.citaSeleccionada.fechaMatriculacionVehiculo.toString()
            textVehiculoRevision.text = newState.citaSeleccionada.fechaRevisionVehiculo.toString()
            textVehiculoMotor.text = newState.citaSeleccionada.tipoMotorVehiculo.lowercase()
            textVehiculoTipo.text = newState.citaSeleccionada.tipoVehiculo.lowercase()
            textInformeFrenado.text = newState.citaSeleccionada.frenadoInforme.toString()
            textInformeContaminacion.text = newState.citaSeleccionada.contaminacionInforme.toString()
            textInformeInterior.text = if (newState.citaSeleccionada.interiorInforme) apto else noApto
            textInformeLuces.text = if (newState.citaSeleccionada.lucesInforme) apto else noApto
            textInformeResultado.text = if (newState.citaSeleccionada.isAptoInforme) apto else noApto
        }
    }

    private fun onAcercaDeAction() {
        logger.debug { "Iniciando ventana Acerca de..." }
        RoutesManager.initAcercaDeStage()
    }

    /**
     * Exportar todas las citas almacenadas en la base de datos
     */
    private fun onExportAll() {
        logger.debug { "Exportando todas las citas" }

        val citaDtoExport = citaViewModel.mapperListCitaDtoToExport()

        val fileChooser = FileChooser()
        fileChooser.title = "Exportando todas las citas a JSON"
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
        val file = fileChooser.showSaveDialog(RoutesManager.getActiveStage())

        RoutesManager.getActiveStage().scene.cursor = Cursor.WAIT

        if (file != null) {
            citaViewModel.exportAllToJson(file, citaDtoExport).onSuccess {
                showAlertOperacion(
                    title = "Datos exportados a JSON",
                    header = "Datos exportados correctamente a fichero JSON",
                    mensaje = "Se han exportado todas las citas a JSON"
                )
            }.onFailure { error ->
                showAlertOperacion(
                    alerta = Alert.AlertType.ERROR,
                    title = "Error al exportar en JSON",
                    mensaje = error.message
                )
            }
            RoutesManager.getActiveStage().scene.cursor = Cursor.DEFAULT
        }
    }

    /**
     * Selección de una cita, asignando el valor al State
     */
    private fun onCitaSeleccionada(newValue: Cita) {
        logger.debug { "Seleccionando cita ${newValue.id}" }
        citaViewModel.updateCitaSeleccionada(newValue)
    }

    /**
     * Exportar una cita a JSON
     */
    private fun onExportarActionJson() {
        logger.debug { "Exportando cita en JSON" }

        val citaDtoExport = citaViewModel.mapperCitaDtoToExportDelEstado()

        val fileChooser = FileChooser()
        fileChooser.title = "Exportando cita en JSON"
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
        val file = fileChooser.showSaveDialog(RoutesManager.getActiveStage())

        RoutesManager.getActiveStage().scene.cursor = Cursor.WAIT

        if (file != null) {
            citaViewModel.exportToJson(file, citaDtoExport).onSuccess {
                showAlertOperacion(
                    title = "Datos exportados a JSON",
                    header = "Datos exportados correctamente a fichero JSON",
                    mensaje = "Se ha exportado la cita en JSON"
                )
            }.onFailure { error ->
                showAlertOperacion(
                    alerta = Alert.AlertType.ERROR,
                    title = "Error al exportar en JSON",
                    mensaje = error.message
                )
            }
            RoutesManager.getActiveStage().scene.cursor = Cursor.DEFAULT
        }
    }

    /**
     * Exportar una cita a HTML/Markdown
     */
    private fun onExportarActionMarkDown() {
        logger.debug { "Exportando cita en MarkDown" }

        val citaDtoExport = citaViewModel.mapperCitaDtoToExportDelEstado()

        val fileChooser = FileChooser()
        fileChooser.title = "Exportando cita en MarkDown"
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("MARKDOWN", "*.md"))
        val file = fileChooser.showSaveDialog(RoutesManager.getActiveStage())

        RoutesManager.getActiveStage().scene.cursor = Cursor.WAIT

        if (file != null) {
            citaViewModel.exportToMarkDown(file, citaDtoExport).onSuccess {
                showAlertOperacion(
                    title = "Datos exportados a MarkDown",
                    header = "Datos exportados correctamente a fichero MarkDown",
                    mensaje = "Se ha exportado la cita en MarkDown"
                )
            }.onFailure { error ->
                showAlertOperacion(
                    alerta = Alert.AlertType.ERROR,
                    title = "Error al exportar en MarkDown",
                    mensaje = error.message
                )
            }
            RoutesManager.getActiveStage().scene.cursor = Cursor.DEFAULT
        }
    }

    /**
     * Muestra las alertas
     */
    private fun showAlertOperacion(
        alerta: Alert.AlertType = Alert.AlertType.CONFIRMATION,
        title: String = "",
        header: String = "",
        mensaje: String = "",
    ) {
        Alert(alerta).apply {
            this.title = title
            this.headerText = header
            this.contentText = mensaje
        }.showAndWait()
    }
}
