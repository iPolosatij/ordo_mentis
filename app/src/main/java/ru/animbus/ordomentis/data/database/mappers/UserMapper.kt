package ru.animbus.ordomentis.data.database.mappers

import com.google.gson.Gson
import ru.animbus.ordomentis.data.database.entities.UserEntity
import ru.animbus.ordomentis.data.models.domain.UserData

@Suppress("UNCHECKED_CAST")
class UserMapper {
    private val gson = Gson()

    fun toEntity(domain: UserData): UserEntity {
        return UserEntity(
            userId = domain.userId,
            units = domain.units?.let { gson.toJson(it) },
            name = domain.name,
            lastName = domain.lastName,
            patronymic = domain.patronymic,
            nikName = domain.nikName,
            accessItem = gson.toJson(domain.accessItem)
        )
    }

    fun toDomain(entity: UserEntity): UserData {
        return UserData(
            userId = entity.userId,
            units = entity.units?.let { gson.fromJson(it, List::class.java) as List<String> },
            name = entity.name,
            lastName = entity.lastName,
            patronymic = entity.patronymic,
            nikName = entity.nikName,
            accessItem = gson.fromJson(entity.accessItem, List::class.java) as List<String>
        )
    }

    fun toEntityList(domainList: List<UserData>): List<UserEntity> {
        return domainList.map { toEntity(it) }
    }

    fun toDomainList(entityList: List<UserEntity>): List<UserData> {
        return entityList.map { toDomain(it) }
    }
}