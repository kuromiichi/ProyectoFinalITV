package dev.team.proyectofinalitv.repositories.base

import dev.team.proyectofinalitv.models.Cite

interface CrudRepository<T, ID> {
    fun findAll(): List<T>
    fun findById(id: ID): Cite?
    fun save(item: T): T
    fun update(item: T): T
    fun deleteById(id: ID): Boolean
}