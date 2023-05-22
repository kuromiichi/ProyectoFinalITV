package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Vehiculo
import dev.team.proyectofinalitv.services.database.DataBaseManager
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.mockito.kotlin.mock
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VehiculoRepositoryImplTest {

    // Solo falseamos la inyección de dependencia, no habrá funciones mockeadas
    private val mockDatabaseManager: DataBaseManager = mock<DataBaseManager>()

    private var vehiculoRepository: VehiculoRepositoryImpl = VehiculoRepositoryImpl(mockDatabaseManager)

    private val dataToTest = mutableListOf<Vehiculo>()

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
        val result = vehiculoRepository.update(dataToTest[0])
        assertTrue(result.matricula == dataToTest[0].matricula)
    }

    @Test
    fun saveTest() {
        val result = vehiculoRepository.save(dataToTest[0])
        assertTrue(result.matricula == dataToTest[0].matricula)
    }
}