package dev.team.proyectofinalitv.repositories

import dev.team.proyectofinalitv.mappers.parseEspecialidad
import dev.team.proyectofinalitv.mappers.parseTipoMotor
import dev.team.proyectofinalitv.mappers.parseTipoVehiculo
import dev.team.proyectofinalitv.models.Trabajador
import dev.team.proyectofinalitv.models.Vehiculo
import dev.team.proyectofinalitv.repositories.base.CRURepository
import dev.team.proyectofinalitv.services.database.DatabaseManager
import mu.KotlinLogging
import java.sql.Connection
import java.time.LocalDate

class VehiculoRepositoryImpl(private val databaseManager: DatabaseManager) : CRURepository<Vehiculo> {

    private val logger = KotlinLogging.logger {}

    val con: Connection
        get() {
        return when (databaseManager.appConfig.testDb){
            false -> databaseManager.conProduction
            true -> databaseManager.conTest
        }
    }

    /**
     * Busca todos los trabajadores que se encuentren en la base de datos
     * @return la lista de todas los trabajadores
     */
    override fun getAll(): List<Vehiculo> {
        logger.debug { "Buscando todos los vehículos" }

        val vehiculos = mutableListOf<Vehiculo>()

        con.use { con ->
            val findAllQuery = "SELECT * FROM Vehiculo"
            val findAllStmt = con.prepareStatement(findAllQuery)
            findAllStmt.use { stmt ->
                val findAllResultSet = stmt.executeQuery()
                findAllResultSet.use { rs ->
                    while (rs.next()) {
                        vehiculos.add(
                            Vehiculo(
                                matricula = rs.getString(1),
                                marca = rs.getString(2),
                                modelo = rs.getString(3),
                                fechaMatriculacion = LocalDate.parse(rs.getString(4)),
                                fechaRevision = LocalDate.parse(rs.getString(5)),
                                tipoMotor = parseTipoMotor(rs.getString(6)),
                                tipoVehiculo = parseTipoVehiculo(rs.getString(7)),
                                dniPropietario = rs.getString(8)
                            )
                        )
                    }
                }
            }
        }

        return vehiculos
    }

    /**
     * Actualiza un vehículo en la base de datos
     * @param item el vehículo que actualizaremos
     * @return el nuevo vehículo actualizado
     */
    override fun update(item: Vehiculo): Vehiculo {
        logger.debug { "Actualizando vehículo con matrícula: ${item.matricula}" }

        con.use { con ->
            val updateQuery = """
                UPDATE Vehiculo
                SET marca = ?, modelo = ?, fecha_matriculacion = ?, fecha_revision = ?, tipo_motor = ?,
                tipo_vehiculo = ?, dni_propietario = ?
                WHERE matricula = ?
            """.trimIndent()
            val updateStmt = con.prepareStatement(updateQuery)
            updateStmt.use { stmt ->
                stmt.setString(1, item.marca)
                stmt.setString(2, item.modelo)
                stmt.setString(3, item.fechaMatriculacion.toString())
                stmt.setString(4, item.fechaRevision.toString())
                stmt.setString(5, item.tipoMotor.name)
                stmt.setString(6, item.tipoVehiculo.name)
                stmt.setString(7, item.dniPropietario)
                stmt.setString(8, item.matricula)

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
    override fun save(item: Vehiculo): Vehiculo {
        logger.debug { "Creando vehículo con matrícula: ${item.matricula}" }

        con.use { con ->
            val saveQuery = """
                INSERT INTO Vehiculo (matricula, marca, modelo, fecha_matriculacion, fecha_revision, tipo_motor,
                tipo_vehiculo, dni_propietario)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """.trimIndent()
            val saveStmt = con.prepareStatement(saveQuery)
            saveStmt.use { stmt ->
                stmt.setString(1, item.matricula)
                stmt.setString(2, item.marca)
                stmt.setString(3, item.modelo)
                stmt.setString(4, item.fechaMatriculacion.toString())
                stmt.setString(5, item.fechaRevision.toString())
                stmt.setString(6, item.tipoMotor.name)
                stmt.setString(7, item.tipoVehiculo.name)
                stmt.setString(8, item.dniPropietario)

                stmt.executeUpdate()
            }
        }

        return item
    }
}
