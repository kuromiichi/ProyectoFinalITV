package dev.team.proyectofinalitv.repositories.base

interface CRURepository<T> {
    fun save(item: T): T
    fun update(item: T): T
    fun getAll(): List<T>
}
