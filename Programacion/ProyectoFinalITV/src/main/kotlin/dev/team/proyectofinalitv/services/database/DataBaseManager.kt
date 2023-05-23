package dev.team.proyectofinalitv.services.database

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.Statement

interface DataBaseManager {
    fun createDatabase()
    fun dropDatabase()
    fun Connection.selectDatabase()
}
