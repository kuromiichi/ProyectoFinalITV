package dev.team.proyectofinalitv.services.database

import dev.team.proyectofinalitv.config.AppConfig
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

class DataBaseManager(private val appConfig: AppConfig) {

    private val logger = mu.KotlinLogging.logger { }

    var connection: Connection? = null // Al abrir la conexión verificamos la nulabilidad
    private var statement: Statement? = null // Para consultas estáticas

    init {
        // En caso de querer la bse de datos por defecto con los valores iniciales
        if (appConfig.getDefaultDb()) {
            logger.debug { "Iniciando DataBaseManager con valores por defecto" }

            resetDataBase()
        } else {
            logger.debug { "Iniciando DataBaseManager" }
        }
    }

    /**
     * En caso de querer reiniciar la base de datos con los valores por defecto, depende de application.properties
     */
    private fun resetDataBase() {
        openConnection()

        // Borramos la base de datos
        deleteDataBase()

        // Creamos la base de datos
        createDataBase()

        // Seleccionamos la base de datos a la que realizar las consultas (USE)
        selectDataBase()

        // Creamos tablas
        createTablesByDefault()

        // Insertamos
        insertDataByDefault()

        closeConnection()
    }

    private fun deleteDataBase() {
        val deleteDatabaseQuery = "DROP DATABASE IF EXISTS ${appConfig.getNameDb()}"

        statement = connection?.createStatement()
        statement?.execute(deleteDatabaseQuery)
    }

    /**
     * Creación de la base de datos inicial
     * @param nombre de la base de datos que crearemos, es recogida por AppConfig de resources
     */
    private fun createDataBase() {
        val createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS ${appConfig.getNameDb()}"

        statement = connection?.createStatement()
        statement?.execute(createDatabaseQuery)
    }

    /**
     * Seleccionar la base de datos para saber donde realizar las consultas
     * @param nombre de la base de datos que utilizaremos, es recogida por AppConfig de resources
     */
    fun selectDataBase() {
        val useDatabaseQuery = "USE ${appConfig.getNameDb()}"

        statement = connection?.createStatement()
        statement?.execute(useDatabaseQuery)
    }

    /**
     * Comprueba si ya hay una conexión establecida, si no la hay establece la conexión con la Base de Datos que tenemos
     */
    fun openConnection() {

        // Comprobar si ya hay una conexión establecida
        if (connection != null && !connection!!.isClosed) {
            logger.debug { "Ya existe una conexión establecida, no se puede conectar nuevamente" }
            return
        }

        // En caso de que no tengamos ninguna conexión establecemos la conexión
        connection =
            DriverManager.getConnection(appConfig.getUrlConnectionDb(), appConfig.getUserDb(), appConfig.getPassDb())

        logger.debug { "Conexión con la Base de Datos establecida" }
    }

    /**
     * Cierra la conexión con la base de datos
     */
    fun closeConnection() {
        logger.debug { "Conexión cerrada con la Base de Datos" }

        statement?.close()
        connection?.close()
    }


    // ============== SECCIÓN DML (en caso de querer tener por defecto inicial la BD) =================

    /**
     * Creación de las tablas por defecto que tendrán nuestras tablas
     */
    private fun createTablesByDefault() {
        val createEstacion =
            """
            CREATE TABLE IF NOT EXISTS Estacion
            (
                id        INTEGER PRIMARY KEY AUTO_INCREMENT,
                nombre    VARCHAR(50)  NOT NULL,
                direccion VARCHAR(100) NOT NULL,
                correo    VARCHAR(100) NOT NULL,
                telefono  VARCHAR(20)  NOT NULL
            );
            """
        statement?.execute(createEstacion)

        val createTrabajador =
            """
            CREATE TABLE IF NOT EXISTS Trabajador
            (
                usuario            VARCHAR(50) PRIMARY KEY,
                contrasenya        VARCHAR(50)   NOT NULL,
                nombre             VARCHAR(100)  NOT NULL,
                correo             VARCHAR(100)  NOT NULL,
                telefono           VARCHAR(20)   NOT NULL,
                salario            DECIMAL(6, 2) NOT NULL,
                fecha_contratacion VARCHAR(10)   NOT NULL,
                especialidad       VARCHAR(20)   NOT NULL,
                id_estacion        INT           REFERENCES Estacion (id) ON DELETE SET NULL
            );
            """
        statement?.execute(createTrabajador)

        val createPropietario =
            """
            CREATE TABLE IF NOT EXISTS Propietario
            (
                dni       VARCHAR(9) PRIMARY KEY,
                nombre    VARCHAR(50)  NOT NULL,
                apellidos VARCHAR(100) NOT NULL,
                correo    VARCHAR(100) NOT NULL,
                telefono  VARCHAR(20)  NOT NULL
            );
            """
        statement?.execute(createPropietario)

        val createVehiculo =
            """
            CREATE TABLE IF NOT EXISTS Vehiculo
            (
                matricula           VARCHAR(15) PRIMARY KEY,
                marca               VARCHAR(30) NOT NULL,
                modelo              VARCHAR(50) NOT NULL,
                fecha_matriculacion VARCHAR(10) NOT NULL,
                fecha_revision      VARCHAR(10) NOT NULL,
                tipo_motor          VARCHAR(20) NOT NULL,
                tipo_vehiculo       VARCHAR(20) NOT NULL,
                dni_propietario     VARCHAR(9) REFERENCES Propietario (dni)
            );
              """
        statement?.execute(createVehiculo)

        val createInforme =
            """
         CREATE TABLE IF NOT EXISTS Informe
        (
            id            INTEGER PRIMARY KEY AUTO_INCREMENT,
            frenado       DECIMAL(4, 2) NOT NULL,
            contaminacion DECIMAL(4, 2) NOT NULL,
            fecha_informe VARCHAR(10)   NOT NULL,
            interior      INTEGER       NOT NULL,
            luces         INTEGER       NOT NULL,
            is_apto       INTEGER       NOT NULL
        );
          """
        statement?.execute(createInforme)

        val createCita =
        """
        CREATE TABLE IF NOT EXISTS Cita
        (
            id                 INTEGER PRIMARY KEY AUTO_INCREMENT,
            estado             VARCHAR(10) NOT NULL,
            fecha_hora         VARCHAR(19) NOT NULL,
            id_informe         INTEGER REFERENCES Informe (id),
            usuario_trabajador VARCHAR(50) REFERENCES Trabajador (usuario),
            matricula_vehiculo VARCHAR(15) REFERENCES Vehiculo (matricula)
        );
          """
        statement?.execute(createCita)
    }

