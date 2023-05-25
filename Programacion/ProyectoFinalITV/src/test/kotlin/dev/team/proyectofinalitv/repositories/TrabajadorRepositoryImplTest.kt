package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.config.AppConfig
import dev.team.proyectofinalitv.models.Trabajador
import dev.team.proyectofinalitv.services.database.DatabaseManagerImpl
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate

class TrabajadorRepositoryImplTest {

    private val appConfig = AppConfig()
    private val dbManager = DatabaseManagerImpl(appConfig)
    private var trabajadorRepository: TrabajadorRepositoryImpl = TrabajadorRepositoryImpl(dbManager)

    private val dataToTest = mutableListOf<Trabajador>()

    @BeforeEach
    fun setUp() {
        // Reiniciamos la Base de datos por si acaso se solapan con datos de otros TEST, para que sean independientes
        dbManager.resetDataBase()

        dataToTest.add(Trabajador(
            "a_maroto", "jU5#r3s3g", "Juan Pérez", "juan.perez@itvdam.org", "+34912345678", 1650.0,
            LocalDate.now(), Trabajador.Especialidad.ADMINISTRACION, false, 1)
        )
    }

    @Test
    fun updateTest() {
        val allTrabajador = trabajadorRepository.findAll()
        val trabajadorToUpdate = allTrabajador[0]
        val nombreToUpdate = "Angel"

        val trabajadorUpdated = trabajadorRepository.update(trabajadorToUpdate.copy(nombre = nombreToUpdate))
        val trabajadorEncontrado =  trabajadorRepository.findAll().find { it.usuario == trabajadorToUpdate.usuario }

        assertTrue(nombreToUpdate == trabajadorEncontrado!!.nombre)
    }

    @Test
    fun saveTest() {
        val trabajadorSaved = trabajadorRepository.save(dataToTest[0])
        val trabajadorEncontrado =  trabajadorRepository.findAll().find { it.usuario == trabajadorSaved.usuario }
        assertTrue(trabajadorSaved.usuario == trabajadorEncontrado!!.usuario)
    }

    @Test
    fun findAllTest(){
        // Tenemos insertado en nuestro DatabaseManager por defecto al crear la Base de datos 10 items
        val allTrabajador = trabajadorRepository.findAll()
        assertTrue(allTrabajador.size == 10)
    }
}
