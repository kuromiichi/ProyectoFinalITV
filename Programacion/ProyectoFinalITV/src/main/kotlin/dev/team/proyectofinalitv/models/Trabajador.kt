package dev.team.proyectofinalitv.models

import java.time.LocalDate

data class Trabajador(
    val usuario: String,
    val contrasenya: String,
    val nombre: String,
    val correo: String,
    val telefono: String,
    private var _salario: Double,
    val fechaContratacion: LocalDate,
    val especialidad: Especialidad,
    val isResponsable: Boolean = false,
    val idEstacion: Long
) {

    init {
        // Aumentos de salario en función de si el trabajador es responsable y su antigüedad
        if (isResponsable) _salario += 1000
        _salario += (LocalDate.now().year - fechaContratacion.year).floorDiv(3) * 100
    }

    // Propiedad pública inmutable de salario tras aumentos
    val salario get() = _salario

    enum class Especialidad {
        ADMINISTRACION,
        ELECTRICIDAD,
        MOTOR,
        MECANICA,
        INTERIOR
    }
}
