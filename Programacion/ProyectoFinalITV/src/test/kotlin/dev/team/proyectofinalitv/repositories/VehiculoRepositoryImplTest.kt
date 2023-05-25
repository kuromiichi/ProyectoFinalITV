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
    fun updateTest() {
        val allVehicule = vehiculoRepository.findAll()
        val vehiculoToUpdate = allVehicule[0]
        val marcaToUpdate = "Otra marca"

        val vehiculoUpdated = vehiculoRepository.update(vehiculoToUpdate.copy(marca = marcaToUpdate))
        val vehiculoEncontrado =  vehiculoRepository.findAll().find { it.matricula == vehiculoToUpdate.matricula }

        assertTrue(marcaToUpdate == vehiculoEncontrado!!.marca)
    }

    @Test
    fun saveTest() {
        val vehiculoSaved = vehiculoRepository.save(dataToTest[0])
        val vehiculoEncontrado =  vehiculoRepository.findAll().find { it.matricula == vehiculoSaved.matricula }
        assertTrue(vehiculoSaved.matricula == vehiculoEncontrado!!.matricula)
    }

    @Test
    fun findAllTest(){
        // Tenemos insertado en nuestro DatabaseManager por defecto al crear la Base de datos 10 items
        val allVehicule = vehiculoRepository.findAll()
        assertTrue(allVehicule.size == 10)
    }
}
