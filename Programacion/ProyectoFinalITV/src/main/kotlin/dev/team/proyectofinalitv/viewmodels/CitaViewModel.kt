package dev.team.proyectofinalitv.viewmodels

import dev.team.proyectofinalitv.models.*
import dev.team.proyectofinalitv.repositories.*
import dev.team.proyectofinalitv.repositories.base.CRURepository
import dev.team.proyectofinalitv.services.storage.CitaStorage
import javafx.beans.property.SimpleObjectProperty
import java.time.LocalDate
import java.time.LocalDateTime

class CitaViewModel(
    private val propietarioRepository: CRURepository<Propietario, String>,
    private val vehiculoRepository: CRURepository<Vehiculo, String>,
    private val informeRepository: CRURepository<Informe, Long>,
    private val trabajadorRepository: CRURepository<Trabajador, String>,
    private val citaRepository: CitaRepository,
    private val storage: CitaStorage
) {
    // Actualizar cita


    // Gestión del estado de la vista
    val state = SimpleObjectProperty<CitaState>()

    fun updateState(cita: Cita) {
        val informe = informeRepository.findById(cita.idInforme)
        val vehiculo = vehiculoRepository.findById(cita.matriculaVehiculo)
        val propietario = vehiculo?.let { propietarioRepository.findById(it.dniPropietario) }
        val trabajador = trabajadorRepository.findById(cita.usuarioTrabajador)
        if (informe != null && vehiculo != null && propietario != null && trabajador != null) {
            state.value = state.value.copy(
                citaSeleccionada = CitaFormulario(
                    idCita = cita.id.toString(),
                    estadoCita = cita.estado,
                    fechaCita = cita.fechaHora,
                    nombreTrabajador = trabajador.nombre,
                    correoTrabajador = trabajador.correo,
                    telefonoTrabajador = trabajador.telefono,
                    especialidadTrabajador = trabajador.especialidad.name.lowercase(),
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
            )
        }
    }

    enum class TipoOperacion { CREAR, ACTUALIZAR }

    fun setTipoOperacion(tipo: TipoOperacion) {
        state.value = state.value.copy(tipoOperacion = tipo)
    }

    data class CitaState(
        val citas: List<Cita> = listOf(),
        val citaSeleccionada: CitaFormulario = CitaFormulario(),
        val tipoOperacion: TipoOperacion = TipoOperacion.CREAR
    )

    data class CitaFormulario(
        val idCita: String = "",
        val estadoCita: String = "",
        val fechaCita: LocalDateTime = LocalDateTime.now(),
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
