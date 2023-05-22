package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Vehiculo
import dev.team.proyectofinalitv.services.database.DataBaseManager
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.sql.PreparedStatement
import java.sql.Statement
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VehiculoRepositoryImplTest {

    private val mockDatabaseManager: DataBaseManager = mock()

    private val mockStatement: Statement = mock()

    private val mockPreparedStatement: PreparedStatement = mock()

    private var vehiculoRepository: VehiculoRepositoryImpl = VehiculoRepositoryImpl(mockDatabaseManager)

    private val dataToTest = mutableListOf<Vehiculo>()

    @AfterEach
    fun setUp() {
        // Verificar llamadas a los métodos en el orden correcto, al final de cada test
        /*verify(mockDatabaseManager).openConnection()
        verify(mockDatabaseManager).selectDataBase()
        verify(mockDatabaseManager).prepareStatement(anyString())
        verify(mockPreparedStatement).executeUpdate()
        verify(mockPreparedStatement).close()
        verify(mockDatabaseManager).closeConnection()*/
    }

    init {
        dataToTest.add(
            Vehiculo(
                "A",
                "a",
                "a",
                LocalDate.now(),
                LocalDate.now(),
                Vehiculo.TipoMotor.DIESEL,
                Vehiculo.TipoVehiculo.CAMION,
                "a"
            )
        )
        dataToTest.add(
            Vehiculo(
                "B",
                "a",
                "a",
                LocalDate.now(),
                LocalDate.now(),
                Vehiculo.TipoMotor.DIESEL,
                Vehiculo.TipoVehiculo.CAMION,
                "a"
            )
        )
    }

    @Test
    fun updateTest() {
        // Falseamos las acciones
        whenever(mockDatabaseManager.prepareStatement(anyString())).thenReturn(mockPreparedStatement)

        // El caso del test
        val result = vehiculoRepository.update(dataToTest[0])
        assertTrue(result.matricula == dataToTest[0].matricula)
    }

    @Test
    fun saveTest() {
        // Falseamos las acciones
        whenever(mockDatabaseManager.prepareStatement(anyString())).thenReturn(mockPreparedStatement)

        // El caso del test
        val result = vehiculoRepository.save(dataToTest[0])
        assertTrue(result.matricula == dataToTest[0].matricula)
    }
}