package ru.animbus.ordomentis.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "main_items")
data class MainItemEntity(
    @PrimaryKey
    val itemId: String,
    val iconUrl: String?,
    val itemName: String?,
    val itemType: String,
    val description: String?,
    val subItems: String?, // JSON список
    val comments: String?, // JSON список
    val status: String,
    val statusList: String, // JSON список
    val ownerId: String,
    val users: String, // JSON список
    val createDate: String,
    val deadline: String?,
    val timeEstimation: String, // JSON список
    val spentTime: String, // JSON список
    val costEstimation: String?, // JSON
    val realCost: String?, // JSON
    val shared: String // JSON список
)