package dev.team.proyectofinalitv.viewmodels

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dev.team.proyectofinalitv.controllers.CrearCitaFormulario
import dev.team.proyectofinalitv.dto.*
import dev.team.proyectofinalitv.errors.CitaError
import dev.team.proyectofinalitv.errors.CrearCitaError
import dev.team.proyectofinalitv.models.*
import dev.team.proyectofinalitv.repositories.*
import dev.team.proyectofinalitv.repositories.base.CRURepository
import dev.team.proyectofinalitv.services.storage.CitaStorage
import javafx.beans.property.SimpleObjectProperty
import mu.KotlinLogging
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class CitaViewModel(
    private val propietarioRepository: CRURepository<Propietario, String>,
    private val vehiculoRepository: CRURepository<Vehiculo, String>,
    private val informeRepository: CRURepository<Informe, Long>,
    private val trabajadorRepository: CRURepository<Trabajador, String>,
    private val citaRepository: CitaRepository,
    private val storage: CitaStorage
) {
    private val logger = KotlinLogging.logger {}

    // Gestión del estado de la vista principal
    val state = SimpleObjectProperty(CitaState())

    // Gestión del estado de la vista de creación
    val crearState = SimpleObjectProperty(CrearCitaState())

    // Gestión del estado de la vista de edición
    val modificarState = SimpleObjectProperty(ModificarCitaState())

    init {
        initialUpdateState()
    }

    /**
     * Estado inicial de nuestra aplicación
     */
    private fun initialUpdateState() {
        logger.debug { "Iniciando estado de la Aplicación" }

        state.value = state.value.copy(
            // Recogemos de la base de datos todas las citas (tendremos que utilizar siempre lo que tenemos + lo nuevo)
            citas = citaRepository.findAll().sortedBy { it.id },
            // Después lo actualizaremos en updateCitaSeleccionada()
            citaSeleccionada = CitaFormulario()
        )
    }

    /**
     *  Filtrado de la tabla de citas en el controlador en función de los siguientes parámetros:
     *  @param tipo
     *  @param matricula
     *  @param fecha
     */
    fun getListCitaFiltered(tipo: Vehiculo.TipoVehiculo?, matricula: String?, fecha: LocalDate?): List<Cita?> {
        val vehiculos = vehiculoRepository.findAll()

        return state.value.citas.filter { cita ->
            // Filtramos por el tipo del vehículo
            val vehiculo = vehiculos.find { it.matricula == cita.matriculaVehiculo }
            val tipoValido = tipo == null || tipo == Vehiculo.TipoVehiculo.ALL || vehiculo?.tipoVehiculo == tipo

            // Filtramos por la matrícula del vehículo
            val matriculaValida = matricula.isNullOrBlank() || cita.matriculaVehiculo.equals(matricula, ignoreCase = true)

            // Filtramos por la fecha de la cita
            val fechaValida = fecha == null || cita.fechaHora.toLocalDate() == fecha || fecha.toString().isBlank()

            tipoValido && matriculaValida && fechaValida
        }
    }

    // TODO
    fun modificarCita(citaFormulario: CrearCitaFormulario): Result<Cita, CrearCitaError> {
        return Err(CrearCitaError.CitaYaExiste("Cambiar al de modificar"))
    }

    /**
     *  Guardar en orden respecto a Integridad Referencial, previamente deben estar validados todos los campos
     *  @param propietario datos del propietario
     *  @param vehiculo datos del vehículo
     *  @param cita datos de la cita
     *  @return la cita si se ha guardado correctamente, o un error si la cita ya existe en el sistema
     */
    fun saveCita(citaFormulario: CrearCitaFormulario): Result<Cita, CrearCitaError> {
        logger.debug { "Guardando nueva cita" }
        val cita = Cita(
            estado = "Pendiente",
            fechaHora = LocalDateTime.of(
                LocalDate.parse(citaFormulario.fecha),
                LocalTime.parse(citaFormulario.hora)
            ),
            idInforme = informeRepository.findAll().maxOf { it.id } + 1,
            usuarioTrabajador = citaFormulario.trabajador.substringAfter("(").substringBefore(")"),
            matriculaVehiculo = citaFormulario.vehiculoMatricula
        )
        val propietario = Propietario(
            dni = citaFormulario.propietarioDni,
            nombre = citaFormulario.propietarioNombre,
            apellidos = citaFormulario.propietarioApellidos,
            correo = citaFormulario.propietarioCorreo,
            telefono = citaFormulario.propietarioTelefono
        )
        val vehiculo = Vehiculo(
            matricula = citaFormulario.vehiculoMatricula,
            marca = citaFormulario.vehiculoMarca,
            modelo = citaFormulario.vehiculoModelo,
            fechaMatriculacion = LocalDate.parse(citaFormulario.vehiculoMatriculacion),
            fechaRevision = LocalDate.parse(citaFormulario.vehiculoRevision),
            tipoMotor = Vehiculo.TipoMotor.valueOf(citaFormulario.vehiculoMotor.uppercase()),
            tipoVehiculo = Vehiculo.TipoVehiculo.valueOf(citaFormulario.vehiculoTipo.uppercase()),
            dniPropietario = citaFormulario.propietarioDni
        )

        if (checkExistingCita(cita))
            return Err(CrearCitaError.CitaYaExiste("Ya existe una cita para ese vehículo en la fecha y hora seleccionadas"))
        when (propietarioRepository.findById(propietario.dni)) {
            null -> propietarioRepository.save(propietario)
            else -> propietarioRepository.update(propietario)
        }
        when (vehiculoRepository.findById(vehiculo.matricula)) {
            null -> vehiculoRepository.save(vehiculo)
            else -> vehiculoRepository.update(vehiculo)
        }
        informeRepository.save(Informe(1, 0.0, 0.0, false, false, false))
        val nuevaCita = citaRepository.save(cita)
        return Ok(nuevaCita)
    }

    /**
     * Comprueba si existe una cita con la misma fecha y hora y matrícula de vehículo
     * @param cita datos de la cita
     * @return true si existe, false si no existe
     */
    private fun checkExistingCita(cita: Cita): Boolean {
        return citaRepository.findAll().find {
            it.fechaHora == cita.fechaHora && it.matriculaVehiculo == cita.matriculaVehiculo
        } != null
    }

    /**
     * Actualizamos el State con todos los datos del formulario, en función de la Cita seleccionada
     * @param cita donde recogeremos las referencias para navegar por la base de datos y disponer de
     * los datos necesarios
     */
    fun updateCitaSeleccionada(cita: Cita) {
        logger.debug { "Actualizando cita seleccionada: $cita" }

        val trabajador = trabajadorRepository.findById(cita.usuarioTrabajador)
        val vehiculo = vehiculoRepository.findById(cita.matriculaVehiculo)
        val informe = informeRepository.findById(cita.idInforme)
        val propietario = vehiculo?.let { propietarioRepository.findById(it.dniPropietario) }

        var citaSeleccionada = CitaFormulario()

        if (trabajador != null && vehiculo != null && informe != null && propietario != null) {
            citaSeleccionada = CitaFormulario(
                idCita = cita.id.toString(),
                estadoCita = cita.estado,
                fechaCita = cita.fechaHora.toLocalDate(),
                horaCita = cita.fechaHora.toLocalTime(),
                nombreTrabajador = trabajador.nombre,
                correoTrabajador = trabajador.correo,
                telefonoTrabajador = trabajador.telefono,
                especialidadTrabajador = trabajador.especialidad.name,
                dniPropietario = propietario.dni,
                nombrePropietario = propietario.nombre,
                apellidosPropietario = propietario.apellidos,
                correoPropietario = propietario.correo,
                telefonoPropietario = propietario.telefono,
                matriculaVehiculo = vehiculo.matricula,
                marcaVehiculo = vehiculo.marca,
                modeloVehiculo = vehiculo.modelo,
                fechaMatriculacionVehiculo = vehiculo.fechaMatriculacion,
                fechaRevisionVehiculo = vehiculo.fechaRevision,
                tipoMotorVehiculo = vehiculo.tipoMotor.name.lowercase(),
                tipoVehiculo = vehiculo.tipoVehiculo.name.lowercase(),
                frenadoInforme = informe.frenado,
                contaminacionInforme = informe.contaminacion,
                interiorInforme = informe.interior,
                lucesInforme = informe.luces,
                isAptoInforme = informe.isApto
            )
        }
        state.value = state.value.copy(citaSeleccionada = citaSeleccionada)
    }

    /**
     * Creación de todas las citas que tengamos en la base de datos a CitaDto
     * @return todas las citas transformadas
     */
    fun mapperListCitaDtoToExport(): List<CitaDtoToExport> {
        return citaRepository.findAll().map { mapperCitaToCitaDtoToExport(it) }
    }

    /**
     * Transformación de una Cita a una CitaDto para exportar
     */
    fun mapperCitaToCitaDtoToExport(citaDb: Cita?): CitaDtoToExport {
        val trabajadorDeCitaDb = trabajadorRepository.findById(citaDb!!.usuarioTrabajador)
        val informeDeCitaDb = informeRepository.findById(citaDb.idInforme)
        val vehiculoDeCitaDb = vehiculoRepository.findById(citaDb.matriculaVehiculo)
        val propietarioDeCitaDb = vehiculoDeCitaDb?.let { propietarioRepository.findById(it.dniPropietario) }

        val citaDto = CitaDto(
            citaId = citaDb.id,
            estado = citaDb.estado,
            fechaHora = citaDb.fechaHora.toString()
        )
        val trabajadorDto = TrabajadorDto(
            nombreTrabajador = trabajadorDeCitaDb!!.nombre,
            correoTrabajador = trabajadorDeCitaDb.correo,
            telefonoTrabajador = trabajadorDeCitaDb.telefono,
            especialidadTrabajador = trabajadorDeCitaDb.especialidad.toString(),
            idEstacion = trabajadorDeCitaDb.idEstacion
        )
        val informeDto = InformeDto(
            informeId = informeDeCitaDb!!.id,
            informeFrenado = informeDeCitaDb.frenado,
            informeContaminacion = informeDeCitaDb.contaminacion,
            informeInterior = informeDeCitaDb.interior,
            informeLuces = informeDeCitaDb.luces,
            informeIsApto = informeDeCitaDb.isApto
        )
        val propietarioDto = PropietarioDto(
            propietarioDni = propietarioDeCitaDb!!.dni,
            propietarioNombre = propietarioDeCitaDb.nombre,
            propietarioApellidos = propietarioDeCitaDb.apellidos,
            propietarioCorreo = propietarioDeCitaDb.correo,
            propietarioTelefono = propietarioDeCitaDb.telefono
        )
        val vehiculoDto = VehiculoDto(
            vehiculoMatricula = vehiculoDeCitaDb.matricula,
            vehiculoMarca = vehiculoDeCitaDb.marca,
            vehiculoModelo = vehiculoDeCitaDb.modelo,
            vehiculoFechaMatriculacion = vehiculoDeCitaDb.fechaMatriculacion.toString(),
            vehiculoFechaRevision = vehiculoDeCitaDb.fechaRevision.toString(),
            vehiculoTipoMotor = vehiculoDeCitaDb.tipoMotor.toString(),
            vehiculoTipoVehiculo = vehiculoDeCitaDb.tipoVehiculo.toString()
        )

        return CitaDtoToExport(citaDto, trabajadorDto, informeDto, propietarioDto, vehiculoDto)
    }

    /**
     * Creamos una CitaDto de la cita seleccionada en el State personalizada para exportar con los datos deseados
     */
    fun mapperCitaDtoToExportDelEstado(): CitaDtoToExport {

        // Busco la cita real, mediante el ID de CitaFormulario()
        val citaDb = citaRepository.findById(state.value.citaSeleccionada.idCita.toLong())
        return mapperCitaToCitaDtoToExport(citaDb)
    }

    /**
     * Exportación a JSON
     * @param file la ruta del archivo que se elige mediante el FileChooser()
     * @param citaDtoToExport datos que exportaremos
     */
    fun exportToJson(file: File, citaDtoToExport: CitaDtoToExport): Result<Unit, CitaError> {
        logger.debug { "Exportando en JSON" }
        return storage.exportToJson(file, citaDtoToExport)
    }

    /**
     * Exportación a JSON todas las citas
     * @param file la ruta del archivo que se elige mediante el FileChooser()
     * @param citaDtoToExport datos que exportaremos
     */
    fun exportAllToJson(file: File, listCitas: List<CitaDtoToExport>): Result<Unit, CitaError> {
        logger.debug { "Exportando todas las citas a JSON" }
        return storage.exportAllToJson(file, listCitas)
    }

    /**
     * Exportación a HTML/Markdown
     * @param file la ruta del archivo que se elige mediante el FileChooser()
     * @param citaDtoToExport datos que exportaremos
     */
    fun exportToMarkDown(file: File, citaDtoToExport: CitaDtoToExport): Result<Unit, CitaError> {
        logger.debug { "Exportando en MarkDown" }
        return storage.exportToMarkdown(file, citaDtoToExport)
    }

    /**
     * Comprobación de las horas disponibles para una fecha determinada
     * @param fecha la fecha que se quiere comprobar
     * @return las horas disponibles para esa fecha en forma de lista de String
     */
    fun getHorasDisponibles(fecha: LocalDate): List<String> {
        val horasPosibles = mutableListOf(
            LocalTime.parse("09:00"),
            LocalTime.parse("09:30"),
            LocalTime.parse("10:00"),
            LocalTime.parse("10:30"),
            LocalTime.parse("11:00"),
            LocalTime.parse("11:30"),
            LocalTime.parse("12:00"),
            LocalTime.parse("12:30"),
            LocalTime.parse("13:00"),
            LocalTime.parse("13:30"),
            LocalTime.parse("14:00"),
            LocalTime.parse("14:30"),
            LocalTime.parse("15:00"),
            LocalTime.parse("15:30"),
            LocalTime.parse("16:00"),
            LocalTime.parse("16:30"),
            LocalTime.parse("17:00"),
            LocalTime.parse("17:30"),
            LocalTime.parse("18:00"),
            LocalTime.parse("18:30"),
            LocalTime.parse("19:00"),
            LocalTime.parse("19:30")
        )
        val citasEnFecha = citaRepository.findAll().filter { it.fechaHora.toLocalDate() == fecha }
        val horasDisponibles = mutableListOf<String>()
        horasPosibles.asSequence().forEach { hora ->
            if (citasEnFecha.count { it.fechaHora.toLocalTime() == hora } < 8) {
                horasDisponibles.add(hora.toString())
            }
        }

        return horasPosibles.map { it.toString() }
    }

    /**
     * Comprobación de los trabajadores disponibles para una fecha y hora determinada
     * @param fecha la fecha que se quiere comprobar
     * @param hora la hora que se quiere comprobar
     * @return los trabajadores disponibles para esa fecha y hora en forma de lista de String
     */
    fun getTrabajadoresDisponibles(fecha: LocalDate, hora: LocalTime): List<String> {
        val trabajadoresPosibles = trabajadorRepository.findAll().toMutableList()
        val citasEnFecha = citaRepository.findAll().filter { it.fechaHora == LocalDateTime.of(fecha, hora) }
        val trabajadoresDisponibles = mutableListOf<String>()
        trabajadoresPosibles.asSequence().forEach { trabajador ->
            if (citasEnFecha.count { it.usuarioTrabajador == trabajador.usuario } < 4) {
                trabajadoresDisponibles.add("${trabajador.nombre} (${trabajador.usuario})")
            }
        }

        return trabajadoresDisponibles
    }

    /**
     * Devuelve toda la lista de los trabajadores
     */
    fun findAllTrabajadores() = trabajadorRepository.findAll()

    data class CitaState(
        val citas: List<Cita> = listOf(),
        val citaSeleccionada: CitaFormulario = CitaFormulario(),
        // El tipo de vehículo en el filtro nunca cambiará
        val tipoVehiculoFilter: List<String> = Vehiculo.TipoVehiculo.values().map { it.toString() },
        // Para el comboBox
    )

    // Estamos duplicando la clase en realidad no? Con CrearCitaState
    data class ModificarCitaState(
        val horasDisponibles: List<String> = listOf(),
        val trabajadoresDisponibles: List<String> = listOf(),
        val fechaSeleccionada: String = "",
        val horaSeleccionada: String = "",
        val trabajadorSeleccionado: String = ""
    )

    data class CrearCitaState(
        val horasDisponibles: List<String> = listOf(),
        val trabajadoresDisponibles: List<String> = listOf(),
        val fechaSeleccionada: String = "",
        val horaSeleccionada: String = "",
        val trabajadorSeleccionado: String = ""
    )

    data class CitaFormulario(
        val idCita: String = "",
        val estadoCita: String = "",
        val fechaCita: LocalDate = LocalDate.now(),
        val horaCita: LocalTime = LocalTime.now(),
        val nombreTrabajador: String = "",
        val correoTrabajador: String = "",
        val telefonoTrabajador: String = "",
        val especialidadTrabajador: String = "",
        val dniPropietario: String = "",
        val nombrePropietario: String = "",
        val apellidosPropietario: String = "",
        val correoPropietario: String = "",
        val telefonoPropietario: String = "",
        val matriculaVehiculo: String = "",
        val marcaVehiculo: String = "",
        val modeloVehiculo: String = "",
        val fechaMatriculacionVehiculo: LocalDate = LocalDate.now(),
        val fechaRevisionVehiculo: LocalDate = LocalDate.now(),
        val tipoMotorVehiculo: String = "",
        val tipoVehiculo: String = "",
        val frenadoInforme: Double = 0.0,
        val contaminacionInforme: Double = 0.0,
        val interiorInforme: Boolean = false,
        val lucesInforme: Boolean = false,
        val isAptoInforme: Boolean = false
    )
}
