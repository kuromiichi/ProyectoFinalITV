package dev.team.proyectofinalitv.config

import java.io.File
import java.io.FileInputStream
import java.util.*


class AppConfig {

    private val logger = mu.KotlinLogging.logger {}

    // Constantes de ruta
    private val CONFIG_FILE = "application.properties"
    private val APP_PATH: String = System.getProperty("user.dir")

    private val RESOURCE_PATH_MAIN =
        "$APP_PATH${File.separator}src${File.separator}main${File.separator}resources${File.separator}"

    private var _urlConnectionSb: String = "" // URL donde nos conectaremos a la base de datos
    fun getUrlConnectionDb(): String {
        return _urlConnectionSb
    }

    private var _nameDb: String = ""
    fun getNameDb(): String {
        return _nameDb
    }

    private var _passDb: String = ""
    fun getPassDb(): String {
        return _passDb
    }

    private var _userDb: String = ""
    fun getUserDb(): String {
        return _userDb
    }

    private var _defaultDb: Boolean = false // Si permitimos reiniciar por defecto la base de datos
    fun getDefaultDb(): Boolean {
        return _defaultDb
    }

    init {
        loadProperties()
    }

    // Cargamos siempre en primer lugar el fichero properties
    private fun loadProperties() {
        logger.debug { "Cargando properties (configuración)" }

        _urlConnectionSb = readProperty("db.url.connection", "jdbc:mariadb://localhost:3306")
        _nameDb = readProperty("db.name", "ItvDam")
        _userDb = readProperty("db.user", "root")
        _passDb = readProperty("db.pass", "1234")
        _defaultDb = readProperty("db.default", "false").toBoolean()
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