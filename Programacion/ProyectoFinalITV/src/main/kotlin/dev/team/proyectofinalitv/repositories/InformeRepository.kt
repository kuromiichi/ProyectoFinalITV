package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Informe

interface InformeRepository {

    fun save(item: Informe): Informe
    fun update(item: Informe): Informe

}