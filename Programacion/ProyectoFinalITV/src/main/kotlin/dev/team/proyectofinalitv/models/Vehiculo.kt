package dev.team.proyectofinalitv.models

import java.time.LocalDateTime

data class Vehiculo(
    val matricula: String,
    val marca: String,
    val modelo: String,
    val fechaMatriculacion: LocalDateTime,
    val fechaRevision: LocalDateTime,
    val tipoMotor: String,
    val tipoVehiculo: String,
    val dniPropietario: String,
)