package dev.team.proyectofinalitv.config

import java.io.File
import java.io.FileInputStream
import java.util.*

private val logger = mu.KotlinLogging.logger {}

class AppConfig {

    // Constantes de ruta
    private val CONFIG_FILE = "application.properties"
    private val APP_PATH: String = System.getProperty("user.dir")

    private val RESOURCE_PATH_MAIN =
        "$APP_PATH${File.separator}src${File.separator}main${File.separator}resources${File.separator}"

    private var _dbUrlConection: String = "" // URL donde nos conectaremos a la base de datos
    fun getUrlDbConection(): String {
        return _dbUrlConection
    }

    private var _deleteDb: Boolean = false // Si permitimos borrar las tablas
    fun getDeleteDb(): Boolean {
        return _deleteDb
    }

    init {
        loadProperties()
    }

    // Cargamos siempre en primer lugar el fichero properties
    private fun loadProperties() {
        logger.debug { "Cargando properties (configuración)" }

        _dbUrlConection = readProperty("db.url.connection", "jdbc:sqlite:database.db")

        _deleteDb = readProperty("db.delete", "false").toBoolean()
    }

    private fun readProperty(propertie: String, defaultValuePropertie: String): String {
        logger.debug { "Leyendo propiedad: $propertie" }

        val properties = Properties()
        // Accedemos al fichero de propiedades mediante su nombre
        val propertiesFile = ClassLoader.getSystemResource(CONFIG_FILE).file
        // Cargamos el fichero a la clase Properties
        properties.load(FileInputStream(propertiesFile))

        return properties.getProperty(propertie, defaultValuePropertie)
    }
}