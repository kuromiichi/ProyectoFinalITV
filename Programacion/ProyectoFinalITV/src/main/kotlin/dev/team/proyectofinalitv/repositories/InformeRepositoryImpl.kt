package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Informe
import dev.team.proyectofinalitv.services.database.DataBaseManager
import mu.KotlinLogging
import java.sql.Statement

class InformeRepositoryImpl(private val databaseManager: DataBaseManager) : InformeRepository {

    private val logger = KotlinLogging.logger {}


    override fun update(informe: Informe): Informe {
        TODO("Not yet implemented")
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

        val statement = databaseManager.connection?.createStatement()

        val sql =
            """
        INSERT INTO Informe (frenado, contaminacion, fecha_informe, interior, luces, is_apto)
        VALUES (?, ?, ?, ?, ?, ?)
        """

        val preparedStatement = databaseManager.connection?.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
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