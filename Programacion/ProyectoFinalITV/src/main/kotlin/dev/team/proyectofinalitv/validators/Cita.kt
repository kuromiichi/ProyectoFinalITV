package dev.team.proyectofinalitv.validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dev.team.proyectofinalitv.errors.CitaError
import dev.team.proyectofinalitv.viewmodels.CitaViewModel
import mu.KotlinLogging
import java.time.LocalDate

private val logger = KotlinLogging.logger {}

fun CitaViewModel.CrearModificarCitaFormulario.validate(): Result<CitaViewModel.CrearModificarCitaFormulario, CitaError> {
    when (validateFechaHora(this)) {
        "fechaVacia" -> return Err(CitaError.FechaInvalida("La fecha no puede estar vacía"))
        "fechaAnteriorAActual" -> return Err(CitaError.FechaInvalida("La fecha no puede ser anterior a la actual"))
        "horaVacia" -> return Err(CitaError.HoraInvalida("La hora no puede estar vacía"))
        "ok" -> logger.debug { "Fecha y hora válidas" }
    }

    when (validateTrabajador(this)) {
        "trabajadorVacio" -> return Err(CitaError.TrabajadorInvalido("El trabajador no puede estar vacío"))
        "ok" -> logger.debug { "Trabajador válido" }
    }

    when (validatePropietario(this)) {
        "dniVacio" -> return Err(CitaError.DniInvalido("El DNI no puede estar vacío"))
        "dniInvalido" -> return Err(CitaError.DniInvalido("El DNI no es válido"))
        "nombreVacio" -> return Err(CitaError.NombreInvalido("El nombre no puede estar vacío"))
        "apellidosVacios" -> return Err(CitaError.ApellidosInvalidos("Los apellidos no pueden estar vacíos"))
        "correoVacio" -> return Err(CitaError.CorreoInvalido("El correo no puede estar vacío"))
        "correoInvalido" -> return Err(CitaError.CorreoInvalido("El correo no es válido"))
        "telefonoVacio" -> return Err(CitaError.TelefonoInvalido("El teléfono no puede estar vacío"))
        "telefonoInvalido" -> return Err(CitaError.TelefonoInvalido("El teléfono no es válido"))
        "ok" -> logger.debug { "Propietario válido" }
    }

    when (validateVehiculo(this)) {
        "matriculaVacia" -> return Err(CitaError.MatriculaInvalida("La matrícula no puede estar vacía"))
        "matriculaInvalida" -> return Err(CitaError.MatriculaInvalida("La matrícula no es válida"))
        "marcaVacia" -> return Err(CitaError.MarcaInvalida("La marca no puede estar vacía"))
        "modeloVacio" -> return Err(CitaError.ModeloInvalido("El modelo no puede estar vacío"))
        "matriculacionVacia" -> return Err(CitaError.MatriculacionInvalida("La fecha de matriculación no puede estar vacía"))
        "matriculacionPosteriorAActual" -> return Err(CitaError.MatriculacionInvalida("La fecha de matriculación no puede ser posterior a la actual"))
        "revisionVacia" -> return Err(CitaError.RevisionInvalida("La fecha de última revisión no puede estar vacía"))
        "revisionPosteriorAActual" -> return Err(CitaError.RevisionInvalida("La fecha de última revisión no puede ser posterior a la actual"))
        "revisionAnteriorAMatriculacion" -> return Err(CitaError.RevisionInvalida("La fecha de última revisión no puede ser anterior a la fecha de matriculación"))
        "motorVacio" -> return Err(CitaError.MotorInvalido("El tipo de motor no puede estar vacío"))
        "tipoVacio" -> return Err(CitaError.TipoInvalido("El tipo de vehículo no puede estar vacío"))
        "ok" -> logger.debug { "Vehículo válido" }
    }

    logger.debug { "Formulario válido" }
    return Ok(this)
}

private fun validateFechaHora(cita: CitaViewModel.CrearModificarCitaFormulario): String {
    if (cita.fecha.isEmpty()) return "fechaVacia"
    if (LocalDate.parse(cita.fecha).isBefore(LocalDate.now()))
        return "fechaAnteriorAActual"
    if (cita.hora.isEmpty()) return "horaVacia"
    return "ok"
}

private fun validateTrabajador(cita: CitaViewModel.CrearModificarCitaFormulario): String {
    if (cita.trabajador.isEmpty()) return "trabajadorVacio"
    return "ok"
}

