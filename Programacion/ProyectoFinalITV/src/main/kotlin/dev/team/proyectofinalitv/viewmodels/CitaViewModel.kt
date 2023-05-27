package dev.team.proyectofinalitv.viewmodels

import com.github.michaelbull.result.Result
import dev.team.proyectofinalitv.dto.*
import dev.team.proyectofinalitv.errors.CitaError
import dev.team.proyectofinalitv.models.*
import dev.team.proyectofinalitv.repositories.*
import dev.team.proyectofinalitv.repositories.base.CRURepository
import dev.team.proyectofinalitv.services.storage.CitaStorage
import javafx.beans.property.SimpleObjectProperty
import mu.KotlinLogging
import java.io.File
import java.time.LocalDate
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

    // Gestión del estado de la vista
    val state = SimpleObjectProperty(CitaState())

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
    fun citaFilteredList2(tipo: Vehiculo.TipoVehiculo?, matricula: String?, fecha: LocalDate?): List<Cita?> {
        logger.debug { "Filtrando lista en función de: $tipo, con la matrícula $matricula y fecha $fecha" }

        val vehiculos = vehiculoRepository.findAll()

        return state.value.citas.filter { cita ->
            // Filtramos por el tipo del vehículo
            when (tipo) {
                Vehiculo.TipoVehiculo.COCHE -> {
                    val coches = vehiculos.filter { it.tipoVehiculo == tipo }
                    val matriculasCoche = coches.map { it.matricula }
                    matriculasCoche.contains(cita.matriculaVehiculo)
                }
                Vehiculo.TipoVehiculo.CAMION -> {
                    val camiones = vehiculos.filter { it.tipoVehiculo == tipo }
                    val matriculasCamion = camiones.map { it.matricula }
                    matriculasCamion.contains(cita.matriculaVehiculo)
                }
                Vehiculo.TipoVehiculo.FURGONETA -> {
                    val furgonetas = vehiculos.filter { it.tipoVehiculo == tipo }
                    val matriculasFurgoneta = furgonetas.map { it.matricula }
                    matriculasFurgoneta.contains(cita.matriculaVehiculo)
                }
                Vehiculo.TipoVehiculo.MOTOCICLETA -> {
                    val motocicletas: List<Vehiculo> = vehiculos.filter { it.tipoVehiculo == tipo }
                    val matriculasMotocicleta = motocicletas.map { it.matricula }
                    matriculasMotocicleta.contains(cita.matriculaVehiculo)
                }
                // Cualquier otro valor del filtro nos dará todos los items
                else -> true
            }
        }.filter { cita ->
            // En caso de darnos una matrícula filtramos por esa matrícula
            if (matricula != null) {
                cita.matriculaVehiculo.contains(matricula)
            } else {
                // Si es nula nos devuelve todas las citas
                true
            }
        }.filter { cita ->
            // En caso de darnos una fecha filtramos por esa fecha
            if (fecha != null) {
                cita.fechaHora.toLocalDate() == fecha
            } else {
                // Si es nula nos devuelve todas las citas
                true
            }
        }
    }

    fun citaFilteredList(tipo: Vehiculo.TipoVehiculo?, matricula: String?, fecha: LocalDate?): List<Cita?> {
        val vehiculos = vehiculoRepository.findAll()

        return state.value.citas.filter { cita ->
            // Filtramos por el tipo del vehículo
            val vehiculo = vehiculos.find { it.matricula == cita.matriculaVehiculo }
            val tipoValido = tipo == null || tipo == Vehiculo.TipoVehiculo.ALL || vehiculo?.tipoVehiculo == tipo

            // Filtramos por la matrícula del vehículo
            val matriculaValida = matricula.isNullOrBlank() || cita.matriculaVehiculo.contains(matricula)

            // Filtramos por la fecha de la cita
            val fechaValida = fecha == null || cita.fechaHora.toLocalDate() == fecha || fecha.toString().isBlank()

            tipoValido && matriculaValida && fechaValida
        }
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
    fun mapperListCitaDtoToExport(): List<CitaDtoToExport>{
        return citaRepository.findAll().map { mapperCitaToCitaDtoToExport(it) }
    }

    /**
     * Transformación de una Cita a una CitaDto para exportar
     */
    private fun mapperCitaToCitaDtoToExport(citaDb: Cita?): CitaDtoToExport {
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

        return CitaDtoToExport(citaDto,trabajadorDto,informeDto,propietarioDto,vehiculoDto)
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

    enum class TipoOperacion { CREAR, ACTUALIZAR }

    /**
     * En función del tipo de operación filtraremos si se CREA o se ACTUALIZA en el controlador
     * @param tipo ENUM del tipo de operación que realizaremos
     */
    fun setTipoOperacion(tipo: TipoOperacion) {
        logger.debug { "Modificando tipo de operación a: ${tipo.name}" }
        state.value = state.value.copy(tipoOperacion = tipo)
    }

    data class CitaState(
        val citas: List<Cita> = listOf(),
        val citaSeleccionada: CitaFormulario = CitaFormulario(),
        val tipoOperacion: TipoOperacion = TipoOperacion.CREAR,
        // El tipo de vehículo en el filtro nunca cambiará
        val tipoVehiculoFilter: List<String> = listOf(
            Vehiculo.TipoVehiculo.ALL.toString(),
            Vehiculo.TipoVehiculo.COCHE.toString(),
            Vehiculo.TipoVehiculo.FURGONETA.toString(),
            Vehiculo.TipoVehiculo.CAMION.toString(),
            Vehiculo.TipoVehiculo.MOTOCICLETA.toString()
        )
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
