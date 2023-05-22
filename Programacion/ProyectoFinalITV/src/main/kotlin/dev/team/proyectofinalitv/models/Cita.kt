package dev.team.proyectofinalitv.models

import java.time.LocalDateTime

data class Cita(
    val id: Long,
    val estado: String,
    val fecha_hora: LocalDateTime,
    val id_informe: Long,
    val usuario_trabajador: String?,
    val matricula_vehiculo: String?,
)
