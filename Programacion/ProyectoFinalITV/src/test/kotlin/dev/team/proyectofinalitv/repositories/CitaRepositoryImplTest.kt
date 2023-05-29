package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.config.AppConfig
import dev.team.proyectofinalitv.models.Cita
import dev.team.proyectofinalitv.models.Informe
import dev.team.proyectofinalitv.models.Propietario
import dev.team.proyectofinalitv.models.Vehiculo
import dev.team.proyectofinalitv.services.database.DatabaseManagerImpl
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.mockito.kotlin.*
import java.time.LocalDate
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CitaRepositoryImplTest {

    private val appConfig = AppConfig()
    private val dbManager = DatabaseManagerImpl(appConfig)
    private var citaRepository: CitaRepositoryImpl = CitaRepositoryImpl(dbManager)

    // Necesitamos el repositorio de propietario por la integridad referencial
    private var propietarioRepository: PropietarioRepositoryImpl = PropietarioRepositoryImpl(dbManager)

    // Necesitamos el repositorio de vehículo por la integridad referencial
    private var vehiculoRepository: VehiculoRepositoryImpl = VehiculoRepositoryImpl(dbManager)

    // Necesitamos el repositorio de informe por la integridad referencial
    private var informeRepository: InformeRepositoryImpl = InformeRepositoryImpl(dbManager)

    private val dataToTest = mutableListOf<Cita>()

    @BeforeEach
    fun setUp() {
        // Reiniciamos la Base de datos por si acaso se solapan con datos de otros TEST, para que sean independientes
        dbManager.resetDataBase()

        // Necesitamos siempre primero un propietario para hacer referencia de DNI
        propietarioRepository.save(
            Propietario(
                dni = "12345678C",
                nombre = "Juan",
                apellidos = "Pérez",
                correo = "juan@example.com",
                telefono = "123456789"
            )
        )
        // Necesitamos siempre primero un vehículo para hacer referencia de MATRICULA
        val vehiculo = Vehiculo(
            "A",
            "a",
            "a",
            LocalDate.now(),
            LocalDate.now(),
            Vehiculo.TipoMotor.DIESEL,
            Vehiculo.TipoVehiculo.CAMION,
            "12345678C"
        )
        vehiculoRepository.save(vehiculo)

        // Necesitamos siempre primero un informe para hacer referencia de id_informe
        val informe = informeRepository.save( Informe(-1, 1.2, 1.2, true, true, true))

        dataToTest.add(
            Cita(-1, "Apto", LocalDateTime.now(), informe.id,"j_sanchez",vehiculo.matricula)
        )
    }

    @Test
    fun findAllTest() {
        // Tenemos insertado en nuestro DatabaseManager por defecto al crear la Base de datos 10 items
        val allCitas = citaRepository.findAll()
        assertTrue(allCitas.size == 10)
    }

    @Test
    fun deleteById() {
        val citaSaved = citaRepository.save(dataToTest[0])

        // Borramos la cita
        citaRepository.deleteById(citaSaved.id)

        // Comprobamos no exista
        val citaEncontrada =  citaRepository.findAll().find { it.id == citaSaved.id }
        assertTrue(citaEncontrada == null)
    }

    @Test
    fun update() {
        val citaToUpdate = citaRepository.save(dataToTest[0]) // Lo tenemos como "Apto"
        val aptitudToUpdate = "No apto"

        val citaUpdated = citaRepository.update(citaToUpdate.copy(estado = aptitudToUpdate))
        val citaEncontrada =  citaRepository.findAll().find { it.id == citaToUpdate.id }

        assertTrue(aptitudToUpdate == citaEncontrada!!.estado)
    }

    @Test
    fun save() {
        val citaSaved = citaRepository.save(dataToTest[0])
        val citaEncontrada =  citaRepository.findAll().find { it.id == citaSaved.id }
        assertTrue(citaSaved.id == citaEncontrada!!.id)
    }

    @Test
    fun findById() {
        val citaSaved = citaRepository.save(dataToTest[0])

        // Comprobamos que exista
        val citaEncontrada =  citaRepository.findById(citaSaved.id)
        assertTrue(citaEncontrada!!.id == citaSaved.id)
    }

    @Test
    fun findByMatricula() {
        // La matrícula que buscamos es la asociada a la Cita en el SETUP, matricula = "A"
        val matriculaToSearch = "A"
        val citaSaved = citaRepository.save(dataToTest[0])

        // Comprobamos que exista
        val citaEncontrada =  citaRepository.findByMatricula(matriculaToSearch)
        assertTrue(citaEncontrada!!.id == citaSaved.id)
    }
}
