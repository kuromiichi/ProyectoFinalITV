package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.config.AppConfig
import dev.team.proyectofinalitv.models.Propietario
import dev.team.proyectofinalitv.models.Vehiculo
import dev.team.proyectofinalitv.services.database.DatabaseManagerImpl
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PropietarioRepositoryImplTest {

    private val appConfig = AppConfig()
    private val dbManager = DatabaseManagerImpl(appConfig)
    private var propietarioRepository: PropietarioRepositoryImpl = PropietarioRepositoryImpl(dbManager)

    private val dataToTest = mutableListOf<Propietario>()

    init{
        dataToTest.add( Propietario(
            dni = "12345678C",
            nombre = "Juan",
            apellidos = "Pérez",
            correo = "juan@example.com",
            telefono = "123456789"
        ))
        dataToTest.add(Propietario(
            dni = "12345678C",
            nombre = "Juan",
            apellidos = "Pérez",
            correo = "juan@example.com",
            telefono = "123456789"
        ))
    }
/*    @BeforeEach
    fun setUp() {
        propietarioRepository.save( Propietario(
            dni = "12345678C",
            nombre = "Juan",
            apellidos = "Pérez",
            correo = "juan@example.com",
            telefono = "123456789"
        ))
        propietarioRepository.save(Propietario(
            dni = "12345678C",
            nombre = "Juan",
            apellidos = "Pérez",
            correo = "juan@example.com",
            telefono = "123456789"
        )
        )
    }*/

    @Test
    fun updateTest() {
        // El caso del test
        val result = propietarioRepository.update(dataToTest[0])
        assertTrue(result.dni == dataToTest[0].dni)
    }

    @Test
    fun saveTest() {
        // El caso del test
        val result = propietarioRepository.save(dataToTest[0])
        assertTrue(result.dni == dataToTest[0].dni)
    }
}
