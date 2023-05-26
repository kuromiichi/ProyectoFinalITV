package dev.team.proyectofinalitv.models

data class Informe(
    val id: Long,
    val frenado: Double,
    val contaminacion: Double,
    val interior: Boolean,
    val luces: Boolean,
    val isApto: Boolean,
)
