package dev.team.proyectofinalitv.services.database

import java.sql.PreparedStatement
import java.sql.Statement

interface DataBaseManager {
    fun openConnection()
    fun closeConnection()
    fun selectDataBase()
    fun createStatement(): Statement?
    fun prepareStatement(sql:String): PreparedStatement?
    fun prepareStatementReturnGeneratedKey(sql: String): PreparedStatement?
}