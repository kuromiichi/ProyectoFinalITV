package dev.team.proyectofinalitv.controllers

import dev.team.proyectofinalitv.models.Vehiculo
import dev.team.proyectofinalitv.viewmodels.CitaViewModel
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalTime

class UpdateCitaViewController: KoinComponent {

    private val logger = KotlinLogging.logger {}

    private val citaViewModel: CitaViewModel by inject()

    // TextField

    @FXML
    private lateinit var textCitaId: TextField

    @FXML
    private lateinit var textPropietarioDni: TextField

    @FXML
    private lateinit var textPropietarioNombre: TextField

    @FXML
    private lateinit var textPropietarioTelefono: TextField

    @FXML
    private lateinit var textPropietarioCorreo: TextField

    @FXML
    private lateinit var textVehiculoMatricula: TextField

    @FXML
    private lateinit var textVehiculoMarca: TextField

    @FXML
    private lateinit var textVehiculoModelo: TextField

    @FXML
    private lateinit var textInformeFrenado: TextField

    @FXML
    private lateinit var textInformeContaminacion: TextField

    // DatePicker

    @FXML
    private lateinit var datePickerCitaFecha: DatePicker

    @FXML
    private lateinit var datePickerMatriculacion: DatePicker

    @FXML
    private lateinit var datePickerRevision: DatePicker

    // ComboBox

    @FXML
    private lateinit var comboCitaEstado: ComboBox<String>

    @FXML
    private lateinit var comboInterior: ComboBox<String>

    @FXML
    private lateinit var comboLuces: ComboBox<String>

    @FXML
    private lateinit var comboCitaHora: ComboBox<String>

    @FXML
    private lateinit var comboMotor: ComboBox<String>

    @FXML
    private lateinit var comboVehiculo: ComboBox<String>

    @FXML
    private lateinit var comboResultadoFinal: ComboBox<String>

    @FXML
    private lateinit var comboTrabajador: ComboBox<String>

    // Buttons
    @FXML
    private lateinit var buttonGuardarCita: Button

    @FXML
    private lateinit var buttonLimpiar: Button

    @FXML
    private fun initialize() {
        logger.info("Inicializando la vista de actualizar cita")

        initBindings()
        initEvents()
    }

    fun initBindings() {
        logger.debug { "Inicializando bindings" }

        // ComboBoxes
        val valoresInforme = listOf("Apto", "No apto")
        val valoresCitaEstado = valoresInforme + "Pendiente"
        comboCitaEstado.items = FXCollections.observableArrayList(valoresCitaEstado)
        comboInterior.items = FXCollections.observableArrayList(valoresInforme)
        comboLuces.items = FXCollections.observableArrayList(valoresInforme)
        comboResultadoFinal.items = FXCollections.observableArrayList(valoresInforme)

        comboCitaHora.items = FXCollections.observableArrayList(citaViewModel.crearState.value.horasDisponibles)
        comboCitaHora.selectionModel.selectFirst()

        comboTrabajador.items = FXCollections.observableArrayList(citaViewModel.crearState.value.trabajadoresDisponibles)
        comboTrabajador.selectionModel.selectFirst()

        comboVehiculo.items =
            FXCollections.observableArrayList(Vehiculo.TipoMotor.values().map { it.toString().lowercase() })
        comboVehiculo.value = null

        comboMotor.items =
            FXCollections.observableArrayList(Vehiculo.TipoVehiculo.values().dropLast(1)
                .map { it.toString().lowercase() })
        comboMotor.value = null

        citaViewModel.modificarState.addListener { _, oldState, newState ->
            updateComboHoras(oldState, newState)
            updateComboTrabajadores(oldState, newState)
        }

        // Valores de la cita inicial
        val citaSelecionnada = citaViewModel.state.value.citaSeleccionada
        textCitaId.text = citaSelecionnada.idCita
        textPropietarioDni.text = citaSelecionnada.dniPropietario
        textPropietarioNombre.text = citaSelecionnada.nombrePropietario
        textPropietarioTelefono.text = citaSelecionnada.telefonoPropietario
        textPropietarioCorreo.text = citaSelecionnada.correoPropietario
        textVehiculoMatricula.text = citaSelecionnada.matriculaVehiculo
        textVehiculoMarca.text = citaSelecionnada.marcaVehiculo
        textVehiculoModelo.text = citaSelecionnada.modeloVehiculo
        textInformeFrenado.text = citaSelecionnada.frenadoInforme.toString()
        textInformeContaminacion.text = citaSelecionnada.contaminacionInforme.toString()
        datePickerMatriculacion.value = citaSelecionnada.fechaMatriculacionVehiculo
        datePickerRevision.value = citaSelecionnada.fechaRevisionVehiculo
    }

    private fun initEvents() {
        logger.debug { "Inicializando eventos" }

        buttonGuardarCita.setOnAction {
            actualizarCita()
        }

        buttonLimpiar.setOnAction {
            limpiarFormulario()
        }

        datePickerCitaFecha.setOnAction {
            onFechaSeleccionada()
        }

        comboCitaHora.setOnAction {
            onHoraSeleccionada()
        }
    }

    /**
     * Actualizar una cita
     */
    private fun actualizarCita(){
        logger.debug { "Actualizar cita" }

    }

    /**
     * Al seleccionar una fecha actualizamos al State con las horas disponibles, para mostrar en el comboBox la
     * reactividad gracias al State
     */
    private fun onFechaSeleccionada() {
        logger.debug { "Fecha seleccionada" }

        citaViewModel.crearState.value = citaViewModel.crearState.value.copy(
            horasDisponibles = citaViewModel.getHorasDisponibles(datePickerCitaFecha.value)
        )
        comboCitaHora.value = null
        comboTrabajador.value = null
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
                    datePickerCitaFecha.value,
                    LocalTime.parse(comboCitaHora.value)
                )
            )
        }
        comboTrabajador.value = null
    }

    private fun updateComboHoras(oldState: CitaViewModel.ModificarCitaState, newState: CitaViewModel.ModificarCitaState) {
        logger.debug { "Actualizando comboBox horas" }

        if (newState.horasDisponibles != oldState.horasDisponibles) {
            comboCitaHora.items = FXCollections.observableArrayList(newState.horasDisponibles)
        }
    }

    private fun updateComboTrabajadores(
        oldState: CitaViewModel.ModificarCitaState, newState: CitaViewModel.ModificarCitaState
    ) {
        logger.debug { "Actualizando comboBox trabajadores" }

        if (oldState.trabajadoresDisponibles != newState.trabajadoresDisponibles) {
            comboTrabajador.items =
                FXCollections.observableArrayList(citaViewModel.crearState.value.trabajadoresDisponibles)
        }
    }

    private fun limpiarFormulario(){
        textPropietarioDni.text = ""
        textPropietarioNombre.text = ""
        textPropietarioTelefono.text = ""
        textPropietarioCorreo.text = ""
        textVehiculoMatricula.text = ""
        textVehiculoMarca.text = ""
        textVehiculoModelo.text = ""
        textInformeFrenado.text = ""
        textInformeContaminacion.text = ""
        datePickerMatriculacion.value = null
        datePickerRevision.value = null
    }
}
