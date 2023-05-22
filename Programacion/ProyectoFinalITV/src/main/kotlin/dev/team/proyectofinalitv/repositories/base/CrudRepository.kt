package dev.team.proyectofinalitv.repositories.base

import dev.team.proyectofinalitv.models.Cita

interface CrudRepository<T, ID> {
    fun findAll(): List<T>
    fun findById(id: ID): Cita?
    fun save(item: T): T
    fun update(item: T): T
    fun deleteById(id: ID): Boolean
}
