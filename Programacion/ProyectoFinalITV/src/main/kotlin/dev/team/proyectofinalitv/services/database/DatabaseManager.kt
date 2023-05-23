package dev.team.proyectofinalitv.services.database

import java.sql.Connection

interface DatabaseManager {
    fun createDatabase()
    fun dropDatabase()
    fun selectDatabase(con: Connection)

    val con: Connection
}
