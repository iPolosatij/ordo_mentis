package ru.animbus.ordomentis.data.database.mappers

import com.google.gson.Gson
import ru.animbus.ordomentis.data.database.entities.UnitEntity
import ru.animbus.ordomentis.data.models.domain.UnitData
import ru.animbus.ordomentis.data.models.domain.UnitType

@Suppress("UNCHECKED_CAST")
class UnitMapper {
    private val gson = Gson()

    fun toEntity(domain: UnitData): UnitEntity {
        return UnitEntity(
            unitId = domain.unitId,
            owners = gson.toJson(domain.owners),
            internal = gson.toJson(domain.internal),
            external = gson.toJson(domain.external),
            publicItems = gson.toJson(domain.publicItems),
            privateItems = gson.toJson(domain.privateItems),
            internalUnits = gson.toJson(domain.internalUnits),
            externalUnits = gson.toJson(domain.externalUnits),
            type = domain.type.name
        )
    }

    fun toDomain(entity: UnitEntity): UnitData {
        return UnitData(
            unitId = entity.unitId,
            owners = gson.fromJson(entity.owners, List::class.java) as List<String>,
            internal = gson.fromJson(entity.internal, List::class.java) as List<String>,
            external = gson.fromJson(entity.external, List::class.java) as List<String>,
            publicItems = gson.fromJson(entity.publicItems, List::class.java) as List<String>,
            privateItems = gson.fromJson(entity.privateItems, List::class.java) as List<String>,
            internalUnits = gson.fromJson(entity.internalUnits, List::class.java) as List<String>,
            externalUnits = gson.fromJson(entity.externalUnits, List::class.java) as List<String>,
            type = UnitType.valueOf(entity.type)
        )
    }

    fun toEntityList(domainList: List<UnitData>): List<UnitEntity> {
        return domainList.map { toEntity(it) }
    }

    fun toDomainList(entityList: List<UnitEntity>): List<UnitData> {
        return entityList.map { toDomain(it) }
    }
}