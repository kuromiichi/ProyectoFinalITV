package dev.team.proyectofinalitv.validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dev.team.proyectofinalitv.errors.CitaError
import dev.team.proyectofinalitv.viewmodels.CitaViewModel
import java.time.LocalDate

fun CitaViewModel.CrearModificarCitaFormulario.validate(): Result<CitaViewModel.CrearModificarCitaFormulario, CitaError> {
    if (fecha.isEmpty()) return Err(CitaError.FechaInvalida("La fecha no puede estar vacía"))
    if (LocalDate.parse(fecha).isBefore(LocalDate.now()))
        return Err(CitaError.FechaInvalida("La fecha no puede ser anterior a la actual"))
    if (hora.isEmpty()) return Err(CitaError.HoraInvalida("La hora no puede estar vacía"))
    if (trabajador.isEmpty())
        return Err(CitaError.TrabajadorInvalido("El trabajador no puede estar vacío"))
    if (propietarioDni.isEmpty()) return Err(CitaError.DniInvalido("El DNI no puede estar vacío"))
    val dniRegex = Regex("[0-9]{8}[A-Z]")
    if (!propietarioDni.matches(dniRegex))
        return Err(CitaError.DniInvalido("El DNI no es válido"))
    if (propietarioNombre.isEmpty()) return Err(CitaError.NombreInvalido("El nombre no puede estar vacío"))
    if (propietarioApellidos.isEmpty())
        return Err(CitaError.ApellidosInvalidos("Los apellidos no pueden estar vacíos"))
    if (propietarioCorreo.isEmpty()) return Err(CitaError.CorreoInvalido("El correo no puede estar vacío"))
    val correoRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")
    if (!propietarioCorreo.matches(correoRegex))
        return Err(CitaError.CorreoInvalido("El correo no es válido"))
    if (propietarioTelefono.isEmpty())
        return Err(CitaError.TelefonoInvalido("El teléfono no puede estar vacío"))
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
            CrearCitaError.RevisionInvalida("La fecha de última revisión no puede ser anterior a la matriculación")
        )
    if (vehiculoMotor.isEmpty())
        return Err(CitaError.MotorInvalido("El tipo de motor no puede estar vacío"))
    if (vehiculoTipo.isEmpty())
        return Err(CitaError.TipoInvalido("El tipo de vehículo no puede estar vacío"))
    return Ok(this)
}


