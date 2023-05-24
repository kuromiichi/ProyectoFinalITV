package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Propietario
import dev.team.proyectofinalitv.repositories.base.SaveUpdateRepository
import dev.team.proyectofinalitv.services.database.DatabaseManager
import mu.KotlinLogging

class PropietarioRepositoryImpl(private val databaseManager: DatabaseManager) : SaveUpdateRepository<Propietario> {

    private val logger = KotlinLogging.logger {}

    private val con get() = databaseManager.con

    /**
     * Actualiza un propietario en la base de datos
     * @param item el propietario que actualizaremos
     * @return el nuevo propietario actualizado
     */
    override fun update(item: Propietario): Propietario {
        logger.debug { "Actualizando propietario con DNI: ${item.dni}" }

        con.use { con ->
            databaseManager.selectDatabase(con)
            val updateQuery = """
                UPDATE Propietario
                SET nombre = ?, apellidos = ?, correo = ?, telefono = ?
                WHERE dni = ?
            """.trimIndent()
            val updateStmt = con.prepareStatement(updateQuery)
            updateStmt.use { stmt ->
                stmt.setString(1, item.nombre)
                stmt.setString(2, item.apellidos)
                stmt.setString(3, item.correo)
                stmt.setString(4, item.telefono)
                stmt.setString(5, item.dni)

                stmt.executeUpdate()
            }
        }

        return item
    }

    /**
     * Guarda un propietario en la base de datos
     * @param item el propietario que guardaremos
     * @return el nuevo propietario guardado
     */
    override fun save(item: Propietario): Propietario {
        logger.debug { "Creando propietario con DNI: ${item.dni}" }

        con.use { con ->
            databaseManager.selectDatabase(con)
            val saveQuery = """
                INSERT INTO Propietario (dni, nombre, apellidos, correo, telefono)
                VALUES (?, ?, ?, ?, ?);
            """.trimIndent()
            val saveStmt = con.prepareStatement(saveQuery)
            saveStmt.use { stmt ->
                stmt.setString(1, item.dni)
                stmt.setString(2, item.nombre)
                stmt.setString(3, item.apellidos)
                stmt.setString(4, item.correo)
                stmt.setString(5, item.telefono)

                stmt.executeUpdate()
            }
        }

        return item
    }
}
