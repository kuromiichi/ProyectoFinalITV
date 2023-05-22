package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Informe

interface InformeRepository {
    fun findAll(): List<Informe>
    fun findById(id: Long): Informe?
    fun save(item: Informe): Informe
    fun update(item: Informe): Informe
    fun deleteById(id: Long): Boolean
}