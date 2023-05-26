package dev.team.proyectofinalitv.errors

sealed class InformeError(val message: String) {
    class IdInvalid(message: String) : InformeError(message)
    class FrenadoInvalid(message: String) : InformeError(message)
    class ContaminacionInvalid(message: String) : InformeError(message)
    class InteriorInvalid(message: String) : InformeError(message)
    class LucesInvalid(message: String) : InformeError(message)
    class IsAptoInvalid(message: String) : InformeError(message)
    class NotFound(message: String) : InformeError(message)
}
