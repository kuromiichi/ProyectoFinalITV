package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Cite
import dev.team.proyectofinalitv.services.database.DataBaseManager
import mu.KotlinLogging
import java.sql.Statement

class CitaRepositoryImpl(private val databaseManager: DataBaseManager) : CitaRepository {

    private val logger = KotlinLogging.logger {}

    /**
     * Busca todas las citas que se encuentren en la base de datos
     * @return la lista de todas las citas
     */
    override fun findAll(): List<Cite> {
        logger.debug { "Buscando todas las citas" }

        val cites = mutableListOf<Cite>()

        databaseManager.openConnection()

        // Seleccionamos la base de datos a la que realizar las consultas
        databaseManager.selectDataBase()

        val statement = databaseManager.connection?.createStatement()

        val sql = "SELECT * FROM Cita"
        val result = statement?.executeQuery(sql)

        // Recorremos los resultados hasta que nos de: 'false'
        while (result?.next() == true) {
            val cite = Cite(
                id = result.getLong("id"),
                estado = result.getBoolean("estado"),
                fecha = result.getDate("fecha").toLocalDate(),
                id_informe = result.getLong("id_informe"),
                usuario_trabajador = result.getString("usuario_trabajador"),
                matricula_vehiculo = result.getString("matricula_vehiculo")
            )
            cites.add(cite)
        }

        statement?.close()
        databaseManager.closeConnection()

        return cites
    }

    /**
     * Busca una cita por su id y la borra de la base de datos
     * @param el id de la cita que buscaremos
     * @return true si el statement no es nulo, es decir ha encontrado la cita, si no false
     */
    override fun deleteById(id: Long): Boolean {
        logger.debug { "Borrando la cita por id: $id" }

        databaseManager.openConnection()

        // Seleccionamos la base de datos a la que realizar las consultas
        databaseManager.selectDataBase()

        val statement = databaseManager.connection?.createStatement()

        val sql = "DELETE FROM Cita WHERE id = $id"
        statement?.executeUpdate(sql)

        statement?.close()

        databaseManager.closeConnection()

        return statement != null
    }

    /**
     * Actualiza una cita de la base de datos
     * @param la cita que actualizaremos
     * @return la nueva cita actualizada
     */
    override fun update(cite: Cite): Cite {
        logger.debug { "Actualizando cita con id: ${cite.id}" }

        databaseManager.openConnection()

        // Seleccionamos la base de datos a la que realizar las consultas
        databaseManager.selectDataBase()

        val statement = databaseManager.connection?.createStatement()

        val sql =
            """
        UPDATE Cita
        SET estado = ?, fecha = ?, id_informe = ?, usuario_trabajador = ?, matricula_vehiculo = ?
        WHERE id = ?
        """

        val preparedStatement = databaseManager.connection?.prepareStatement(sql)
        preparedStatement?.setBoolean(1, cite.estado)
        preparedStatement?.setString(2, cite.fecha.toString())
        preparedStatement?.setLong(3, cite.id_informe)
        preparedStatement?.setString(4, cite.usuario_trabajador)
        preparedStatement?.setString(5, cite.matricula_vehiculo)
        preparedStatement?.setLong(6, cite.id)

        preparedStatement?.executeUpdate()

        preparedStatement?.close()
        statement?.close()
        databaseManager.closeConnection()

        return cite
    }

    /**
     * Guarda una cita en la base de datos
     * @param la cita que guardaremos
     * @return la nueva cita guardada
     */
    override fun save(cite: Cite): Cite {
        logger.debug { "Creando cita con id: ${cite.id}" }

        databaseManager.openConnection()

        // Seleccionamos la base de datos a la que realizar las consultas
        databaseManager.selectDataBase()

        val statement = databaseManager.connection?.createStatement()

        val sql =
            """
            INSERT INTO Cita (estado, fecha, id_informe, usuario_trabajador, matricula_vehiculo)
            VALUES (?, ?, ?, ?, ?)
            """

        val preparedStatement = databaseManager.connection?.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        preparedStatement?.setBoolean(1, cite.estado)
        preparedStatement?.setString(2, cite.fecha.toString())
        preparedStatement?.setLong(3, cite.id_informe)
        preparedStatement?.setString(4, cite.usuario_trabajador)
        preparedStatement?.setString(5, cite.matricula_vehiculo)

        preparedStatement?.executeUpdate()

        // Obtenemos el ID auto-numérico para la nueva cita
        var idCite = 0L
        val generatedKeys = statement?.generatedKeys
        if (generatedKeys?.next() == true) {
            val generatedId = generatedKeys.getLong(1)
            idCite = generatedId
        }

        preparedStatement?.close()
        statement?.close()
        databaseManager.closeConnection()

        return cite.copy(id = idCite)
    }

    /**
     * Buscar una cita por su ID
     * @param el id por el que se buscará
     * @return la cita encontrada o null si no la encuentra
     */
    override fun findById(id: Long): Cite? {
        logger.debug { "Buscando cita con id: $id" }

        var cite: Cite? = null

        databaseManager.openConnection()

        // Seleccionamos la base de datos a la que realizar las consultas
        databaseManager.selectDataBase()

        val statement = databaseManager.connection?.createStatement()

        val sql = "SELECT * FROM Cita WHERE id = $id"
        val result = statement?.executeQuery(sql)

        // Verificamos si se encontró una cita con el ID proporcionado
        if (result?.next() == true) {
            cite = Cite(
                id = result.getLong("id"),
                estado = result.getBoolean("estado"),
                fecha = result.getDate("fecha").toLocalDate(),
                id_informe = result.getLong("id_informe"),
                usuario_trabajador = result.getString("usuario_trabajador"),
                matricula_vehiculo = result.getString("matricula_vehiculo")
            )
        }

        statement?.close()
        databaseManager.closeConnection()

        return cite
    }

    /**
     * Buscar una cita por la matrícula del coche que pasará al cita
     * @param la matrícula por la que se buscará
     * @return la cita encontrada o null si no la encuentra
     */
    override fun findByMatricula(matricula: String): Cite? {

        var cite: Cite? = null

        databaseManager.openConnection()

        // Seleccionamos la base de datos a la que realizar las consultas
        databaseManager.selectDataBase()

        val statement = databaseManager.connection?.createStatement()

        val sql =
            """
            SELECT * FROM Cita WHERE matricula_vehiculo = '$matricula'
            """
        val result = statement?.executeQuery(sql)

        // Recorremos los resultados hasta que nos de: 'false'
        while (result?.next() == true) {
            cite = Cite(
                id = result.getLong("id"),
                estado = result.getBoolean("estado"),
                fecha = result.getDate("fecha").toLocalDate(),
                id_informe = result.getLong("id_informe"),
                usuario_trabajador = result.getString("usuario_trabajador"),
                matricula_vehiculo = result.getString("matricula_vehiculo")
            )
        }

        statement?.close()
        databaseManager.closeConnection()

        return cite
    }
}