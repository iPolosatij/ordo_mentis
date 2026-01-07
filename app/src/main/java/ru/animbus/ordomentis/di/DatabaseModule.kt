package ru.animbus.ordomentis.di

import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.animbus.ordomentis.data.database.AppDatabase
import ru.animbus.ordomentis.data.database.mappers.MainItemMapper
import ru.animbus.ordomentis.data.database.mappers.UnitMapper
import ru.animbus.ordomentis.data.database.mappers.UserMapper
import ru.animbus.ordomentis.data.repositories.*

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "ordomentis-db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    // DAOs
    single { get<AppDatabase>().unitDao() }
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().mainItemDao() }

    // Mappers
    single { UnitMapper() }
    single { UserMapper() }
    single { MainItemMapper() }

    // Repositories
    single { UnitRepository(get(), get()) }
    single { UserRepository(get(), get()) }
    single { MainItemRepository(get(), get()) }

    // Main repository
    single { AppRepository(get(), get(), get()) }
}