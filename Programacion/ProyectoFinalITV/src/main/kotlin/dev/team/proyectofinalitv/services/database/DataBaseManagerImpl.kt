package dev.team.proyectofinalitv.services.database

import dev.team.proyectofinalitv.config.AppConfig
import mu.KotlinLogging
import java.sql.Connection
import java.sql.DriverManager

class DataBaseManagerImpl(private val appConfig: AppConfig) : DataBaseManager {

    private val logger = KotlinLogging.logger { }

    val con: Connection
        get() {
            logger.debug { "Creando conexión con la base de datos" }
            return DriverManager.getConnection(appConfig.urlConnection, appConfig.dbUser, appConfig.dbPassword)
        }

    init {
        when (appConfig.resetDb) {
            true -> {
                logger.info { "Inicializando DBManager con valores por defecto" }
                resetDataBase()
            }

            false -> {
                logger.info { "Inicializando DBManager" }
            }
        }
    }

    /**
     * En caso de querer reiniciar la base de datos con los valores por defecto, depende de application.properties
     */
    private fun resetDataBase() {
        // Borramos la base de datos
        dropDatabase()

        // Creamos la base de datos
        createDatabase()

        // Creamos tablas
        createTablesByDefault()

        // Insertamos
        insertDataByDefault()

    }

    /**
     * Borrado de la base de datos inicial
     */
    override fun dropDatabase() {
        logger.debug { "Borrando base de datos" }
        val query = "DROP DATABASE IF EXISTS ${appConfig.dbName}"
        val stmt = con.prepareStatement(query)
        stmt.use { it.executeUpdate() }
    }

    /**
     * Creación de la base de datos inicial
     */
    override fun createDatabase() {
        logger.debug { "Creando base de datos" }
        val query = "CREATE DATABASE IF NOT EXISTS ${appConfig.dbName}"
        val stmt = con.prepareStatement(query)
        stmt.use { it.executeUpdate() }
    }

    /**
     * Seleccionar la base de datos para saber donde realizar las consultas
     */
    override fun Connection.selectDatabase() {
        logger.debug { "Seleccionando base de datos" }
        this.catalog = appConfig.dbName
    }

    // ============== SECCIÓN DML (en caso de querer tener por defecto inicial la BD) =================

    /**
     * Creación de las tablas por defecto que tendrán nuestras tablas
     */
    private fun createTablesByDefault() {
        con.use { con ->
            con.selectDatabase()
            
            // Estación
            val estacionCreate = """
                CREATE TABLE IF NOT EXISTS Estacion
                (
                    id        INTEGER PRIMARY KEY AUTO_INCREMENT,
                    nombre    VARCHAR(50)  NOT NULL,
                    direccion VARCHAR(100) NOT NULL,
                    correo    VARCHAR(100) NOT NULL,
                    telefono  VARCHAR(20)  NOT NULL
                );
            """.trimIndent()
            val estacionStmt = this.con.prepareStatement(estacionCreate)
            estacionStmt.use { it.executeUpdate() }

            // Trabajador
            val trabajadorCreate = """
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
                
            """.trimIndent()
            val trabajadorStmt = this.con.prepareStatement(trabajadorCreate)
            trabajadorStmt.use { it.executeUpdate() }

            // Propietario
            val propietarioCreate = """
                CREATE TABLE IF NOT EXISTS Propietario
                (
                    dni       VARCHAR(9) PRIMARY KEY,
                    nombre    VARCHAR(50)  NOT NULL,
                    apellidos VARCHAR(100) NOT NULL,
                    correo    VARCHAR(100) NOT NULL,
                    telefono  VARCHAR(20)  NOT NULL
                );
            """.trimIndent()
            val propietarioStmt = this.con.prepareStatement(propietarioCreate)
            propietarioStmt.use { it.executeUpdate() }

            // Vehículo
            val vehiculoCreate = """
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
            """.trimIndent()
            val vehiculoStmt = this.con.prepareStatement(vehiculoCreate)
            vehiculoStmt.use { it.executeUpdate() }

            // Informe
            val informeCreate = """
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
            """.trimIndent()
            val informeStmt = this.con.prepareStatement(informeCreate)
            informeStmt.use { it.executeUpdate() }

            // Cita
            val citaCreate = """
                CREATE TABLE IF NOT EXISTS Cita
                (
                    id                 INTEGER PRIMARY KEY AUTO_INCREMENT,
                    estado             VARCHAR(10) NOT NULL,
                    fecha_hora         VARCHAR(30) NOT NULL,
                    id_informe         INTEGER REFERENCES Informe (id),
                    usuario_trabajador VARCHAR(50) REFERENCES Trabajador (usuario),
                    matricula_vehiculo VARCHAR(15) REFERENCES Vehiculo (matricula)
                );
            """.trimIndent()
            val citaStmt = this.con.prepareStatement(citaCreate)
            citaStmt.use { it.executeUpdate() }
        }
    }


