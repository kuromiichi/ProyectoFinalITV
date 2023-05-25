package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.config.AppConfig
import dev.team.proyectofinalitv.models.Propietario
import dev.team.proyectofinalitv.models.Vehiculo
import dev.team.proyectofinalitv.services.database.DatabaseManagerImpl
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VehiculoRepositoryImplTest {

    private val appConfig = AppConfig()
    private val dbManager = DatabaseManagerImpl(appConfig)
    private var vehiculoRepository: VehiculoRepositoryImpl = VehiculoRepositoryImpl(dbManager)
    // Necesitamos el repositorio de propietario por la integridad referencial
    private var propietarioRepository: PropietarioRepositoryImpl = PropietarioRepositoryImpl(dbManager)

    private val dataToTest = mutableListOf<Vehiculo>()
    private val dataPropietario = mutableListOf<Propietario>()

    @BeforeEach
    fun setUp() {
        // Reiniciamos la Base de datos por si acaso se solapan con datos de otros TEST, para que sean independientes
        dbManager.resetDataBase()

        // Necesitamos siempre primero un propietario para hacer referencia de DNI
        propietarioRepository.save(Propietario(
            dni = "12345678C",
            nombre = "Juan",
            apellidos = "Pérez",
            correo = "juan@example.com",
            telefono = "123456789"
        ))

        dataToTest.add( Vehiculo(
            "A",
            "a",
            "a",
            LocalDate.now(),
            LocalDate.now(),
            Vehiculo.TipoMotor.DIESEL,
            Vehiculo.TipoVehiculo.CAMION,
            "12345678C"
        ))
    }

    @Test
    fun saveTest() {
        // El caso del test
        val result = vehiculoRepository.save(dataToTest[0])
        val findItem =  vehiculoRepository.findAll().find { it.matricula == result.matricula }
        assertTrue(result.matricula == findItem!!.matricula)
    }

    @Test
    fun updateTest() {
        // El caso del test
        val result = vehiculoRepository.update(dataToTest[0])
        assertTrue(result.matricula == dataToTest[0].matricula)
    }
}
