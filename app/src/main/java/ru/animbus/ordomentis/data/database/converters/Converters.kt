package ru.animbus.ordomentis.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.animbus.ordomentis.data.webapi.models.SyncAtom
import ru.animbus.ordomentis.domain.models.ContactDataType
import ru.animbus.ordomentis.domain.models.ContactsData
import ru.animbus.ordomentis.domain.models.ItemComment
import ru.animbus.ordomentis.domain.models.ItemStatus
import ru.animbus.ordomentis.domain.models.ItemType
import ru.animbus.ordomentis.domain.models.ItemUser
import ru.animbus.ordomentis.domain.models.Money
import ru.animbus.ordomentis.domain.models.MoneyType
import ru.animbus.ordomentis.domain.models.SharedUser
import ru.animbus.ordomentis.domain.models.UnitType
import ru.animbus.ordomentis.domain.models.UserRole
import ru.animbus.ordomentis.domain.models.WorkingHours

class Converters {
    private val gson = Gson()

    // Базовые списки строк
    @TypeConverter
    fun fromStringList(list: List<String>): String = gson.toJson(list)

    @TypeConverter
    fun toStringList(json: String?): List<String> =
        json?.let { gson.fromJson(it, object : TypeToken<List<String>>() {}.type) } ?: emptyList()

    // ItemStatus список
    @TypeConverter
    fun fromItemStatusList(list: List<ItemStatus>): String =
        gson.toJson(list.map { it.name })

    @TypeConverter
    fun toItemStatusList(json: String?): List<ItemStatus> =
        json?.let {
            val names: List<String> = gson.fromJson(it, object : TypeToken<List<String>>() {}.type)
            names.map { name -> ItemStatus.valueOf(name) }
        } ?: emptyList()

    // Enum конвертеры
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

    @TypeConverter
    fun fromContactDataType(type: ContactDataType): String = type.name
    @TypeConverter
    fun toContactDataType(name: String): ContactDataType = ContactDataType.valueOf(name)

    // Комплексные объекты
    @TypeConverter
    fun fromContactsDataList(list: List<ContactsData>): String = gson.toJson(list)
    @TypeConverter
    fun toContactsDataList(json: String?): List<ContactsData> =
        json?.let { gson.fromJson(it, object : TypeToken<List<ContactsData>>() {}.type) } ?: emptyList()

    @TypeConverter
    fun fromItemCommentList(list: List<ItemComment>): String = gson.toJson(list)
    @TypeConverter
    fun toItemCommentList(json: String?): List<ItemComment> =
        json?.let { gson.fromJson(it, object : TypeToken<List<ItemComment>>() {}.type) } ?: emptyList()

    @TypeConverter
    fun fromItemUserList(list: List<ItemUser>): String = gson.toJson(list)
    @TypeConverter
    fun toItemUserList(json: String?): List<ItemUser> =
        json?.let { gson.fromJson(it, object : TypeToken<List<ItemUser>>() {}.type) } ?: emptyList()

    @TypeConverter
    fun fromWorkingHoursList(list: List<WorkingHours>): String = gson.toJson(list)
    @TypeConverter
    fun toWorkingHoursList(json: String?): List<WorkingHours> =
        json?.let { gson.fromJson(it, object : TypeToken<List<WorkingHours>>() {}.type) } ?: emptyList()

    @TypeConverter
    fun fromSharedUserList(list: List<SharedUser>): String = gson.toJson(list)
    @TypeConverter
    fun toSharedUserList(json: String?): List<SharedUser> =
        json?.let { gson.fromJson(it, object : TypeToken<List<SharedUser>>() {}.type) } ?: emptyList()

    @TypeConverter
    fun fromMoney(money: Money?): String? = money?.let { gson.toJson(it) }
    @TypeConverter
    fun toMoney(json: String?): Money? = json?.let { gson.fromJson(it, Money::class.java) }

    @TypeConverter
    fun fromMainItemDataList(list: List<ru.animbus.ordomentis.domain.models.MainItemData>): String = gson.toJson(list)
    @TypeConverter
    fun toMainItemDataList(json: String?): List<ru.animbus.ordomentis.domain.models.MainItemData> =
        json?.let { gson.fromJson(it, object : TypeToken<List<ru.animbus.ordomentis.domain.models.MainItemData>>() {}.type) } ?: emptyList()

    @TypeConverter
    fun fromSyncAtomList(list: List<SyncAtom>): String = gson.toJson(list)
    @TypeConverter
    fun toSyncAtomList(json: String?): List<SyncAtom> =
        json?.let { gson.fromJson(it, object : TypeToken<List<SyncAtom>>() {}.type) } ?: emptyList()

}