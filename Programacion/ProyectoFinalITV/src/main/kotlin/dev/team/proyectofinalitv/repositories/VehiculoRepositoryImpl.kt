package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.models.Vehiculo
import dev.team.proyectofinalitv.repositories.base.SaveUpdateRepository
import dev.team.proyectofinalitv.services.database.DatabaseManager
import mu.KotlinLogging

class VehiculoRepositoryImpl(private val databaseManager: DatabaseManager) : SaveUpdateRepository<Vehiculo> {

    private val logger = KotlinLogging.logger {}

    /**
     * Actualiza un vehículo de la base de datos
     * @param el vehículo que actualizaremos
     * @return el nuevo vehículo actualizado
     */
    override fun update(item: Vehiculo): Vehiculo {
        logger.debug { "Actualizando vehiculo con matricula: ${item.matricula}" }

        databaseManager.openConnection()

        // Seleccionamos la base de datos a la que realizar las consultas
        databaseManager.selectDataBase()

        val sql =
        """
        UPDATE Propietario
        SET marca = ?, modelo = ?, fechaMatriculacion = ?, fechaRevision = ?, tipoMotor = ?, tipoVehiculo = ?, dniPropietario = ?
        WHERE matricula = ?
        """

        val preparedStatement = databaseManager.prepareStatement(sql)
        preparedStatement?.setString(1, item.marca)
        preparedStatement?.setString(2, item.modelo)
        preparedStatement?.setString(3, item.fechaMatriculacion.toString())
        preparedStatement?.setString(4, item.fechaRevision.toString())
        preparedStatement?.setString(5, item.tipoMotor.toString())
        preparedStatement?.setString(6, item.tipoVehiculo.toString())
        preparedStatement?.setString(7, item.dniPropietario)
        preparedStatement?.setString(8, item.matricula)


        preparedStatement?.executeUpdate()

        preparedStatement?.close()
        databaseManager.closeConnection()

        return item
    }

    /**
     * Guarda un vehículo en la base de datos
     * @param el vehículo que guardaremos
     * @return el nuevo vehículo guardado
     */
    override fun save(item: Vehiculo): Vehiculo {
        logger.debug { "Creando vehículo con matrícula: ${item.matricula}" }

        databaseManager.openConnection()

        // Seleccionamos la base de datos a la que realizar las consultas
        databaseManager.selectDataBase()

        val sql =
            """
        INSERT INTO Vehiculo (matricula, marca, modelo, fecha_matriculacion, fecha_revision, tipo_motor, tipo_vehiculo, dni_propietario)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """

        val preparedStatement = databaseManager.prepareStatement(sql)
        preparedStatement?.setString(1, item.matricula)
        preparedStatement?.setString(2, item.marca)
        preparedStatement?.setString(3, item.modelo)
        preparedStatement?.setString(4, item.fechaMatriculacion.toString())
        preparedStatement?.setString(5, item.fechaRevision.toString())
        preparedStatement?.setString(6, item.tipoMotor.toString())
        preparedStatement?.setString(7, item.tipoVehiculo.toString())
        preparedStatement?.setString(8, item.dniPropietario)

        preparedStatement?.executeUpdate()

        preparedStatement?.close()
        databaseManager.closeConnection()

        return item
    }
}
