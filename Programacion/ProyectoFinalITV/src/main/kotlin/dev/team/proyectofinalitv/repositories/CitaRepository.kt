package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Cita

interface CitaRepository {
    fun findAll(): List<Cita>
}