package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Cita
import dev.team.proyectofinalitv.repositories.base.CrudRepository

interface CitaRepository : CrudRepository<Cita, Long> {
    fun findByMatricula(matricula: String): Cita?
}
