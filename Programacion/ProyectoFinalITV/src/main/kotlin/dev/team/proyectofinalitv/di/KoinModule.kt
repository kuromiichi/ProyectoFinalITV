package dev.team.proyectofinalitv.di

import dev.team.proyectofinalitv.config.AppConfig
import dev.team.proyectofinalitv.models.Informe
import dev.team.proyectofinalitv.models.Propietario
import dev.team.proyectofinalitv.models.Trabajador
import dev.team.proyectofinalitv.models.Vehiculo
import dev.team.proyectofinalitv.repositories.*
import dev.team.proyectofinalitv.repositories.base.CRURepository
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

    single<CRURepository<Propietario, String>>(named("PropietarioRepository")) { PropietarioRepositoryImpl(get(named("DatabaseManager"))) }
    single<CRURepository<Vehiculo, String>>(named("VehiculoRepository")) { VehiculoRepositoryImpl(get(named("DatabaseManager"))) }
    single<CRURepository<Informe, Long>>(named("InformeRepository")) { InformeRepositoryImpl(get(named("DatabaseManager"))) }
    single<CRURepository<Trabajador, String>>(named("TrabajadorRepository")) { TrabajadorRepositoryImpl(get(named("DatabaseManager"))) }
    single<CitaRepository>(named("CitaRepository")) { CitaRepositoryImpl(get(named("DatabaseManager"))) }
    single<CitaStorage>(named("CitaStorage")) { CitaStorageImpl() }

    single {
        CitaViewModel(
            get(named("PropietarioRepository")),
            get(named("VehiculoRepository")),
            get(named("InformeRepository")),
            get(named("TrabajadorRepository")),
            get(named("CitaRepository")),
            get(named("CitaStorage"))
        )
    }
}
