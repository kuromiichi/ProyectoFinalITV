package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Cita
import dev.team.proyectofinalitv.services.database.DatabaseManager
import mu.KotlinLogging
import java.sql.Connection
import java.sql.Statement
import java.time.LocalDateTime

class CitaRepositoryImpl(private val databaseManager: DatabaseManager) : CitaRepository {

    private val logger = KotlinLogging.logger {}

    val con: Connection
        get() {
        return when (databaseManager.appConfig.testDb){
            false -> databaseManager.conProduction
            true -> databaseManager.conTest
        }
    }

    /**
     * Busca todas las citas que se encuentren en la base de datos
     * @return la lista de todas las citas
     */
    override fun findAll(): List<Cita> {
        logger.debug { "Buscando todas las citas" }

        val citas = mutableListOf<Cita>()

        con.use { con ->
            val findAllQuery = "SELECT * FROM Cita"
            val findAllStmt = con.prepareStatement(findAllQuery)
            findAllStmt.use { stmt ->
                val findAllResultSet = stmt.executeQuery()
                findAllResultSet.use { rs ->
                    while (rs.next()) {
                        citas.add(
                            Cita(
                                id = rs.getLong(1),
                                estado = rs.getString(2),
                                fechaHora = LocalDateTime.parse(rs.getString(3)),
                                idInforme = rs.getLong(4),
                                matriculaVehiculo = rs.getString(5),
                                usuarioTrabajador = rs.getString(6)
                            )
                        )
                    }
                }
            }
        }

        return citas
    }

    /**
     * Busca una cita por su id y la borra de la base de datos
     * @param id el id de la cita que buscaremos
     * @return true si se ha encontrado la cita, false si no
     */
    override fun deleteById(id: Long): Boolean {
        logger.debug { "Borrando la cita con ID: $id" }

        con.use { con ->
            val deleteQuery = "DELETE FROM Cita WHERE id = ?"
            val deleteStmt = con.prepareStatement(deleteQuery)
            deleteStmt.use { stmt ->
                stmt.setLong(1, id)
                return stmt.executeUpdate() > 0
            }
        }
    }

    /**
     * Actualiza una cita en la base de datos
     * @param item la cita que actualizaremos
     * @return la nueva cita actualizada
     */
    override fun update(item: Cita): Cita {
        logger.debug { "Actualizando cita con ID: ${item.id}" }

        con.use { con ->
            val updateQuery = """
                UPDATE Cita
                SET estado = ?, fecha_hora = ?, id_informe = ?, matricula_vehiculo = ?, usuario_trabajador = ?
                WHERE id = ?
            """.trimIndent()
            val updateStmt = con.prepareStatement(updateQuery)
            updateStmt.use { stmt ->
                stmt.setString(1, item.estado)
                stmt.setString(2, item.fechaHora.toString())
                stmt.setLong(3, item.idInforme)
                stmt.setString(4, item.matriculaVehiculo)
                stmt.setString(5, item.usuarioTrabajador)
                stmt.setLong(6, item.id)

                stmt.executeUpdate()
            }
        }

        return item
    }

    /**
     * Guarda una cita en la base de datos
     * @param item la cita que guardaremos
     * @return la nueva cita guardada
     */
    override fun save(item: Cita): Cita {
        logger.debug { "Creando cita con ID: ${item.id}" }

        var id: Long

        con.use { con ->
            val saveQuery = """
                INSERT INTO Cita
                (estado, fecha_hora, id_informe, usuario_trabajador, matricula_vehiculo)
                VALUES (?, ?, ?, ?, ?)
            """.trimIndent()
            val saveStmt = con.prepareStatement(saveQuery, Statement.RETURN_GENERATED_KEYS)
            saveStmt.use { stmt ->
                stmt.setString(1, item.estado)
                stmt.setString(2, item.fechaHora.toString())
                stmt.setLong(3, item.idInforme)
                stmt.setString(4, item.usuarioTrabajador)
                stmt.setString(5, item.matriculaVehiculo)

                stmt.executeUpdate()
                val key = stmt.generatedKeys
                id = key.use { if (it.next()) it.getLong(1) else 0L }
            }
        }

        return item.copy(id = id)
    }

    /**
     * Buscar una cita por su ID
     * @param id el id por el que se buscará
     * @return la cita si se ha encontrado, null si no
     */
    override fun findById(id: Long): Cita? {
        logger.debug { "Buscando cita con ID: $id" }

        var cita: Cita? = null

        con.use { con ->
            val findQuery = "SELECT * FROM Cita WHERE id = ?"
            val findStmt = con.prepareStatement(findQuery)
            findStmt.use { stmt ->
                stmt.setLong(1, id)
                val findResultSet = stmt.executeQuery()
                findResultSet.use { rs ->
                    if (rs.next()) {
                        cita = Cita(
                            id = rs.getLong(1),
                            estado = rs.getString(2),
                            fechaHora = LocalDateTime.parse(rs.getString(3)),
                            idInforme = rs.getLong(4),
                            usuarioTrabajador = rs.getString(5),
                            matriculaVehiculo = rs.getString(6)
                        )
                    }
                }
            }
        }

        return cita
    }

    /**
     * Buscar una cita por matrícula
     * @param matricula la matrícula por la que se buscará
     * @return la cita si se ha encontrado, null si no
     */
    override fun findByMatricula(matricula: String): Cita? {
        logger.debug { "Buscando cita con matrícula: $matricula" }

        var cita: Cita? = null

        con.use { con ->
            val findQuery = "SELECT * FROM Cita WHERE matricula_vehiculo = ?"
            val findStmt = con.prepareStatement(findQuery)
            findStmt.use { stmt ->
                stmt.setString(1, matricula)
                val findResultSet = stmt.executeQuery()
                findResultSet.use { rs ->
                    if (rs.next()) {
                        cita = Cita(
                            id = rs.getLong(1),
                            estado = rs.getString(2),
                            fechaHora = LocalDateTime.parse(rs.getString(3)),
                            idInforme = rs.getLong(4),
                            usuarioTrabajador = rs.getString(5),
                            matriculaVehiculo = rs.getString(6)
                        )
                    }
                }
            }
        }

        return cita
    }
}
