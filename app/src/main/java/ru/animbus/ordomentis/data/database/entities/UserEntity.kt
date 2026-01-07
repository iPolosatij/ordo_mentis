package ru.animbus.ordomentis.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val userId: String,
    val units: String?, // JSON список
    val name: String?,
    val lastName: String?,
    val patronymic: String?,
    val nikName: String,
    val accessItem: String // JSON список
)