package dev.team.proyectofinalitv.models

import java.time.LocalDate

data class Vehiculo(
    val matricula: String,
    val marca: String,
    val modelo: String,
    val fechaMatriculacion: LocalDate,
    val fechaRevision: LocalDate,
    val tipoMotor: TipoMotor,
    val tipoVehiculo: TipoVehiculo,
    val dniPropietario: String
){
    enum class TipoMotor {
        GASOLINA,
        DIESEL,
        HIBRIDO,
        ELECTRICO
    }

    enum class TipoVehiculo {
        COCHE,
        FURGONETA,
        CAMION,
        MOTOCICLETA
    }
}
