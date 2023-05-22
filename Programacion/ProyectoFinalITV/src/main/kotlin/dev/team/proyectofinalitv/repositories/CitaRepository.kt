package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Cite
import dev.team.proyectofinalitv.repositories.base.CrudRepository

interface CitaRepository : CrudRepository<Cite, Long> {
    fun findByMatricula(matricula: String): Cite?
}