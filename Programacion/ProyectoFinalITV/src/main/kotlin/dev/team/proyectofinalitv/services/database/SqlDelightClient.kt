package dev.team.proyectofinalitv.services.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import database.*
import dev.team.proyectofinalitv.config.AppConfig
import dev.team.proyectofinalitv.database.AppDatabase

private val logger = mu.KotlinLogging.logger { }

class SqlDelightClient(private val appConfig: AppConfig){

    // Queries de los items que se podrán gestionar
    lateinit var citaQueries: CitaQueries
    lateinit var informeQueries: InformeQueries
    lateinit var propietarioQueries: PropietarioQueries
    lateinit var vehiculeQueries: VehiculeQueries

    // Queries de los items que NO se podrán gestionar
    lateinit var estacionQueries: EstacionQueries
    lateinit var trabajadorQueries: TrabajadorQueries

    private val connectionUrl = appConfig.getUrlDbConection()

    init {
        logger.debug { "Inicializando el gestor de Bases de Datos" }

        initConfig()

        // Borramos la base de datos para no duplicar datos, si está en la config=true
        if (appConfig.getDeleteDb()) {
            removeAllData()
        }

        // Añadimos datos bases por defecto (INSERT)
        insertAllData()
    }

    private fun initConfig() {
        val driver = JdbcSqliteDriver(connectionUrl)
        logger.debug { "Creando Base de Datos" }
        AppDatabase.Schema.create(driver)
        val database = AppDatabase(driver)

        citaQueries = database.citaQueries
        informeQueries = database.informeQueries
        propietarioQueries = database.propietarioQueries
        vehiculeQueries = database.vehiculeQueries

        estacionQueries = database.estacionQueries
        trabajadorQueries = database.trabajadorQueries
    }

    private fun insertAllData() {
        logger.debug { "Añadiendo todos los datos" }

        citaQueries.transaction {
            citaQueries.insertAll()
        }
        informeQueries.transaction {
            informeQueries.insertAll()
        }
        propietarioQueries.transaction {
            propietarioQueries.insertAll()
        }
        vehiculeQueries.transaction {
            vehiculeQueries.insertAll()
        }

        estacionQueries.transaction {
            estacionQueries.insertAll()
        }
        trabajadorQueries.transaction {
            trabajadorQueries.insertAll()
        }
    }

    private fun removeAllData() {
        logger.debug { "Eliminando todos los datos" }

        citaQueries.transaction {
            citaQueries.deleteAll()
        }
        informeQueries.transaction {
            informeQueries.deleteAll()
        }
        propietarioQueries.transaction {
            propietarioQueries.deleteAll()
        }
        vehiculeQueries.transaction {
            vehiculeQueries.deleteAll()
        }

        estacionQueries.transaction {
            estacionQueries.deleteAll()
        }
        trabajadorQueries.transaction {
            trabajadorQueries.deleteAll()
        }
    }
}