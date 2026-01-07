package ru.animbus.ordomentis.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.animbus.ordomentis.data.database.entities.MainItemEntity

@Dao
interface MainItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: MainItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItems(items: List<MainItemEntity>)

    @Update
    suspend fun updateItem(item: MainItemEntity)

    @Delete
    suspend fun deleteItem(item: MainItemEntity)

    @Query("SELECT * FROM main_items WHERE itemId = :itemId")
    suspend fun getItemById(itemId: String): MainItemEntity?

    @Query("SELECT * FROM main_items")
    fun getAllItems(): Flow<List<MainItemEntity>>

    @Query("SELECT * FROM main_items WHERE ownerId = :ownerId")
    suspend fun getItemsByOwnerId(ownerId: String): List<MainItemEntity>

    @Query("SELECT * FROM main_items WHERE itemId IN (:itemIds)")
    suspend fun getItemsByIds(itemIds: List<String>): List<MainItemEntity>

    @Query("DELETE FROM main_items")
    suspend fun clearAllItems()

    @Query("DELETE FROM main_items WHERE ownerId = :ownerId")
    suspend fun deleteItemsByOwnerId(ownerId: String)
}