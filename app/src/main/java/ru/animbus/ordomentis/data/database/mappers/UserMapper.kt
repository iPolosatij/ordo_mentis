package ru.animbus.ordomentis.data.database.mappers

import com.google.gson.Gson
import ru.animbus.ordomentis.data.database.entities.UserEntity
import ru.animbus.ordomentis.domain.models.ContactsData
import ru.animbus.ordomentis.domain.models.UserData

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
            bornDate = domain.bornDate,
            userContacts = gson.toJson(domain.userContacts),
            contactList = gson.toJson(domain.contactList),
            specialties = gson.toJson(domain.specialties),
            invites = gson.toJson(domain.invites),
            accessItem = gson.toJson(domain.accessItem)
        )
    }

    fun toDomain(entity: UserEntity): UserData {
        return UserData(
            userId = entity.userId,
            units = entity.units?.let { gson.fromJson(it, Array<String>::class.java).toList() },
            name = entity.name,
            lastName = entity.lastName,
            patronymic = entity.patronymic,
            nikName = entity.nikName,
            bornDate = entity.bornDate,
            userContacts = gson.fromJson(entity.userContacts, Array<ContactsData>::class.java).toList(),
            contactList = gson.fromJson(entity.contactList, Array<String>::class.java).toList(),
            specialties = gson.fromJson(entity.specialties, Array<String>::class.java).toList(),
            invites = gson.fromJson(entity.invites, Array<String>::class.java).toList(),
            accessItem = gson.fromJson(entity.accessItem, Array<String>::class.java).toList()
        )
    }

    fun toEntityList(domainList: List<UserData>): List<UserEntity> {
        return domainList.map { toEntity(it) }
    }

    fun toDomainList(entityList: List<UserEntity>): List<UserData> {
        return entityList.map { toDomain(it) }
    }
}