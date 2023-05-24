package dev.team.proyectofinalitv.di

import dev.team.proyectofinalitv.config.AppConfig
import dev.team.proyectofinalitv.controllers.ProbandoCosillasController
import dev.team.proyectofinalitv.models.Informe
import dev.team.proyectofinalitv.models.Propietario
import dev.team.proyectofinalitv.models.Trabajador
import dev.team.proyectofinalitv.models.Vehiculo
import dev.team.proyectofinalitv.repositories.*
import dev.team.proyectofinalitv.repositories.base.SaveUpdateRepository
import dev.team.proyectofinalitv.services.database.DatabaseManager
import dev.team.proyectofinalitv.services.database.DatabaseManagerImpl
import dev.team.proyectofinalitv.services.storage.CitaStorage
import dev.team.proyectofinalitv.services.storage.CitaStorageImpl
import dev.team.proyectofinalitv.viewmodels.CitaViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val koinModule = module {
    single { AppConfig() }

    single<DatabaseManager>(named("DatabaseManager")) { DatabaseManagerImpl(get()) }

    single<SaveUpdateRepository<Propietario>>(named("PropietarioRepository")) { PropietarioRepositoryImpl(get(named("DatabaseManager"))) }
    single<SaveUpdateRepository<Vehiculo>>(named("VehiculoRepository")) { VehiculoRepositoryImpl(get(named("DatabaseManager"))) }
    single<SaveUpdateRepository<Informe>>(named("InformeRepository")) { InformeRepositoryImpl(get(named("DatabaseManager"))) }
    single<CitaRepository>(named("CitaRepository")) { CitaRepositoryImpl(get(named("DatabaseManager"))) }
    single<CitaStorage>(named("CitaStorage")) { CitaStorageImpl() }

    single {
        CitaViewModel(
            get(named("PropietarioRepository")),
            get(named("VehiculoRepository")),
            get(named("InformeRepository")),
            get(named("CitaRepository")),
            get(named("CitaStorage"))
            )
    }
}
