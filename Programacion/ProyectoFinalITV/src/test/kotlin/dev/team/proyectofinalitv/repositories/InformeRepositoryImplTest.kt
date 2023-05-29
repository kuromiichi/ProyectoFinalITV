package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.config.AppConfig
import dev.team.proyectofinalitv.models.Informe
import dev.team.proyectofinalitv.services.database.DatabaseManagerImpl
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InformeRepositoryImplTest {

    private val appConfig = AppConfig()
    private val dbManager = DatabaseManagerImpl(appConfig)
    private var informeRepository: InformeRepositoryImpl = InformeRepositoryImpl(dbManager)

    private val dataToTest = mutableListOf<Informe>()

    @BeforeEach
    fun setUp() {
        // Reiniciamos la Base de datos por si acaso se solapan con datos de otros TEST, para que sean independientes
        dbManager.resetDataBase()

        dataToTest.add(
            Informe(
            55,1.2,1.2,true,true,true
        ))
    }

    @Test
    fun updateTest() {
        val allInforme = informeRepository.findAll()
        val informeToUpdate = allInforme[0]
        val contaminacionToUpdate = 10.4

        val informeUpdated = informeRepository.update(informeToUpdate.copy(contaminacion = contaminacionToUpdate))
        val informeEncontrado =  informeRepository.findAll().find { it.id == informeToUpdate.id }

        assertTrue(contaminacionToUpdate == informeEncontrado!!.contaminacion)
    }

    @Test
    fun saveTest() {
        val informeSaved = informeRepository.save(dataToTest[0])
        val informeEncontrado =  informeRepository.findAll().find { it.id == informeSaved.id }
        assertTrue(informeSaved.id == informeEncontrado!!.id)
    }

    @Test
    fun findAllTest(){
        // Tenemos insertado en nuestro DatabaseManager por defecto al crear la Base de datos 10 items
        val allInforme = informeRepository.findAll()
        assertTrue(allInforme.size == 10)
    }
}
