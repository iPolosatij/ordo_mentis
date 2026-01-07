package ru.animbus.ordomentis.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.animbus.ordomentis.data.database.entities.UnitEntity

@Dao
interface UnitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: UnitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUnits(units: List<UnitEntity>)

    @Update
    suspend fun updateUnit(unit: UnitEntity)

    @Delete
    suspend fun deleteUnit(unit: UnitEntity)

    @Query("SELECT * FROM units WHERE unitId = :unitId")
    suspend fun getUnitById(unitId: String): UnitEntity?

    @Query("SELECT * FROM units WHERE unitId IN (:unitIds)")
    suspend fun getUnitsByIds(unitIds: List<String>): List<UnitEntity>

    @Query("SELECT * FROM units")
    fun getAllUnits(): Flow<List<UnitEntity>>

    @Query("SELECT * FROM units WHERE owners LIKE '%' || :ownerId || '%'")
    suspend fun getUnitsForOwner(ownerId: String): List<UnitEntity>

    @Query("SELECT * FROM units WHERE internal LIKE '%' || :userId || '%' OR external LIKE '%' || :userId || '%'")
    suspend fun getUnitsForUser(userId: String): List<UnitEntity>

    @Query("DELETE FROM units")
    suspend fun clearAllUnits()
}