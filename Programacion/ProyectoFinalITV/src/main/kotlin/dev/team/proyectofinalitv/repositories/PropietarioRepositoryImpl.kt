package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Propietario
import dev.team.proyectofinalitv.services.database.DataBaseManager
import mu.KotlinLogging

class PropietarioRepositoryImpl(private val databaseManager: DataBaseManager) : PropietarioRepository {

    private val logger = KotlinLogging.logger {}

    /**
     * Actualiza un propietario de la base de datos
     * @param el propietario que actualizaremos
     * @return el nuevo propietario actualizado
     */
    override fun update(propietario: Propietario): Propietario {
        logger.debug { "Actualizando propietario con DNI: ${propietario.dni}" }

        databaseManager.openConnection()

        // Seleccionamos la base de datos a la que realizar las consultas
        databaseManager.selectDataBase()

        val statement = databaseManager.connection?.createStatement()

        val sql =
        """
        UPDATE Propietario
        SET nombre = ?, apellidos = ?, correo = ?, telefono = ?
        WHERE dni = ?
        """

        val preparedStatement = databaseManager.connection?.prepareStatement(sql)
        preparedStatement?.setString(1, propietario.nombre)
        preparedStatement?.setString(2, propietario.apellidos)
        preparedStatement?.setString(3, propietario.correo)
        preparedStatement?.setString(4, propietario.telefono)
        preparedStatement?.setString(5, propietario.dni)

        preparedStatement?.executeUpdate()

        preparedStatement?.close()
        statement?.close()
        databaseManager.closeConnection()

        return propietario
    }

    /**
     * Guarda un propietario en la base de datos
     * @param el propietario que guardaremos
     * @return el nuevo propietario guardado
     */
    override fun save(propietario: Propietario): Propietario {
        logger.debug { "Creando propietario con DNI: ${propietario.dni}" }

        databaseManager.openConnection()

        // Seleccionamos la base de datos a la que realizar las consultas
        databaseManager.selectDataBase()

        val statement = databaseManager.connection?.createStatement()

        val sql =
            """
        INSERT INTO Propietario (dni, nombre, apellidos, correo, telefono)
        VALUES (?, ?, ?, ?, ?)
        """

        val preparedStatement = databaseManager.connection?.prepareStatement(sql)
        preparedStatement?.setString(1, propietario.dni)
        preparedStatement?.setString(2, propietario.nombre)
        preparedStatement?.setString(3, propietario.apellidos)
        preparedStatement?.setString(4, propietario.correo)
        preparedStatement?.setString(5, propietario.telefono)

        preparedStatement?.executeUpdate()

        preparedStatement?.close()
        statement?.close()
        databaseManager.closeConnection()

        return propietario
    }

}