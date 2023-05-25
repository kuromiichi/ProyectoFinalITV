package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.config.AppConfig
import dev.team.proyectofinalitv.models.Propietario
import dev.team.proyectofinalitv.services.database.DatabaseManagerImpl
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PropietarioRepositoryImplTest {

    private val appConfig = AppConfig()
    private val dbManager = DatabaseManagerImpl(appConfig)
    private var propietarioRepository: PropietarioRepositoryImpl = PropietarioRepositoryImpl(dbManager)

    private val dataToTest = mutableListOf<Propietario>()

    @BeforeEach
    fun setUp() {
        // Reiniciamos la Base de datos por si acaso se solapan con datos de otros TEST, para que sean independientes
        dbManager.resetDataBase()

        dataToTest.add(Propietario(
            dni = "12345678C",
            nombre = "Juan",
            apellidos = "Pérez",
            correo = "juan@example.com",
            telefono = "123456789"
        ))
    }

    @Test
    fun updateTest() {
        val allPropietario = propietarioRepository.findAll()
        val propietarioToUpdate = allPropietario[0]
        val nameToUpdate = "Otro nombre"

        val propietarioUpdated = propietarioRepository.update(propietarioToUpdate.copy(nombre = nameToUpdate))
        val propietarioEncontrado =  propietarioRepository.findAll().find { it.dni == propietarioToUpdate.dni }

        assertTrue(nameToUpdate == propietarioEncontrado!!.nombre)
    }

    @Test
    fun saveTest() {
        val propietarioSaved = propietarioRepository.save(dataToTest[0])
        val propietarioEncontrado =  propietarioRepository.findAll().find { it.dni == propietarioSaved.dni }
        assertTrue(propietarioSaved.dni == propietarioEncontrado!!.dni)
    }

    @Test
    fun findAllTest(){
        // Tenemos insertado en nuestro DatabaseManager por defecto al crear la Base de datos 10 items
        val allPropietario = propietarioRepository.findAll()
        assertTrue(allPropietario.size == 10)
    }
}
