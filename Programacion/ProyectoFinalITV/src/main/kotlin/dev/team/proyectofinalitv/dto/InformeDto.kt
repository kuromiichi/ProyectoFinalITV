package dev.team.proyectofinalitv.dto

data class InformeDto(
    val informeId: Long,
    val informeFechaInforme: String,
    val informeFrenado: Double,
    val informeContaminacion: Double,
    val informeInterior: Boolean,
    val informeLuces: Boolean,
    val informeIsApto: Boolean
)
