package ru.animbus.ordomentis.data.database.mappers

import com.google.gson.Gson
import ru.animbus.ordomentis.data.database.entities.SyncEntity
import ru.animbus.ordomentis.data.webapi.models.SyncAtom
import ru.animbus.ordomentis.data.webapi.models.SyncData

class SyncDataMapper {
    private val gson = Gson()

    fun toEntity(domain: SyncData): SyncEntity {
        return SyncEntity(
            users = gson.toJson(domain.users) ,
            items = gson.toJson(domain.items) ,
            units = gson.toJson(domain.units) ,
        )
    }

    fun toDomain(entity: SyncEntity?): SyncData {
        return entity?.let {
            SyncData(
                units = gson.fromJson(entity.units, Array<SyncAtom>::class.java).toList(),
                items = gson.fromJson(entity.items, Array<SyncAtom>::class.java).toList(),
                users = gson.fromJson(entity.users, Array<SyncAtom>::class.java).toList(),
            )
        } ?: SyncData(
            units = listOf(),
            items = listOf(),
            users = listOf(),
        )
    }
}