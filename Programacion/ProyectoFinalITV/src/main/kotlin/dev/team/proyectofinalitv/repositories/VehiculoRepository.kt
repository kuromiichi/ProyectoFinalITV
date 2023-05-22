package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Vehiculo

interface VehiculoRepository {

    fun save(item: Vehiculo): Vehiculo
    fun update(item: Vehiculo): Vehiculo

}