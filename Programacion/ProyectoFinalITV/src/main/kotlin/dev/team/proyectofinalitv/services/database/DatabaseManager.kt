package dev.team.proyectofinalitv.services.database

import dev.team.proyectofinalitv.config.AppConfig
import java.sql.Connection

interface DatabaseManager {
    fun createDatabase()
    fun dropDatabase()

    val conProduction: Connection

    val conTest: Connection

    val appConfig: AppConfig

}
