package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Cita
import dev.team.proyectofinalitv.services.database.DatabaseManager
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

    private val mockDatabaseManager: DatabaseManager = mock()

    private val mockStatement: Statement = mock()

    private val mockPreparedStatement: PreparedStatement = mock()

    private var citaRepository: CitaRepositoryImpl = CitaRepositoryImpl(mockDatabaseManager)

    private val dataToTest = mutableListOf<Cita>()

    init {
        dataToTest.add(
            Cita(
                1,
                "Pendiente",
                LocalDateTime.now(),
                1,
                "usuario1",
                "matricula1"
            )
        )
        dataToTest.add(
            Cita(
                2,
                "Confirmada",
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
        // Falseamos las acciones
        whenever(mockDatabaseManager.createStatement()).thenReturn(mockStatement)

        // Caso de test
        val result = citaRepository.deleteById(dataToTest[0].id)
        assertTrue(result)

        // Verificar llamadas a los métodos en el orden correcto
        verify(mockDatabaseManager).openConnection()
        verify(mockDatabaseManager).selectDataBase()
        verify(mockDatabaseManager).createStatement()
        verify(mockStatement).executeUpdate(anyString())
        verify(mockStatement).close()
        verify(mockDatabaseManager).closeConnection()
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
