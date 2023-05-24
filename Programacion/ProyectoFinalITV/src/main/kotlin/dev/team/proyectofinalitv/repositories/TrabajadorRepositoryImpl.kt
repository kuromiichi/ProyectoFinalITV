package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Trabajador
import dev.team.proyectofinalitv.repositories.base.SaveUpdateRepository
import dev.team.proyectofinalitv.services.database.DatabaseManager
import mu.KotlinLogging

class TrabajadorRepositoryImpl(private val databaseManager: DatabaseManager) : SaveUpdateRepository<Trabajador> {

    private val logger = KotlinLogging.logger {}

    private val con get() = databaseManager.con

    /**
     * Actualiza un vehículo en la base de datos
     * @param item el vehículo que actualizaremos
     * @return el nuevo vehículo actualizado
     */
    override fun update(item: Trabajador): Trabajador {
        logger.debug { "Actualizando trabajador con usuario: ${item.usuario}" }

        con.use { con ->
            databaseManager.selectDatabase(con)
            val updateQuery = """
                UPDATE Trabajador
                SET contrasenya = ?, nombre = ?, correo = ?, telefono = ?, salario = ?, fecha_contratacion = ?,
                especialidad = ?, is_responsable = ?, id_estacion = ?
                WHERE usuario = ?
            """.trimIndent()
            val updateStmt = con.prepareStatement(updateQuery)
            updateStmt.use { stmt ->
                stmt.setString(1, item.contrasenya)
                stmt.setString(2, item.nombre)
                stmt.setString(3, item.correo)
                stmt.setString(4, item.telefono)
                stmt.setDouble(5, item.salario)
                stmt.setString(6, item.fechaContratacion.toString())
                stmt.setString(7, item.especialidad.name)
                stmt.setBoolean(8, item.isResponsable)
                stmt.setLong(9, item.idEstacion)
                stmt.setString(10, item.usuario)

                stmt.executeUpdate()
            }
        }

        return item
    }

    /**
     * Guarda un vehículo en la base de datos
     * @param item el vehículo que guardaremos
     * @return el nuevo vehículo guardado
     */
    override fun save(item: Trabajador): Trabajador {
        logger.debug { "Creando trabajador con usuario: ${item.usuario}" }
        con.use { con ->
            databaseManager.selectDatabase(con)
            val saveQuery = """
                INSERT INTO Trabajador (usuario, contrasenya, nombre, correo, telefono, salario, fecha_contratacion,
                especialidad, is_responsable, id_estacion)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """.trimIndent()
            val saveStmt = con.prepareStatement(saveQuery)
            saveStmt.use { stmt ->
                stmt.setString(1, item.usuario)
                stmt.setString(2, item.contrasenya)
                stmt.setString(3, item.nombre)
                stmt.setString(4, item.correo)
                stmt.setString(5, item.telefono)
                stmt.setDouble(6, item.salario)
                stmt.setString(7, item.fechaContratacion.toString())
                stmt.setString(8, item.especialidad.name)
                stmt.setBoolean(9, item.isResponsable)
                stmt.setLong(10, item.idEstacion)

                stmt.executeUpdate()
            }
        }

        return item
    }
}
