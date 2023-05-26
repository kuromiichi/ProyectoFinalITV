package dev.team.proyectofinalitv.repositories.base

interface CRURepository<T, ID> {
    fun save(item: T): T
    fun update(item: T): T
    fun findAll(): List<T>
    fun findById(id: ID): T?
}
