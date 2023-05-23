package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Informe
import dev.team.proyectofinalitv.repositories.base.SaveUpdateRepository
import dev.team.proyectofinalitv.services.database.DatabaseManager
import mu.KotlinLogging

class InformeRepositoryImpl(private val databaseManager: DatabaseManager) : SaveUpdateRepository<Informe> {

    private val logger = KotlinLogging.logger {}

    /**
     * Actualiza un informe de la base de datos
     * @param el informe que actualizaremos
     * @return el nuevo informe actualizado
     */
    override fun update(item: Informe): Informe {
        logger.debug { "Actualizando informe con id: ${item.id}" }

        databaseManager.openConnection()

        // Seleccionamos la base de datos a la que realizar las consultas
        databaseManager.selectDataBase()

        val sql =
        """
        UPDATE Informe
        SET frenado = ?, contaminacion = ?, fecha_informe = ?, interior = ?, luces = ?, is_apto = ?
        WHERE id = ?
        """

        val preparedStatement = databaseManager.prepareStatement(sql)
        preparedStatement?.setDouble(1, item.frenado)
        preparedStatement?.setDouble(2, item.contaminacion)
        preparedStatement?.setString(3, item.fechaInforme.toString())
        preparedStatement?.setBoolean(4, item.interior)
        preparedStatement?.setBoolean(5, item.luces)
        preparedStatement?.setBoolean(6, item.isApto)
        preparedStatement?.setLong(7, item.id)

        preparedStatement?.executeUpdate()

        preparedStatement?.close()
        databaseManager.closeConnection()

        return item
    }

    /**
     * Guarda un informe en la base de datos
     * @param el informe que guardaremos
     * @return el nuevo informe guardado
     */
    override fun save(item: Informe): Informe {
        logger.debug { "Creando informe con ID: ${item.id}" }

        databaseManager.openConnection()

        // Seleccionamos la base de datos a la que realizar las consultas
        databaseManager.selectDataBase()

        val sql =
            """
        INSERT INTO Informe (frenado, contaminacion, fecha_informe, interior, luces, is_apto)
        VALUES (?, ?, ?, ?, ?, ?)
        """

        val preparedStatement = databaseManager.prepareStatementReturnGeneratedKey(sql)
        preparedStatement?.setDouble(1, item.frenado)
        preparedStatement?.setDouble(2, item.contaminacion)
        preparedStatement?.setString(3, item.fechaInforme.toString())
        preparedStatement?.setBoolean(4, item.interior)
        preparedStatement?.setBoolean(5, item.luces)
        preparedStatement?.setBoolean(6, item.isApto)

        preparedStatement?.executeUpdate()

        // Obtenemos el ID auto-generado para el nuevo informe
        val statement = databaseManager.createStatement()
        var idInforme = 0L
        val generatedKeys = statement?.generatedKeys
        if (generatedKeys?.next() == true) {
            val generatedId = generatedKeys.getLong(1)
            idInforme = generatedId
        }

        preparedStatement?.close()
        statement?.close()
        databaseManager.closeConnection()

        return item.copy(id = idInforme)
    }

}
