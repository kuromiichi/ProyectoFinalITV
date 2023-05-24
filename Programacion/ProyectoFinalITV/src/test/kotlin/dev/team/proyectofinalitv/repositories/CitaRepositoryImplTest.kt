package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.config.AppConfig
import dev.team.proyectofinalitv.models.Cita
import dev.team.proyectofinalitv.services.database.DatabaseManager
import dev.team.proyectofinalitv.services.database.DatabaseManagerImpl
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.*
import org.mockito.kotlin.verify
import java.sql.PreparedStatement
import java.sql.Statement
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CitaRepositoryImplTest {

    private val appConfig = AppConfig()
    private val dbManager = DatabaseManagerImpl(appConfig)
    private var citaRepository: CitaRepositoryImpl = CitaRepositoryImpl(dbManager)

    private val dataToTest = mutableListOf<Cita>()

    init {
        dataToTest.add(
            Cita(
                1,
                "Apto",
                LocalDateTime.now(),
                1,
                "usuario1",
                "matricula1"
            )
        )
        dataToTest.add(
            Cita(
                2,
                "No apto",
                LocalDateTime.now(),
                2,
                "usuario2",
                "matricula2"
            )
        )
    }

    @Test
    fun findAll() {

    }

    @Test
    fun deleteById() {
        // Caso de test
        val result = citaRepository.deleteById(dataToTest[0].id)
        assertTrue(result)
    }


    @Test
    fun update() {
    }

    @Test
    fun save() {

    }

    @Test
    fun findById() {
    }

    @Test
    fun findByMatricula() {
    }
}
