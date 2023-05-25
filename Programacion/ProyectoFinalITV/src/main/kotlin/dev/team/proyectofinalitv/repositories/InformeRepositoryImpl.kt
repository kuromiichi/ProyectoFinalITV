package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Informe
import dev.team.proyectofinalitv.repositories.base.CRURepository
import dev.team.proyectofinalitv.services.database.DatabaseManager
import mu.KotlinLogging
import java.sql.Connection
import java.sql.Statement
import java.time.LocalDate

class InformeRepositoryImpl(private val databaseManager: DatabaseManager) : CRURepository<Informe> {

    private val logger = KotlinLogging.logger {}

    val con: Connection
        get() = if (databaseManager.appConfig.testDb) databaseManager.conTest else databaseManager.conProduction

    /**
     * Busca todos los informes que se encuentren en la base de datos
     * @return la lista de todas los informes
     */
    override fun findAll(): List<Informe> {
        logger.debug { "Buscando todos los informes" }

        val informes = mutableListOf<Informe>()

        con.use { con ->
            val findAllQuery = "SELECT * FROM Informe"
            val findAllStmt = con.prepareStatement(findAllQuery)
            findAllStmt.use { stmt ->
                val findAllResultSet = stmt.executeQuery()
                findAllResultSet.use { rs ->
                    while (rs.next()) {
                        informes.add(
                            Informe(
                                id = rs.getLong(1),
                                frenado = rs.getDouble(2),
                                contaminacion = rs.getDouble(3),
                                fechaInforme = LocalDate.parse(rs.getString(4)),
                                interior = rs.getBoolean(5),
                                luces =  rs.getBoolean(6),
                                isApto =  rs.getBoolean(7)
                            )
                        )
                    }
                }
            }
        }

        return informes
    }

    /**
     * Actualiza un informe en la base de datos
     * @param item el informe que actualizaremos
     * @return el nuevo informe actualizado
     */
    override fun update(item: Informe): Informe {
        logger.debug { "Actualizando informe con ID: ${item.id}" }

        con.use { con ->
            val updateQuery = """
                UPDATE Informe
                SET frenado = ?, contaminacion = ?, fecha_informe = ?, interior = ?, luces = ?, is_apto = ?
                WHERE id = ?
            """.trimIndent()
            val updateStmt = con.prepareStatement(updateQuery)
            updateStmt.use { stmt ->
                stmt.setDouble(1, item.frenado)
                stmt.setDouble(2, item.contaminacion)
                stmt.setString(3, item.fechaInforme.toString())
                stmt.setBoolean(4, item.interior)
                stmt.setBoolean(5, item.luces)
                stmt.setBoolean(6, item.isApto)
                stmt.setLong(7, item.id)

                stmt.executeUpdate()
            }
        }

        return item
    }

    /**
     * Guarda un informe en la base de datos
     * @param item el informe que guardaremos
     * @return el nuevo informe guardado
     */
    override fun save(item: Informe): Informe {
        logger.debug { "Creando informe con ID: ${item.id}" }

        var id: Long

        con.use { con ->
            val saveQuery = """
                INSERT INTO Informe (frenado, contaminacion, fecha_informe, interior, luces, is_apto)
                VALUES (?, ?, ?, ?, ?, ?)
            """.trimIndent()
            val saveStmt = con.prepareStatement(saveQuery, Statement.RETURN_GENERATED_KEYS)
            saveStmt.use { stmt ->
                stmt.setDouble(1, item.frenado)
                stmt.setDouble(2, item.contaminacion)
                stmt.setString(3, item.fechaInforme.toString())
                stmt.setBoolean(4, item.interior)
                stmt.setBoolean(5, item.luces)
                stmt.setBoolean(6, item.isApto)

                stmt.executeUpdate()
                val key = stmt.generatedKeys
                id = key.use { if (it.next()) it.getLong(1) else 0L }
            }
        }

        return item.copy(id = id)
    }
}
