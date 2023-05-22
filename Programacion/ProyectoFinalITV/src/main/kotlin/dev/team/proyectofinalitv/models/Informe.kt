package dev.team.proyectofinalitv.models

import java.time.LocalDate
import java.time.LocalDateTime

data class Informe(
    val id: Long,
    val frenado: Double,
    val contaminacion: Double,
    val fechaInforme: LocalDate,
    val interior: Boolean,
    val luces: Boolean,
    val isApto: Boolean,
)