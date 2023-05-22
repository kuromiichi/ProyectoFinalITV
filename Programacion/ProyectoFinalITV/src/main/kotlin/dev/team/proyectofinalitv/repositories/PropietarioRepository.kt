package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Propietario

interface PropietarioRepository {
    fun save(item: Propietario): Propietario
    fun update(item: Propietario): Propietario

}