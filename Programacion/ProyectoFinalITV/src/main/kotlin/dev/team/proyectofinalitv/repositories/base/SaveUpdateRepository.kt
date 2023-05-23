package dev.team.proyectofinalitv.repositories.base

interface SaveUpdateRepository<T> {
    fun save(item: T): T
    fun update(item: T): T
}
