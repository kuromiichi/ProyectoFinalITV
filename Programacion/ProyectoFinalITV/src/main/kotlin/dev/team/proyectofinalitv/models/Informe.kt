package dev.team.proyectofinalitv.models

import java.time.LocalDate

data class Informe(
    val id: Long,
    val frenado: Double,
    val contaminacion: Double,
    val fechaInforme: LocalDate,
    val interior: Boolean,
    val luces: Boolean,
    val isApto: Boolean,
)
