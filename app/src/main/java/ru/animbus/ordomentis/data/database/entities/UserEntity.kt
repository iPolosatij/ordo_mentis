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
    val bornDate: Long,
    val userContacts: String, // JSON список ContactsData
    val contactList: String, // JSON список идентификаторов UserData
    val specialties: String, // JSON список названий специальностей
    val invites: String, // JSON список идентификаторов UnitData
    val accessItem: String // JSON список
)