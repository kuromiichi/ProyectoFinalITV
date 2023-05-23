package dev.team.proyectofinalitv.models

import java.time.LocalDateTime

data class Cita(
    val id: Long,
    val estado: String,
    val fechaHora: LocalDateTime,
    val idInforme: Long,
    val usuarioTrabajador: String,
    val matriculaVehiculo: String,
)
