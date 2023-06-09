package dev.team.proyectofinalitv.config

import mu.KotlinLogging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


class AppConfig {

    private val logger = KotlinLogging.logger {}

    // Constantes de ruta
    private val CONFIG_FILE = "application.properties"
    private val APP_PATH: String = System.getProperty("user.dir")

    private val RESOURCE_PATH_MAIN =
        "$APP_PATH${File.separator}src${File.separator}main${File.separator}resources${File.separator}"
    private val RESOURCE_PATH_TEST =
        "$APP_PATH${File.separator}src${File.separator}test${File.separator}resources${File.separator}"

    // Propiedades
    private lateinit var _urlConnection: String
    val urlConnection get() = _urlConnection

    private lateinit var _urlConnectionLocalHost: String
    val urlConnectionLocalHost get() = _urlConnectionLocalHost

    private lateinit var _dbName: String
    val dbName get() = _dbName

    private lateinit var _dbUser: String
    val dbUser get() = _dbUser

    private lateinit var _dbPassword: String
    val dbPassword get() = _dbPassword

    private var _resetDb: Boolean = false
    val resetDb get() = _resetDb

    private lateinit var _dataRoot: String
    val dataRoot get() = _dataRoot

    private lateinit var _dataInput: String
    val dataInput get() = _dataInput

    private lateinit var _dataOutput: String
    val dataOutput get() = _dataOutput

    // Sección de TEST

    private lateinit var _urlConnectionTest: String
    val urlConnectionTest get() = _urlConnectionTest

    private lateinit var _dbNameTest: String
    val dbNameTest get() = _dbNameTest

    private var _testDb: Boolean = false
    val testDb get() = _testDb

    init {
        loadProperties()
        initStorage()
    }

    // Cargamos siempre en primer lugar el fichero properties
    private fun loadProperties() {
        logger.debug { "Cargando properties (configuración)" }

        val props = Properties()
        props.load(ClassLoader.getSystemResourceAsStream(CONFIG_FILE))

        // Database
        _urlConnection = props.getProperty("db.url.connection", "jdbc:mariadb://localhost:3306/itvdam")
        _urlConnectionLocalHost = props.getProperty("db.url.connection.localhost", "jdbc:mariadb://localhost:3306")

        _dbName = props.getProperty("db.name", "ItvDam")
        _dbUser = props.getProperty("db.user", "root")
        _dbPassword = props.getProperty("db.pass", "1234")
        _resetDb = props.getProperty("db.default", "false").toBoolean()

        // Sección de TEST
        _urlConnectionTest = props.getProperty("db.url.connection.test", "jdbc:mariadb://localhost:3306/itvdamtest")
        _dbNameTest = props.getProperty("db.name.test", "itvdamtest")
        _testDb = props.getProperty("db.test", "false").toBoolean()

        // Directorios
        _dataRoot = props.getProperty("data.root", "data")
        _dataInput = dataRoot + File.separator + props.getProperty("data.input", "input")
        _dataOutput = dataRoot + File.separator + props.getProperty("data.output", "output")
    }

    private fun initStorage() {
        logger.debug { "Inicializando storage (configuración)" }

        // Creamos los directorios necesarios para el intercambio de datos
        Files.createDirectories(Paths.get(dataInput))
        Files.createDirectories(Paths.get(dataOutput))
    }
}
