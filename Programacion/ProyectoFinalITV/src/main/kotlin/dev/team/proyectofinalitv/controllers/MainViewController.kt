package dev.team.proyectofinalitv.controllers

import dev.team.proyectofinalitv.models.Cita
import dev.team.proyectofinalitv.viewmodels.CitaViewModel
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewController : KoinComponent {

    private val logger = KotlinLogging.logger {}

    private val citaViewModel: CitaViewModel by inject()

    // Buscador

    @FXML
    private lateinit var textBuscador: TextField

    @FXML
    private lateinit var comboFiltradoTabla: ComboBox<String>

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
    private lateinit var buttonNuevo: Button

    @FXML
    private lateinit var buttonEditar: Button

    @FXML
    private lateinit var buttonLimpiar: Button


    @FXML
    private fun initialize() {
        logger.debug { "Inicializando vista principal" }

        initBindings()
        initEvents()
    }

    private fun initBindings() {
        logger.debug { "Inicializando bindings" }
        comboFiltradoTabla.items = FXCollections.observableArrayList() // TODO: rellenar con valores
        comboFiltradoTabla.selectionModel.selectFirst()

        tableCitas.items = FXCollections.observableArrayList(citaViewModel.state.value.citas)
        tableCitas.selectionModel.selectionMode = SelectionMode.SINGLE

        columnId.cellValueFactory = PropertyValueFactory("idCita")
        columnEstado.cellValueFactory = PropertyValueFactory("estadoCita")
        columnFecha.cellValueFactory = PropertyValueFactory("fechaCita")
        columnHora.cellValueFactory = PropertyValueFactory("horaCita")
        columnMatricula.cellValueFactory = PropertyValueFactory("matriculaVehiculo")


        citaViewModel.state.addListener { _, oldState, newState ->
            updateTable(oldState, newState)
            updateForm(oldState, newState)
        }
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
            textTrabajadorEspecialidad.text = newState.citaSeleccionada.especialidadTrabajador
            textPropietarioDni.text = newState.citaSeleccionada.dniPropietario
            textPropietarioNombre.text = newState.citaSeleccionada.nombrePropietario
            textPropietarioTelefono.text = newState.citaSeleccionada.telefonoPropietario
            textPropietarioCorreo.text = newState.citaSeleccionada.correoPropietario
            textVehiculoMatricula.text = newState.citaSeleccionada.matriculaVehiculo
            textVehiculoMarca.text = newState.citaSeleccionada.marcaVehiculo
            textVehiculoModelo.text = newState.citaSeleccionada.modeloVehiculo
            textVehiculoMatriculacion.text = newState.citaSeleccionada.fechaMatriculacionVehiculo.toString()
            textVehiculoRevision.text = newState.citaSeleccionada.fechaRevisionVehiculo.toString()
            textVehiculoMotor.text = newState.citaSeleccionada.tipoMotorVehiculo
            textVehiculoTipo.text = newState.citaSeleccionada.tipoVehiculo
            textInformeFrenado.text = newState.citaSeleccionada.frenadoInforme.toString()
            textInformeContaminacion.text = newState.citaSeleccionada.contaminacionInforme.toString()
            textInformeInterior.text = if (newState.citaSeleccionada.interiorInforme) apto else noApto
            textInformeLuces.text = if (newState.citaSeleccionada.lucesInforme) apto else noApto
            textInformeResultado.text = if (newState.citaSeleccionada.isAptoInforme) apto else noApto
        }
    }

    private fun initEvents() {
        tableCitas.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            onCitaSeleccionada(newValue)
        }
    }

    private fun onCitaSeleccionada(newValue: Cita) {
        logger.debug { "Seleccionando cita ${newValue.id}" }
        citaViewModel.updateCitaSeleccionada(newValue)
    }

}
