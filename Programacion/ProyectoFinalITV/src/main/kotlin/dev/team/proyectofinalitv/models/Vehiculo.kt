package dev.team.proyectofinalitv.models

import java.time.LocalDate

data class Vehiculo(
    val matricula: String,
    val marca: String,
    val modelo: String,
    val fechaMatriculacion: LocalDate,
    val fechaRevision: LocalDate,
    val tipoMotor: String,
    val tipoVehiculo: String,
    val dniPropietario: String,
)
