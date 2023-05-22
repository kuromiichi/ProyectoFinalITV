package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Cita

interface CitaRepository {
    fun findAll(): List<Cita>
    fun findById(id: Long): Cita?
    fun save(item: Cita): Cita
    fun update(item: Cita): Cita
    fun deleteById(id: Long): Boolean
    fun findByMatricula(matricula: String): Cita?
}
