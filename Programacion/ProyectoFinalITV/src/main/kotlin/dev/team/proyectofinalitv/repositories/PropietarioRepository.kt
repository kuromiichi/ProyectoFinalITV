package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Propietario

interface PropietarioRepository {
    fun findAll(): List<Propietario>
    fun findById(id: Long): Propietario?
    fun save(item: Propietario): Propietario
    fun update(item: Propietario): Propietario
    fun deleteById(id: Long): Boolean
}