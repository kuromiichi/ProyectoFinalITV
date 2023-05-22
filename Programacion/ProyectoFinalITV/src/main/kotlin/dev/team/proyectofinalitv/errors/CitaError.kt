package dev.team.proyectofinalitv.errors

sealed class CitaError(val message: String) {
    class IdInvalid(message: String) : CitaError(message)
    class EstadoInvalid(message: String) : CitaError(message)
    class FechaHoraInvalid(message: String) : CitaError(message)
    class IdInformeInvalid(message: String) : CitaError(message)
    class UsuarioTrabajadorInvalid(message: String) : CitaError(message)
    class MatriculaVehiculoInvalid(message: String) : CitaError(message)
    class NotFound(message: String) : CitaError(message)
    class ExportInvalid : CitaError("Exportación fallida")
}