    /**
     * Insertamos los datos por defecto que tendrá nuestra base de datos
     */
    private fun insertDataByDefault() {
        val insertEstacion =
            """
            INSERT INTO Estacion (nombre, direccion, correo, telefono)
            VALUES ('ITV DAM', 'Paseo de la Ermita 15', 'itvdam@itvdam.org', '+34822659855');
            """
        statement?.execute(insertEstacion)

        val insertTrabajador =
            """
            INSERT INTO Trabajador (usuario, contrasenya, nombre, correo, telefono, salario, fecha_contratacion, especialidad,
                        id_estacion)
            VALUES ('j_perez', 'jU5#r3s3g', 'Juan Pérez', 'juan.perez@itvdam.org', '+34912345678', 1650.0, '2022-01-15',
                    'ADMINISTRACION', 1),
                   ('m_lopez', 'M@r14L0p${'$'}', 'María López', 'maria.lopez@itvdam.org', '+34911234567', 1800.0, '2021-09-20',
                    'ELECTRICIDAD', 1),
                   ('d_garcia', 'D4v1dG@rc14', 'David García', 'david.garcia@itvdam.org', '+34910987654', 1700.0, '2023-02-10',
                    'MOTOR', 1),
                   ('a_rodriguez', 'Alic14R0dr1', 'Alicia Rodríguez', 'alicia.rodriguez@itvdam.org', '+34913456789', 1600.0,
                    '2022-11-05', 'MECANICA', 1),
                   ('l_hernandez', 'L@ur4H3rn', 'Laura Hernández', 'laura.hernandez@itvdam.org', '+34914567890', 1750.0,
                    '2021-12-30', 'INTERIOR', 1),
                   ('p_martinez', 'P3dr0M@rt', 'Pedro Martínez', 'pedro.martinez@itvdam.org', '+34915678901', 1650.0, '2023-03-18',
                    'ADMINISTRACION', 1),
                   ('s_gomez', 'Sof14G0m${'$'}', 'Sofía Gómez', 'sofia.gomez@itvdam.org', '+34916789012', 1800.0, '2022-07-22',
                    'ELECTRICIDAD', 1),
                   ('j_sanchez', 'Ju4nS@nch3z', 'Juan Sánchez', 'juan.sanchez@itvdam.org', '+34917890123', 1700.0, '2022-04-02',
                    'MOTOR', 1),
                   ('c_romero', 'C4rm3nR0m', 'Carmen Romero', 'carmen.romero@itvdam.org', '+34918901234', 1600.0, '2023-01-12',
                    'MECANICA', 1),
                   ('r_lopez', 'R0b3rtL0p!', 'Roberto López', 'roberto.lopez@itvdam.org', '+34919012345', 1750.0, '2022-08-27',
                    'INTERIOR', 1);
            """
        statement?.execute(insertTrabajador)

        val insertPropietario =
            """
            INSERT INTO Propietario (dni, nombre, apellidos, correo, telefono)
            VALUES ('86548973P', 'Carlos', 'Gómez', 'carlos.gomez@gmail.com', '+34651234567'),
                   ('54892376T', 'Laura', 'Torres', 'laura.torres@gmail.com', '+34659876543'),
                   ('33571289M', 'Pablo', 'Hernández', 'pablo.hernandez@gmail.com', '+34647851236'),
                   ('75261498N', 'Isabel', 'García', 'isabel.garcia@gmail.com', '+34652148796'),
                   ('45789632R', 'Manuel', 'Ruiz', 'manuel.ruiz@gmail.com', '+34658473920'),
                   ('93651247L', 'Carmen', 'Morales', 'carmen.morales@gmail.com', '+34657982461'),
                   ('28963517S', 'Ricardo', 'Ortega', 'ricardo.ortega@gmail.com', '+34652789413'),
                   ('69587412U', 'Marta', 'Sánchez', 'marta.sanchez@gmail.com', '+34651327894'),
                   ('12547896X', 'Pedro', 'González', 'pedro.gonzalez@gmail.com', '+34652784596'),
                   ('96321478Y', 'Ana', 'López', 'ana.lopez@gmail.com', '+34658963214');
            """
        statement?.execute(insertPropietario)

        val insertVehiculo =
            """
            INSERT INTO Vehiculo (matricula, marca, modelo, fecha_matriculacion, fecha_revision, tipo_motor, tipo_vehiculo,
                      dni_propietario)
            VALUES ('5794-RGK', 'Ford', 'Focus', '2020-01-15', '2023-01-15', 'GASOLINA', 'Coche', '86548973P'),
                   ('1267-BTK', 'Toyota', 'Corolla', '2019-05-20', '2022-05-20', 'ELECTRICO', 'Coche', '54892376T'),
                   ('2803-HNS', 'Volkswagen', 'Golf', '2021-08-10', '2024-08-10', 'DIESEL', 'Coche', '33571289M'),
                   ('4578-FLS', 'Renault', 'Clio', '2018-11-28', '2021-11-28', 'GASOLINA', 'Coche', '75261498N'),
                   ('8026-QJK', 'BMW', 'X5', '2022-02-05', '2025-02-05', 'HIBRIDO', 'SUV', '45789632R'),
                   ('2354-DHP', 'Ford', 'Mustang', '2020-03-12', '2023-03-12', 'ELECTRICO', 'Coche', '93651247L'),
                   ('6908-RSV', 'Nissan', 'Qashqai', '2019-06-30', '2022-06-30', 'DIESEL', 'SUV', '28963517S'),
                   ('1482-CKM', 'Mercedes-Benz', 'A-Class', '2021-09-18', '2024-09-18', 'HIBRIDO', 'Coche', '69587412U'),
                   ('3761-PZX', 'Audi', 'A4', '2022-04-25', '2025-04-25', 'GASOLINA', 'Coche', '12547896X'),
                   ('5139-TBS', 'Volkswagen', 'Polo', '2021-07-05', '2024-07-05', 'DIESEL', 'Coche', '96321478Y');
            """
        statement?.execute(insertVehiculo)

        val insertInforme =
            """
            INSERT INTO Informe (frenado, contaminacion, fecha_informe, interior, luces, is_apto)
            VALUES (8.35, 30.75, '2023-04-20', 1, 1, 0),
                   (6.92, 42.18, '2023-04-22', 0, 1, 1),
                   (3.76, 25.62, '2023-04-25', 1, 0, 0),
                   (9.87, 47.93, '2023-04-28', 0, 1, 1),
                   (5.21, 38.79, '2023-05-02', 1, 0, 0),
                   (7.64, 31.45, '2023-05-05', 0, 1, 1),
                   (2.98, 26.87, '2023-05-08', 1, 0, 0),
                   (8.14, 43.26, '2023-05-10', 1, 1, 1),
                   (4.57, 28.93, '2023-05-12', 0, 0, 0),
                   (9.23, 34.51, '2023-05-15', 1, 1, 1);
            """
        statement?.execute(insertInforme)

        val insertCita =
            """
            INSERT INTO Cita (estado, fecha, id_informe, usuario_trabajador, matricula_vehiculo)
            VALUES (0, '2023-04-20', 1, 'j_perez', '5794-RGK'),
                   (1, '2023-04-22', 2, 'm_lopez', '1267-BTK'),
                   (0, '2023-04-25', 3, 'd_garcia', '2803-HNS'),
                   (1, '2023-04-28', 4, 'a_rodriguez', '4578-FLS'),
                   (0, '2023-05-02', 5, 'l_hernandez', '8026-QJK'),
                   (1, '2023-05-05', 6, 'p_martinez', '2354-DHP'),
                   (0, '2023-05-08', 7, 's_gomez', '6908-RSV'),
                   (1, '2023-05-10', 8, 'j_sanchez', '1482-CKM'),
                   (0, '2023-05-12', 9, 'c_romero', '3761-PZX'),
                   (1, '2023-05-15', 10, 'r_lopez', '5139-TBS');
            """
        statement?.execute(insertCita)
    }
}