package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.config.AppConfig
import dev.team.proyectofinalitv.models.Vehiculo
import dev.team.proyectofinalitv.services.database.DatabaseManager
import dev.team.proyectofinalitv.services.database.DatabaseManagerImpl
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.nio.file.Files
import java.sql.PreparedStatement
import java.sql.Statement
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.io.path.Path

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VehiculoRepositoryImplTest {

    private val appConfig = AppConfig()
    private val dbManager = DatabaseManagerImpl(appConfig)
    private var vehiculoRepository: VehiculoRepositoryImpl = VehiculoRepositoryImpl(dbManager)

    private val dataToTest = mutableListOf<Vehiculo>()

    @BeforeEach
    fun setUp() {
        vehiculoRepository.save( Vehiculo(
            "A",
            "a",
            "a",
            LocalDate.now(),
            LocalDate.now(),
            Vehiculo.TipoMotor.DIESEL,
            Vehiculo.TipoVehiculo.CAMION,
            "a"
        ))
        vehiculoRepository.save( Vehiculo(
            "B",
            "a",
            "a",
            LocalDate.now(),
            LocalDate.now(),
            Vehiculo.TipoMotor.DIESEL,
            Vehiculo.TipoVehiculo.CAMION,
            "a"
        ))
    }

    @Test
    fun updateTest() {
        // El caso del test
        val result = vehiculoRepository.update(dataToTest[0])
        assertTrue(result.matricula == dataToTest[0].matricula)
    }

    @Test
    fun saveTest() {
        // El caso del test
        val result = vehiculoRepository.save(dataToTest[0])
        assertTrue(result.matricula == dataToTest[0].matricula)
    }
}
