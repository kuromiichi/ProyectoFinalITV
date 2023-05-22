package dev.team.proyectofinalitv.errors

sealed class VehiculoError(val message: String) {
    class MatriculaInvalid(message: String) : VehiculoError(message)
    class MarcaInvalid(message: String) : VehiculoError(message)
    class ModeloInvalid(message: String) : VehiculoError(message)
    class FechaMatriculacionInvalid(message: String) : VehiculoError(message)
    class FechaRevisionInvalid(message: String) : VehiculoError(message)
    class TipoMotorInvalid(message: String) : VehiculoError(message)
    class TipoVehiculoInvalid(message: String) : VehiculoError(message)
    class DniPropietarioInvalid(message: String) : VehiculoError(message)
    class NotFound(message: String) : VehiculoError(message)
}