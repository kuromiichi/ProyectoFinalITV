package dev.team.proyectofinalitv.errors

sealed class CitaError(val message: String) {
    class FechaInvalida(message: String) : CitaError(message)
    class HoraInvalida(message: String) : CitaError(message)
    class TrabajadorInvalido(message: String) : CitaError(message)
    class DniInvalido(message: String) : CitaError(message)
    class NombreInvalido(message: String) : CitaError(message)
    class ApellidosInvalidos(message: String) : CitaError(message)
    class CorreoInvalido(message: String) : CitaError(message)
    class TelefonoInvalido(message: String) : CitaError(message)
    class MatriculaInvalida(message: String) : CitaError(message)
    class MarcaInvalida(message: String) : CitaError(message)
    class ModeloInvalido(message: String) : CitaError(message)
    class MatriculacionInvalida(message: String) : CitaError(message)
    class RevisionInvalida(message: String) : CitaError(message)
    class MotorInvalido(message: String) : CitaError(message)
    class TipoInvalido(message: String) : CitaError(message)
    class CitaYaExiste(message: String) : CitaError(message)
    class CitaNoExiste(message: String) : CitaError(message)

    class ExportInvalidJson : CitaError("Exportación fallida de JSON")
    class ExportInvalidMarkDown : CitaError("Exportación fallida de HTML")
}
