package ru.animbus.ordomentis.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.animbus.ordomentis.data.database.converters.Converters
import ru.animbus.ordomentis.data.database.dao.MainItemDao
import ru.animbus.ordomentis.data.database.dao.SyncDao
import ru.animbus.ordomentis.data.database.dao.UnitDao
import ru.animbus.ordomentis.data.database.dao.UserDao
import ru.animbus.ordomentis.data.database.entities.MainItemEntity
import ru.animbus.ordomentis.data.database.entities.SyncEntity
import ru.animbus.ordomentis.data.database.entities.UnitEntity
import ru.animbus.ordomentis.data.database.entities.UserEntity

@Database(
    entities = [UnitEntity::class, UserEntity::class, MainItemEntity::class, SyncEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun unitDao(): UnitDao
    abstract fun userDao(): UserDao
    abstract fun mainItemDao(): MainItemDao
    abstract fun syncDataDao(): SyncDao
}