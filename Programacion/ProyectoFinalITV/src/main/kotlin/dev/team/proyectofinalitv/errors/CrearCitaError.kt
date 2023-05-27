package dev.team.proyectofinalitv.errors

sealed class CrearCitaError(val message: String) {
    class FechaInvalida(message: String) : CrearCitaError(message)
    class HoraInvalida(message: String) : CrearCitaError(message)
    class TrabajadorInvalido(message: String) : CrearCitaError(message)
    class DniInvalido(message: String) : CrearCitaError(message)
    class NombreInvalido(message: String) : CrearCitaError(message)
    class ApellidosInvalidos(message: String) : CrearCitaError(message)
    class CorreoInvalido(message: String) : CrearCitaError(message)
    class TelefonoInvalido(message: String) : CrearCitaError(message)
    class MatriculaInvalida(message: String) : CrearCitaError(message)
    class MarcaInvalida(message: String) : CrearCitaError(message)
    class ModeloInvalido(message: String) : CrearCitaError(message)
    class MatriculacionInvalida(message: String) : CrearCitaError(message)
    class RevisionInvalida(message: String) : CrearCitaError(message)
    class MotorInvalido(message: String) : CrearCitaError(message)
    class TipoInvalido(message: String) : CrearCitaError(message)
    class CitaYaExiste(message: String) : CrearCitaError(message)
}
