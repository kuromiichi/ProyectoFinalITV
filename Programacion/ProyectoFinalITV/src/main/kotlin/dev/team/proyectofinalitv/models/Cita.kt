package dev.team.proyectofinalitv.models

import java.time.LocalDate

data class Cita(
    val id: Long,
    val estado: Boolean,
    val fecha: LocalDate,
    val id_informe: Long,
    val usuario_trabajador: String?,
    val matricula_vehiculo: String?
)