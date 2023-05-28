package dev.team.proyectofinalitv.controllers

import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dev.team.proyectofinalitv.models.Vehiculo
import dev.team.proyectofinalitv.router.RoutesManager
import dev.team.proyectofinalitv.validators.validate
import dev.team.proyectofinalitv.viewmodels.CitaViewModel
import javafx.collections.FXCollections
import javafx.scene.control.Alert.AlertType
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import mu.KotlinLogging
import javafx.scene.control.*
import javafx.stage.Stage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDate
import java.time.LocalTime

class CrearCitaViewController : KoinComponent {

    private val logger = KotlinLogging.logger {}


    private val citaViewModel: CitaViewModel by inject()

    @FXML
    private lateinit var dateCita: DatePicker

    @FXML
    private lateinit var comboCitaHora: ComboBox<String>

    @FXML
    private lateinit var comboCitaTrabajador: ComboBox<String>

    @FXML
    private lateinit var textPropietarioDni: TextField

    @FXML
    private lateinit var textPropietarioNombre: TextField

    @FXML
    private lateinit var textPropietarioApellidos: TextField

    @FXML
    private lateinit var textPropietarioCorreo: TextField

    @FXML
    private lateinit var textPropietarioTelefono: TextField

    @FXML
    private lateinit var textVehiculoMatricula: TextField

    @FXML
    private lateinit var textVehiculoMarca: TextField

    @FXML
    private lateinit var textVehiculoModelo: TextField

    @FXML
    private lateinit var dateVehiculoMatriculacion: DatePicker

    @FXML
    private lateinit var dateVehiculoRevision: DatePicker

    @FXML
    private lateinit var comboVehiculoMotor: ComboBox<String>

    @FXML
    private lateinit var comboVehiculoTipo: ComboBox<String>

    @FXML
    private lateinit var buttonGuardar: Button

    @FXML
    private lateinit var buttonLimpiar: Button

    @FXML
    private fun initialize() {
        logger.info("Inicializando la vista de crear cita")

        initBindings()
        initEvents()
    }

    private fun initBindings() {
        logger.debug { "Inicializando bindings" }

        // ComboBoxes
        comboCitaHora.items = FXCollections.observableArrayList(citaViewModel.crearState.value.horasDisponibles)
        comboCitaHora.selectionModel.selectFirst()

        comboVehiculoMotor.items =
            FXCollections.observableArrayList(Vehiculo.TipoMotor.values().map { it.toString().lowercase() })
        comboVehiculoMotor.value = null

        comboVehiculoTipo.items =
            FXCollections.observableArrayList(Vehiculo.TipoVehiculo.values().dropLast(1)
                .map { it.toString().lowercase() })
        comboVehiculoTipo.value = null

        comboCitaTrabajador.items =
            FXCollections.observableArrayList(citaViewModel.crearState.value.trabajadoresDisponibles)
        comboCitaTrabajador.selectionModel.selectFirst()

        citaViewModel.crearState.addListener { _, oldState, newState ->
            updateComboHoras(oldState, newState)
            updateComboTrabajadores(oldState, newState)
        }
    }

    private fun updateComboHoras(oldState: CitaViewModel.CrearCitaState, newState: CitaViewModel.CrearCitaState) {
        logger.debug { "Actualizando comboBox horas" }

        if (newState.horasDisponibles != oldState.horasDisponibles) {
            comboCitaHora.items = FXCollections.observableArrayList(newState.horasDisponibles)
        }
    }

    private fun updateComboTrabajadores(
        oldState: CitaViewModel.CrearCitaState, newState: CitaViewModel.CrearCitaState
    ) {
        logger.debug { "Actualizando comboBox trabajadores" }

        if (oldState.trabajadoresDisponibles != newState.trabajadoresDisponibles) {
            comboCitaTrabajador.items =
                FXCollections.observableArrayList(citaViewModel.crearState.value.trabajadoresDisponibles)
        }
    }

    private fun initEvents() {
        logger.debug { "Inicializando eventos" }

        buttonGuardar.setOnAction {
            guardarCita()
        }

        buttonLimpiar.setOnAction {
            limpiarFormulario()
        }

        dateCita.setOnAction {
            onFechaSeleccionada()
        }

        comboCitaHora.setOnAction {
            onHoraSeleccionada()
        }
    }

