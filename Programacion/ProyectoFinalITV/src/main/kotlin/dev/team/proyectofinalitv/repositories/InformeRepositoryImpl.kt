package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Informe
import dev.team.proyectofinalitv.repositories.base.SaveUpdateRepository
import dev.team.proyectofinalitv.services.database.DatabaseManager
import mu.KotlinLogging
import java.sql.Statement

class InformeRepositoryImpl(private val databaseManager: DatabaseManager) : SaveUpdateRepository<Informe> {

    private val logger = KotlinLogging.logger {}

    private val con get() = databaseManager.con

    /**
     * Actualiza un informe en la base de datos
     * @param item el informe que actualizaremos
     * @return el nuevo informe actualizado
     */
    override fun update(item: Informe): Informe {
        logger.debug { "Actualizando informe con ID: ${item.id}" }

        con.use { con ->
            databaseManager.selectDatabase(con)
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
            databaseManager.selectDatabase(con)
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
