package ru.animbus.ordomentis.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.animbus.ordomentis.data.models.domain.*

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(list: List<String>): String = gson.toJson(list)

    @TypeConverter
    fun toStringList(json: String?): List<String> =
        json?.let { gson.fromJson(it, object : TypeToken<List<String>>() {}.type) } ?: emptyList()

    @TypeConverter
    fun fromItemStatusList(list: List<ItemStatus>): String =
        gson.toJson(list.map { it.name })

    @TypeConverter
    fun toItemStatusList(json: String?): List<ItemStatus> =
        json?.let {
            val names: List<String> = gson.fromJson(it, object : TypeToken<List<String>>() {}.type)
            names.map { name -> ItemStatus.valueOf(name) }
        } ?: emptyList()

    @TypeConverter
    fun fromUnitType(type: UnitType): String = type.name

    @TypeConverter
    fun toUnitType(name: String): UnitType = UnitType.valueOf(name)

    @TypeConverter
    fun fromItemType(type: ItemType): String = type.name

    @TypeConverter
    fun toItemType(name: String): ItemType = ItemType.valueOf(name)

    @TypeConverter
    fun fromItemStatus(status: ItemStatus): String = status.name

    @TypeConverter
    fun toItemStatus(name: String): ItemStatus = ItemStatus.valueOf(name)

    @TypeConverter
    fun fromUserRole(role: UserRole): String = role.name

    @TypeConverter
    fun toUserRole(name: String): UserRole = UserRole.valueOf(name)

    @TypeConverter
    fun fromMoneyType(moneyType: MoneyType): String = moneyType.name

    @TypeConverter
    fun toMoneyType(name: String): MoneyType = MoneyType.valueOf(name)
}