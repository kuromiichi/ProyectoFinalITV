package dev.team.proyectofinalitv.controllers

import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dev.team.proyectofinalitv.models.Vehiculo
import dev.team.proyectofinalitv.validators.validate
import dev.team.proyectofinalitv.viewmodels.CitaViewModel
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.Stage
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDate
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

    @FXML
    private lateinit var textInformeId: Label

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

        comboCitaHora.items =
            FXCollections.observableArrayList(citaViewModel.crearModificarState.value.horasDisponibles)
        comboCitaHora.selectionModel.selectFirst()

        comboTrabajador.items =
            FXCollections.observableArrayList(citaViewModel.crearModificarState.value.trabajadoresDisponibles)
        comboTrabajador.selectionModel.selectFirst()

        comboMotor.items =
            FXCollections.observableArrayList(Vehiculo.TipoMotor.values().map { it.toString().lowercase() })
        comboMotor.value = null

        comboVehiculo.items =
            FXCollections.observableArrayList(Vehiculo.TipoVehiculo.values().dropLast(1)
                .map { it.toString().lowercase() })
        comboVehiculo.value = null

        citaViewModel.crearModificarState.addListener { _, oldState, newState ->
            updateComboHoras(oldState, newState)
            updateComboTrabajadores(oldState, newState)
        }

        // Valores de la cita inicial
        val citaSelecionnada = citaViewModel.state.value.citaSeleccionada
        textCitaId.text = citaSelecionnada.idCita
        textPropietarioDni.text = citaSelecionnada.dniPropietario
        textPropietarioNombre.text = citaSelecionnada.nombrePropietario + " " + citaSelecionnada.apellidosPropietario
        textPropietarioTelefono.text = citaSelecionnada.telefonoPropietario
        textPropietarioCorreo.text = citaSelecionnada.correoPropietario
        textVehiculoMatricula.text = citaSelecionnada.matriculaVehiculo
        textVehiculoMarca.text = citaSelecionnada.marcaVehiculo
        textVehiculoModelo.text = citaSelecionnada.modeloVehiculo
        textInformeFrenado.text = citaSelecionnada.frenadoInforme.toString()
        textInformeContaminacion.text = citaSelecionnada.contaminacionInforme.toString()
        datePickerMatriculacion.value = citaSelecionnada.fechaMatriculacionVehiculo
        datePickerRevision.value = citaSelecionnada.fechaRevisionVehiculo
        comboCitaEstado.value = citaSelecionnada.estadoCita
        datePickerCitaFecha.value = citaSelecionnada.fechaCita
        comboCitaHora.value = citaSelecionnada.horaCita.toString()
        comboTrabajador.value = citaSelecionnada.nombreTrabajador + "(" + citaSelecionnada.usuarioTrabajador + ")"
        comboMotor.value = citaSelecionnada.tipoMotorVehiculo
        comboVehiculo.value = citaSelecionnada.tipoVehiculo
        // Informe
        comboCitaEstado.value = if (citaSelecionnada.isAptoInforme) "Apto" else "No apto"
        comboLuces.value = if (citaSelecionnada.lucesInforme) "Apto" else "No apto"
        comboInterior.value = if (citaSelecionnada.interiorInforme) "Apto" else "No apto"
        comboResultadoFinal.value = if (citaSelecionnada.isAptoInforme) "Apto" else "No apto"
        textInformeId.text = citaSelecionnada.idInforme
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

        val partes = textPropietarioNombre.text.split(" ")

        if (textPropietarioNombre.text.isNullOrEmpty()) {
            logger.debug { "Error al actualizar la cita" }

            showAlertOperacion(
                Alert.AlertType.ERROR,
                title = "Error al actualizar la cita",
                header = "Se ha producido un error al actualizar la cita",
                mensaje = "Se ha producido un error al actualizar en el sistema"
            )
            return
        }

        val nombre = partes[0]
        val apellido = partes[1]

        val cita = CitaViewModel.CrearModificarCitaFormulario(
            citaViewModel.state.value.citaSeleccionada.idCita,
            datePickerCitaFecha.value?.toString() ?: "",
            comboCitaHora.value ?: "",
            comboTrabajador.value ?: "",
            textPropietarioDni.text,
            nombre,
            apellido,
            textPropietarioCorreo.text,
            textPropietarioTelefono.text,
            textVehiculoMatricula.text,
            textVehiculoMarca.text,
            textVehiculoModelo.text,
            datePickerMatriculacion.value?.toString() ?: "",
            datePickerRevision.value?.toString() ?: "",
            comboMotor.value ?: "",
            comboVehiculo.value ?: "",
            citaViewModel.state.value.citaSeleccionada.idInforme,
            textInformeFrenado.text,
            textInformeContaminacion.text,
            comboInterior.value ?: "",
            comboLuces.value ?: "",
            comboResultadoFinal.value ?: ""
        )

        cita.validate().andThen { citaViewModel.modificarCita(it) }
            .onSuccess { nuevaCita ->
                logger.debug { "Cita actualizada correctamente" }

                val citas = citaViewModel.state.value.citas.toMutableList()
                citas.add(nuevaCita)
                citaViewModel.state.value = citaViewModel.state.value.copy(citas = citas)

                showAlertOperacion(
                    Alert.AlertType.INFORMATION,
                    title = "Cita actualizar",
                    header = "Cita actualizada y almacenada correctamente",
                    mensaje = "Se ha actualizar en el sistema"
                )

                val stage = buttonGuardarCita.scene.window as Stage
                stage.close()
            }.onFailure { error ->
                logger.debug { "Error al actualizar la cita ${error.message}" }

                showAlertOperacion(
                    Alert.AlertType.ERROR,
                    title = "Error al actualizar la cita",
                    header = "Se ha producido un error al actualizar la cita",
                    mensaje = "Se ha producido un error al actualizar en el sistema: \n${error.message}"
                )
            }
    }

    /**
     * Al seleccionar una fecha actualizamos al State con las horas disponibles, para mostrar en el comboBox la
     * reactividad gracias al State
     */
    private fun onFechaSeleccionada() {
        logger.debug { "Fecha seleccionada" }

        citaViewModel.crearModificarState.value = citaViewModel.crearModificarState.value.copy(
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
            citaViewModel.crearModificarState.value = citaViewModel.crearModificarState.value.copy(
                trabajadoresDisponibles = citaViewModel.getTrabajadoresDisponibles(
                    datePickerCitaFecha.value,
                    LocalTime.parse(comboCitaHora.value)
                )
            )
        }
        comboTrabajador.value = null
    }

    private fun updateComboHoras(oldState: CitaViewModel.CrearModificarCitaState, newState: CitaViewModel.CrearModificarCitaState) {
        logger.debug { "Actualizando comboBox horas" }

        if (newState.horasDisponibles != oldState.horasDisponibles) {
            comboCitaHora.items = FXCollections.observableArrayList(newState.horasDisponibles)
        }
    }

    private fun updateComboTrabajadores(
        oldState: CitaViewModel.CrearModificarCitaState, newState: CitaViewModel.CrearModificarCitaState,
    ) {
        logger.debug { "Actualizando comboBox trabajadores" }

        if (oldState.trabajadoresDisponibles != newState.trabajadoresDisponibles) {
            comboTrabajador.items =
                FXCollections.observableArrayList(citaViewModel.crearModificarState.value.trabajadoresDisponibles)
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
        datePickerMatriculacion.value = LocalDate.now()
        datePickerRevision.value = LocalDate.now()
    }

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