private fun validatePropietario(cita: CitaViewModel.CrearModificarCitaFormulario): String {
    if (cita.propietarioDni.isEmpty()) return "dniVacio"
    val dniRegex = Regex("[0-9]{8}[A-Z]")
    if (!cita.propietarioDni.matches(dniRegex)) return "dniInvalido"
    if (cita.propietarioNombre.isEmpty()) return "nombreVacio"
    if (cita.propietarioApellidos.isEmpty()) return "apellidosVacios"
    if (cita.propietarioCorreo.isEmpty()) return "correoVacio"
    val correoRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")
    if (!cita.propietarioCorreo.matches(correoRegex)) return "correoInvalido"
    if (cita.propietarioTelefono.isEmpty()) return "telefonoVacio"
    val telefonoRegex = Regex("[+]?[0-9]{9,11}")
    if (!propietarioTelefono.matches(telefonoRegex))
        return Err(CitaError.TelefonoInvalido("El teléfono no es válido"))
    if (vehiculoMatricula.isEmpty())
        return Err(CitaError.MatriculaInvalida("La matrícula no puede estar vacía"))
    val matriculaRegex = Regex("[0-9]{4}-[A-Z]{3}")
    if (!vehiculoMatricula.matches(matriculaRegex))
        return Err(CitaError.MatriculaInvalida("La matrícula no es válida"))
    if (vehiculoMarca.isEmpty()) return Err(CitaError.MarcaInvalida("La marca no puede estar vacía"))
    if (vehiculoModelo.isEmpty()) return Err(CitaError.ModeloInvalido("El modelo no puede estar vacío"))
    if (vehiculoMatriculacion.isEmpty())
        return Err(CitaError.MatriculacionInvalida("La fecha de matriculación no puede estar vacía"))
    if (LocalDate.parse(vehiculoMatriculacion).isAfter(LocalDate.now()))
        return Err(
            CitaError.MatriculacionInvalida("La fecha de matriculación no puede ser posterior a la actual")
        )
    if (vehiculoRevision.isEmpty())
        return Err(CitaError.RevisionInvalida("La fecha de última revisión no puede estar vacía"))
    if (LocalDate.parse(vehiculoRevision).isAfter(LocalDate.now()))
        return Err(
            CitaError.RevisionInvalida("La fecha de última revisión no puede ser posterior a la actual")
        )
    if (LocalDate.parse(vehiculoRevision).isBefore(LocalDate.parse(vehiculoMatriculacion)))
        return Err(
            CitaError.RevisionInvalida("La fecha de última revisión no puede ser anterior a la matriculación")
        )
    if (vehiculoMotor.isEmpty())
        return Err(CitaError.MotorInvalido("El tipo de motor no puede estar vacío"))
    if (vehiculoTipo.isEmpty())
        return Err(CitaError.TipoInvalido("El tipo de vehículo no puede estar vacío"))
    return Ok(this)
}

private fun validateVehiculo(cita: CitaViewModel.CrearModificarCitaFormulario): String {
    if (cita.vehiculoMatricula.isEmpty()) return "matriculaVacia"
    val matriculaRegex = Regex("[0-9]{4}-[A-Z]{3}")
    if (!cita.vehiculoMatricula.matches(matriculaRegex)) return "matriculaInvalida"
    if (cita.vehiculoMarca.isEmpty()) return "marcaVacia"
    if (cita.vehiculoModelo.isEmpty()) return "modeloVacio"
    if (cita.vehiculoMatriculacion.isEmpty()) return "matriculacionVacia"
    if (LocalDate.parse(cita.vehiculoMatriculacion).isAfter(LocalDate.now()))
        return "matriculacionPosteriorAActual"
    if (cita.vehiculoRevision.isEmpty()) return "revisionVacia"
    if (LocalDate.parse(cita.vehiculoRevision).isAfter(LocalDate.now()))
        return "revisionPosteriorAActual"
    if (LocalDate.parse(cita.vehiculoRevision).isBefore(LocalDate.parse(cita.vehiculoMatriculacion)))
        return "revisionAnteriorAMatriculacion"
    if (cita.vehiculoMotor.isEmpty()) return "motorVacio"
    if (cita.vehiculoTipo.isEmpty()) return "tipoVacio"
    return "ok"
}