    /**
     * Insertamos los datos por defecto que tendrá nuestra base de datos
     */
    private fun insertDataByDefault() {
        con.use { con ->
            con.selectDatabase()
            // Estación
            val estacionInsert = """
                INSERT INTO Estacion (nombre, direccion, correo, telefono)
                VALUES ('ITV DAM', 'Paseo de la Ermita 15', 'itvdam@itvdam.org', '+34822659855');
            """.trimIndent()
            val estacionStmt = con.prepareStatement(estacionInsert)
            estacionStmt.use { it.executeUpdate() }
            
            // Trabajador
            val trabajadorInsert = """
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
            """.trimIndent()
            val trabajadorStmt = con.prepareStatement(trabajadorInsert)
            trabajadorStmt.use { it.executeUpdate() }
            
            // Propietario
            val propietarioInsert = """
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
            """.trimIndent()
            val propietarioStmt = con.prepareStatement(propietarioInsert)
            propietarioStmt.use { it.executeUpdate() }
            
            // Vehiculo
            val vehiculoInsert = """
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
            """.trimIndent()
            val vehiculoStmt = con.prepareStatement(vehiculoInsert)
            vehiculoStmt.use { it.executeUpdate() }
            
            // Informe
            val informeInsert = """
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
            """.trimIndent()
            val informeStmt = con.prepareStatement(informeInsert)
            informeStmt.use { it.executeUpdate() }
            
            // Cita
            val citaInsert = """
                INSERT INTO Cita (estado, fecha_hora, id_informe, usuario_trabajador, matricula_vehiculo)
                VALUES ('No apto', '2023-04-20T09:00:00', 1, 'j_perez', '5794-RGK'),
                       ('Apto', '2023-04-22T13:30:00', 2, 'm_lopez', '1267-BTK'),
                       ('No apto', '2023-04-25T10:30:00', 3, 'd_garcia', '2803-HNS'),
                       ('Apto', '2023-04-28T15:30:00', 4, 'a_rodriguez', '4578-FLS'),
                       ('No apto', '2023-05-02T11:00:00', 5, 'l_hernandez', '8026-QJK'),
                       ('Apto', '2023-05-05T14:30:00', 6, 'p_martinez', '2354-DHP'),
                       ('No apto', '2023-05-08T09:30:00', 7, 's_gomez', '6908-RSV'),
                       ('Apto', '2023-05-10T16:00:00', 8, 'j_sanchez', '1482-CKM'),
                       ('No apto', '2023-05-12T12:30:00', 9, 'c_romero', '3761-PZX'),
                       ('Apto', '2023-05-15T14:00:00', 10, 'r_lopez', '5139-TBS');
            """.trimIndent()
            val citaStmt = con.prepareStatement(citaInsert)
            citaStmt.use { it.executeUpdate() }
        }
    }
}
