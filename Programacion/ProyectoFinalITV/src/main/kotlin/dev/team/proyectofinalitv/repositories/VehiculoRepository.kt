package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Vehiculo

interface VehiculoRepository {
    fun findAll(): List<Vehiculo>
    fun findById(id: Long): Vehiculo?
    fun save(item: Vehiculo): Vehiculo
    fun update(item: Vehiculo): Vehiculo
    fun deleteById(id: Long): Boolean
}