package ru.animbus.ordomentis.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.animbus.ordomentis.data.models.domain.UnitType

@Entity(tableName = "units")
data class UnitEntity(
    @PrimaryKey
    val unitId: String,
    val owners: String, // JSON список
    val internal: String, // JSON список
    val external: String, // JSON список
    val publicItems: String, // JSON список
    val privateItems: String, // JSON список
    val internalUnits: String, // JSON список
    val externalUnits: String, // JSON список
    val type: String
)