package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.mappers.MapperCita
import dev.team.proyectofinalitv.models.Cita
import dev.team.proyectofinalitv.services.database.SqlDelightClient
import mu.KotlinLogging
import java.time.LocalDate

private val logger = KotlinLogging.logger {}

class CitaRepositoryImpl( private val databaseClient: SqlDelightClient): CitaRepository {

    // Cargamos el cliente, donde tenemos las consultas
    private val database = databaseClient.citaQueries

    override fun findAll(): List<Cita> {
        logger.debug { "findAll" }

        return database.selectAll().executeAsList().map { MapperCita().citaTabletoCita(it) }
    }

}