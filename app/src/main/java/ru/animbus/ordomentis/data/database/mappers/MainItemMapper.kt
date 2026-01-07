package ru.animbus.ordomentis.data.database.mappers

import com.google.gson.Gson
import ru.animbus.ordomentis.data.database.entities.MainItemEntity
import ru.animbus.ordomentis.data.models.domain.*

class MainItemMapper {
    private val gson = Gson()

    fun toEntity(domain: MainItemData): MainItemEntity {
        return MainItemEntity(
            itemId = domain.itemId,
            iconUrl = domain.iconUrl,
            itemName = domain.itemName,
            itemType = domain.itemType.name,
            description = domain.description,
            subItems = domain.subItems?.let { gson.toJson(it) },
            comments = domain.comments?.let { gson.toJson(it) },
            status = domain.status.name,
            statusList = gson.toJson(domain.statusList.map { it.name }),
            ownerId = domain.ownerId,
            users = gson.toJson(domain.users),
            createDate = domain.createDate,
            deadline = domain.deadline,
            timeEstimation = gson.toJson(domain.timeEstimation),
            spentTime = gson.toJson(domain.spentTime),
            costEstimation = domain.costEstimation?.let { gson.toJson(it) },
            realCost = domain.realCost?.let { gson.toJson(it) },
            shared = gson.toJson(domain.shared)
        )
    }

    fun toDomain(entity: MainItemEntity): MainItemData {
        return MainItemData(
            itemId = entity.itemId,
            iconUrl = entity.iconUrl,
            itemName = entity.itemName,
            itemType = ItemType.valueOf(entity.itemType),
            description = entity.description,
            subItems = entity.subItems?.let {
                gson.fromJson(it, Array<MainItemData>::class.java).toList()
            },
            comments = entity.comments?.let {
                gson.fromJson(it, Array<ItemComment>::class.java).toList()
            },
            status = ItemStatus.valueOf(entity.status),
            statusList = gson.fromJson(entity.statusList, Array<String>::class.java)
                .map { ItemStatus.valueOf(it) },
            ownerId = entity.ownerId,
            users = gson.fromJson(entity.users, Array<ItemUser>::class.java).toList(),
            createDate = entity.createDate,
            deadline = entity.deadline,
            timeEstimation = gson.fromJson(entity.timeEstimation, Array<WorkingHours>::class.java).toList(),
            spentTime = gson.fromJson(entity.spentTime, Array<WorkingHours>::class.java).toList(),
            costEstimation = entity.costEstimation?.let { gson.fromJson(it, Money::class.java) },
            realCost = entity.realCost?.let { gson.fromJson(it, Money::class.java) },
            shared = gson.fromJson(entity.shared, Array<SharedUser>::class.java).toList()
        )
    }

    fun toEntityList(domainList: List<MainItemData>): List<MainItemEntity> {
        return domainList.map { toEntity(it) }
    }

    fun toDomainList(entityList: List<MainItemEntity>): List<MainItemData> {
        return entityList.map { toDomain(it) }
    }
}