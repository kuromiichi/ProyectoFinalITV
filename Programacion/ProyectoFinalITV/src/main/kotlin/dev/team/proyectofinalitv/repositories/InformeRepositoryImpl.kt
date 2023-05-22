package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Informe
import dev.team.proyectofinalitv.services.database.DataBaseManager
import mu.KotlinLogging

class InformeRepositoryImpl(private val databaseManager: DataBaseManager) : InformeRepository {

    private val logger = KotlinLogging.logger {}

    /**
     * Actualiza un informe de la base de datos
     * @param el informe que actualizaremos
     * @return el nuevo informe actualizado
     */
    override fun update(informe: Informe): Informe {
        logger.debug { "Actualizando informe con id: ${informe.id}" }

        databaseManager.openConnection()

        // Seleccionamos la base de datos a la que realizar las consultas
        databaseManager.selectDataBase()

        val statement = databaseManager.createStatement()

        val sql =
        """
        UPDATE Informe
        SET frenado = ?, contaminacion = ?, fecha_informe = ?, interior = ?, luces = ?, is_apto = ?
        WHERE id = ?
        """

        val preparedStatement = databaseManager.prepareStatement(sql)
        preparedStatement?.setDouble(1, informe.frenado)
        preparedStatement?.setDouble(2, informe.contaminacion)
        preparedStatement?.setString(3, informe.fechaInforme.toString())
        preparedStatement?.setBoolean(4, informe.interior)
        preparedStatement?.setBoolean(5, informe.luces)
        preparedStatement?.setBoolean(6, informe.isApto)
        preparedStatement?.setLong(7, informe.id)

        preparedStatement?.executeUpdate()

        preparedStatement?.close()
        statement?.close()
        databaseManager.closeConnection()

        return informe
    }

    /**
     * Guarda un informe en la base de datos
     * @param el informe que guardaremos
     * @return el nuevo informe guardado
     */
    override fun save(informe: Informe): Informe {
        logger.debug { "Creando informe con ID: ${informe.id}" }

        databaseManager.openConnection()

        // Seleccionamos la base de datos a la que realizar las consultas
        databaseManager.selectDataBase()

        val statement = databaseManager.createStatement()

        val sql =
            """
        INSERT INTO Informe (frenado, contaminacion, fecha_informe, interior, luces, is_apto)
        VALUES (?, ?, ?, ?, ?, ?)
        """

        val preparedStatement = databaseManager.prepareStatementReturnGeneratedKey(sql)
        preparedStatement?.setDouble(1, informe.frenado)
        preparedStatement?.setDouble(2, informe.contaminacion)
        preparedStatement?.setString(3, informe.fechaInforme.toString())
        preparedStatement?.setBoolean(4, informe.interior)
        preparedStatement?.setBoolean(5, informe.luces)
        preparedStatement?.setBoolean(6, informe.isApto)

        preparedStatement?.executeUpdate()

        // Obtenemos el ID auto-generado para el nuevo informe
        var idInforme = 0L
        val generatedKeys = statement?.generatedKeys
        if (generatedKeys?.next() == true) {
            val generatedId = generatedKeys.getLong(1)
            idInforme = generatedId
        }

        preparedStatement?.close()
        statement?.close()
        databaseManager.closeConnection()

        return informe.copy(id = idInforme)
    }

}