package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Cita
import dev.team.proyectofinalitv.models.Propietario
import dev.team.proyectofinalitv.repositories.base.CRURepository
import dev.team.proyectofinalitv.services.database.DatabaseManager
import mu.KotlinLogging
import java.sql.Connection
import java.time.LocalDateTime

class PropietarioRepositoryImpl(private val databaseManager: DatabaseManager) : CRURepository<Propietario> {

    private val logger = KotlinLogging.logger {}

    val con: Connection get() {
        return when (databaseManager.appConfig.testDb){
            false -> databaseManager.conProduction
            true -> databaseManager.conTest
        }
    }

    /**
     * Busca todos los propietarios que se encuentren en la base de datos
     * @return la lista de todas los propietarios
     */
    override fun getAll(): List<Propietario> {
        logger.debug { "Buscando todos los propietarios" }

        val propietarios = mutableListOf<Propietario>()

        con.use { con ->
            val findAllQuery = "SELECT * FROM Propietario"
            val findAllStmt = con.prepareStatement(findAllQuery)
            findAllStmt.use { stmt ->
                val findAllResultSet = stmt.executeQuery()
                findAllResultSet.use { rs ->
                    while (rs.next()) {
                        propietarios.add(
                            Propietario(
                                dni = rs.getString(1),
                                nombre = rs.getString(2),
                                apellidos = rs.getString(3),
                                correo = rs.getString(4),
                                telefono = rs.getString(5)
                            )
                        )
                    }
                }
            }
        }

        return propietarios
    }

    /**
     * Actualiza un propietario en la base de datos
     * @param item el propietario que actualizaremos
     * @return el nuevo propietario actualizado
     */
    override fun update(item: Propietario): Propietario {
        logger.debug { "Actualizando propietario con DNI: ${item.dni}" }

        con.use { con ->
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
