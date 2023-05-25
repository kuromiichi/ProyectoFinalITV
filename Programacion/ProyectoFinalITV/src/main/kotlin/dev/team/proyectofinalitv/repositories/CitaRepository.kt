package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Cita
import dev.team.proyectofinalitv.repositories.base.CRURepository

interface CitaRepository : CRURepository<Cita> {
    override fun findAll(): List<Cita>
    fun findById(id: Long): Cita?
    override fun save(item: Cita): Cita
    override fun update(item: Cita): Cita
    fun deleteById(id: Long): Boolean
    fun findByMatricula(matricula: String): Cita?
}
