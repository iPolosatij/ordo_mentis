package ru.animbus.ordomentis.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync")
data class SyncEntity(
    @PrimaryKey
    val name: String = SYNC_NAME,
    val users: String,
    val items: String,
    val units: String,
){
    companion object{
        const val SYNC_NAME = "UserSyncData"
    }
}