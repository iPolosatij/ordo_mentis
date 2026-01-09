package ru.animbus.ordomentis.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.animbus.ordomentis.data.database.entities.SyncEntity

@Dao
interface SyncDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSync(unit: SyncEntity)

    @Query("SELECT * FROM sync WHERE name = :name")
    suspend fun getSyncById(name: String): SyncEntity?
}