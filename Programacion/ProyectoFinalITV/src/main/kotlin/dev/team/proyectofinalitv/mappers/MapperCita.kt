package dev.team.proyectofinalitv.mappers

import database.CitaTable
import dev.team.proyectofinalitv.models.Cita
import java.time.LocalDate

class MapperCita {
    fun citaTabletoCita(itemTable: CitaTable): Cita {
        return Cita(
            itemTable.id,
            itemTable.estado.toInt() == 1,
            LocalDate.parse(itemTable.fecha),
            itemTable.id_informe!!.toLong(),
            itemTable.usuario_trabajador,
            itemTable.matricula_vehiculo
        )
    }
}