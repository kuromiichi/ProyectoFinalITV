package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Propietario
import dev.team.proyectofinalitv.repositories.base.SaveUpdateRepository
import dev.team.proyectofinalitv.services.database.DatabaseManager
import mu.KotlinLogging

class PropietarioRepositoryImpl(private val databaseManager: DatabaseManager) : SaveUpdateRepository<Propietario> {

    private val logger = KotlinLogging.logger {}

    /**
     * Actualiza un propietario de la base de datos
     * @param el propietario que actualizaremos
     * @return el nuevo propietario actualizado
     */
    override fun update(item: Propietario): Propietario {
        logger.debug { "Actualizando propietario con DNI: ${item.dni}" }

        databaseManager.openConnection()

        // Seleccionamos la base de datos a la que realizar las consultas
        databaseManager.selectDataBase()

        val sql =
        """
        UPDATE Propietario
        SET nombre = ?, apellidos = ?, correo = ?, telefono = ?
        WHERE dni = ?
        """

        val preparedStatement = databaseManager.prepareStatement(sql)
        preparedStatement?.setString(1, item.nombre)
        preparedStatement?.setString(2, item.apellidos)
        preparedStatement?.setString(3, item.correo)
        preparedStatement?.setString(4, item.telefono)
        preparedStatement?.setString(5, item.dni)

        preparedStatement?.executeUpdate()

        preparedStatement?.close()
        databaseManager.closeConnection()

        return item
    }

    /**
     * Guarda un propietario en la base de datos
     * @param el propietario que guardaremos
     * @return el nuevo propietario guardado
     */
    override fun save(item: Propietario): Propietario {
        logger.debug { "Creando propietario con DNI: ${item.dni}" }

        databaseManager.openConnection()

        // Seleccionamos la base de datos a la que realizar las consultas
        databaseManager.selectDataBase()

        val sql =
            """
        INSERT INTO Propietario (dni, nombre, apellidos, correo, telefono)
        VALUES (?, ?, ?, ?, ?)
        """

        val preparedStatement = databaseManager.prepareStatement(sql)
        preparedStatement?.setString(1, item.dni)
        preparedStatement?.setString(2, item.nombre)
        preparedStatement?.setString(3, item.apellidos)
        preparedStatement?.setString(4, item.correo)
        preparedStatement?.setString(5, item.telefono)

        preparedStatement?.executeUpdate()

        preparedStatement?.close()
        databaseManager.closeConnection()

        return item
    }

}