    /**
     * Al seleccionar una fecha actualizamos al State con las horas disponibles, para mostrar en el comboBox la
     * reactividad gracias al State
     */
    private fun onFechaSeleccionada() {
        logger.debug { "Fecha seleccionada" }

        citaViewModel.crearState.value = citaViewModel.crearState.value.copy(
            horasDisponibles = citaViewModel.getHorasDisponibles(dateCita.value)
        )
        comboCitaHora.value = null
        comboCitaTrabajador.value = null
    }

    /**
     * Al seleccionar una hora actualizamos al State con los trabajadores disponibles, para mostrar en el comboBox la
     * reactividad gracias al State
     */
    private fun onHoraSeleccionada() {
        logger.debug { "Hora seleccionada" }

        if (comboCitaHora.value != null) {
            citaViewModel.crearState.value = citaViewModel.crearState.value.copy(
                trabajadoresDisponibles = citaViewModel.getTrabajadoresDisponibles(
                    dateCita.value,
                    LocalTime.parse(comboCitaHora.value)
                )
            )
        }
        comboCitaTrabajador.value = null
    }

    private fun limpiarFormulario() {
        dateCita.value = null
        comboCitaHora.value = null
        comboCitaTrabajador.value = null
        textPropietarioDni.text = ""
        textPropietarioNombre.text = ""
        textPropietarioApellidos.text = ""
        textPropietarioCorreo.text = ""
        textPropietarioTelefono.text = ""
        textVehiculoMatricula.text = ""
        textVehiculoMarca.text = ""
        textVehiculoModelo.text = ""
        dateVehiculoMatriculacion.value = null
        dateVehiculoRevision.value = null
        comboVehiculoMotor.value = null
        comboVehiculoTipo.value = null
    }

    /**
     * Guardamos la cita recogiendo los datos del formulario y validando los datos, si es posible se guarda.
     */
    private fun guardarCita() {
        logger.debug { "Guardando cita" }

        val cita = CrearCitaFormulario(
            dateCita.value?.toString() ?: "",
            comboCitaHora.value ?: "",
            comboCitaTrabajador.value ?: "",
            textPropietarioDni.text,
            textPropietarioNombre.text,
            textPropietarioApellidos.text,
            textPropietarioCorreo.text,
            textPropietarioTelefono.text,
            textVehiculoMatricula.text,
            textVehiculoMarca.text,
            textVehiculoModelo.text,
            dateVehiculoMatriculacion.value?.toString() ?: "",
            dateVehiculoRevision.value?.toString() ?: "",
            comboVehiculoMotor.value ?: "",
            comboVehiculoTipo.value ?: ""
        )

        cita.validate().andThen { citaViewModel.saveCita(it) }
            .onSuccess { nuevaCita ->
                logger.debug { "Cita creada correctamente" }

                val citas = citaViewModel.state.value.citas.toMutableList()
                citas.add(nuevaCita)
                citaViewModel.state.value = citaViewModel.state.value.copy(citas = citas)

                showAlertOperacion(
                    AlertType.INFORMATION,
                    title = "Cita creada",
                    header = "Cita creada y almacenada correctamente",
                    mensaje = "Se ha salvado en el sistema"
                )

                val stage = buttonGuardar.scene.window as Stage
                stage.close()
            }.onFailure { error ->
                logger.debug { "Error al guardar la cita ${error.message}" }

                showAlertOperacion(
                    AlertType.ERROR,
                    title = "Error al salvar la cita",
                    header = "Se ha producido un error al salvar la cita",
                    mensaje = "Se ha producido un error al salvar en el sistema: \n${error.message}"
                )
            }
    }


    private fun showAlertOperacion(
        alerta: AlertType = AlertType.CONFIRMATION,
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

class CrearCitaFormulario(
    val fecha: String,
    val hora: String,
    val trabajador: String,
    val propietarioDni: String,
    val propietarioNombre: String,
    val propietarioApellidos: String,
    val propietarioCorreo: String,
    val propietarioTelefono: String,
    val vehiculoMatricula: String,
    val vehiculoMarca: String,
    val vehiculoModelo: String,
    val vehiculoMatriculacion: String,
    val vehiculoRevision: String,
    val vehiculoMotor: String,
    val vehiculoTipo: String
)
