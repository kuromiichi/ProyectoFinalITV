package dev.team.proyectofinalitv.di

import dev.team.proyectofinalitv.config.AppConfig
import dev.team.proyectofinalitv.repositories.*
import dev.team.proyectofinalitv.services.database.DatabaseManager
import dev.team.proyectofinalitv.services.database.DatabaseManagerImpl
import dev.team.proyectofinalitv.viewmodels.CitaViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val ModuleKoin = module {
    single { AppConfig() }

    // AppConfig()
    single<DatabaseManager>(named("DataBaseManager")) { DatabaseManagerImpl(get()) }

    // DataBaseManager()
    single<PropietarioRepository>(named("PropietarioRepository")) { PropietarioRepositoryImpl(get(named("DataBaseManager"))) }
    single<VehiculoRepository>(named("VehiculoRepository")) { VehiculoRepositoryImpl( get(named("DataBaseManager"))) }
    single<InformeRepository>(named("InformeRepository")) { InformeRepositoryImpl( get(named("DataBaseManager"))) }
    single<CitaRepository>(named("CitaRepository")) { CitaRepositoryImpl( get(named("DataBaseManager"))) }

    single {
        CitaViewModel(
            get(named("PropietarioRepository")),
            get(named("VehiculoRepository")),
            get(named("InformeRepository")),
            get(named("CitaRepository"))
        )
    }
}
/*    single { VehiculeSqlDelightClient(get()) } // AppConfig
    single<VehiculesRepository>(named("VehiculesRepository")) { VehiculesRepositoryImpl(get()) } // VehiculeSqlDelightClient
    single<VehiculesStorage>(named("VehiculesStorage")) { VehiculesStorageImpl() }

    single {
        MainViewModel(
            get(named("VehiculesRepository")),
            get(named("VehiculesStorage"))
        )
    } // VehiculesRepository && VehiculesStorage*/
