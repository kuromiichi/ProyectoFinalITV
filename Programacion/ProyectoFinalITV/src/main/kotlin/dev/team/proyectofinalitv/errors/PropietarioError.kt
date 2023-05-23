package dev.team.proyectofinalitv.errors

sealed class PropietarioError(val message: String) {
    class DniInvalid(message: String) : PropietarioError(message)
    class NombreInvalid(message: String) : PropietarioError(message)
    class ApellidosInvalid(message: String) : PropietarioError(message)
    class CorreoInvalid(message: String) : PropietarioError(message)
    class TelefonoInvalid(message: String) : PropietarioError(message)
    class NotFound(message: String) : PropietarioError(message)
}