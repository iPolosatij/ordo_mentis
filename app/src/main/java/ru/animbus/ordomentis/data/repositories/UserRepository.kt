package ru.animbus.ordomentis.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.animbus.ordomentis.data.database.dao.UserDao
import ru.animbus.ordomentis.data.database.mappers.UserMapper
import ru.animbus.ordomentis.domain.models.UserData

class UserRepository(
    private val userDao: UserDao,
    private val mapper: UserMapper = UserMapper()
) {
    suspend fun insertUser(user: UserData) {
        userDao.insertUser(mapper.toEntity(user))
    }

    suspend fun insertAllUsers(users: List<UserData>) {
        userDao.insertAllUsers(mapper.toEntityList(users))
    }

    suspend fun updateUser(user: UserData) {
        userDao.updateUser(mapper.toEntity(user))
    }

    suspend fun deleteUser(user: UserData) {
        userDao.deleteUser(mapper.toEntity(user))
    }

    suspend fun getUserById(userId: String): UserData? {
        return userDao.getUserById(userId)?.let { mapper.toDomain(it) }
    }

    suspend fun getUserByNikName(nikName: String): UserData? {
        return userDao.getUserByNikName(nikName)?.let { mapper.toDomain(it) }
    }

    fun getAllUsers(): Flow<List<UserData>> {
        return userDao.getAllUsers().map { mapper.toDomainList(it) }
    }

    suspend fun getUsersByIds(userIds: List<String>): List<UserData> {
        return mapper.toDomainList(userDao.getUsersByIds(userIds))
    }

    suspend fun clearAllUsers() {
        userDao.clearAllUsers()
    }

    suspend fun searchUsers(query: String): List<UserData> {
        val allUsers = userDao.getAllUsers().first()
        return mapper.toDomainList(allUsers).filter { user ->
            user.nikName.contains(query, ignoreCase = true) ||
                    user.name?.contains(query, ignoreCase = true) == true ||
                    user.lastName?.contains(query, ignoreCase = true) == true ||
                    user.userContacts.any { contact ->
                        contact.contact.contains(query, ignoreCase = true) ||
                                contact.name.contains(query, ignoreCase = true)
                    } ||
                    user.specialties.any { it.contains(query, ignoreCase = true) }
        }
    }

    suspend fun getUsersByUnitId(unitId: String): List<UserData> {
        val allUsers = userDao.getAllUsers().first()
        return mapper.toDomainList(allUsers).filter { user ->
            user.units?.contains(unitId) == true
        }
    }

    suspend fun getUsersBySpecialty(specialty: String): List<UserData> {
        val allUsers = userDao.getAllUsers().first()
        return mapper.toDomainList(allUsers).filter { user ->
            user.specialties.any { it.contains(specialty, ignoreCase = true) }
        }
    }

    suspend fun getUsersWithPendingInvites(): List<UserData> {
        val allUsers = userDao.getAllUsers().first()
        return mapper.toDomainList(allUsers).filter { user ->
            user.invites.isNotEmpty()
        }
    }

    suspend fun getUserContacts(userId: String): List<UserData> {
        val user = getUserById(userId) ?: return emptyList()
        return getUsersByIds(user.contactList)
    }

    suspend fun addContactToUser(userId: String, contactUserId: String): Boolean {
        val user = getUserById(userId) ?: return false
        val updatedContactList = user.contactList.toMutableList().apply {
            if (!contains(contactUserId)) {
                add(contactUserId)
            }
        }
        val updatedUser = user.copy(contactList = updatedContactList)
        updateUser(updatedUser)
        return true
    }

    suspend fun removeContactFromUser(userId: String, contactUserId: String): Boolean {
        val user = getUserById(userId) ?: return false
        val updatedContactList = user.contactList.toMutableList().apply {
            remove(contactUserId)
        }
        val updatedUser = user.copy(contactList = updatedContactList)
        updateUser(updatedUser)
        return true
    }

    suspend fun addInviteToUser(userId: String, unitId: String): Boolean {
        val user = getUserById(userId) ?: return false
        val updatedInvites = user.invites.toMutableList().apply {
            if (!contains(unitId)) {
                add(unitId)
            }
        }
        val updatedUser = user.copy(invites = updatedInvites)
        updateUser(updatedUser)
        return true
    }

    suspend fun removeInviteFromUser(userId: String, unitId: String): Boolean {
        val user = getUserById(userId) ?: return false
        val updatedInvites = user.invites.toMutableList().apply {
            remove(unitId)
        }
        val updatedUser = user.copy(invites = updatedInvites)
        updateUser(updatedUser)
        return true
    }

    suspend fun addSpecialtyToUser(userId: String, specialty: String): Boolean {
        val user = getUserById(userId) ?: return false
        val updatedSpecialties = user.specialties.toMutableList().apply {
            if (!contains(specialty)) {
                add(specialty)
            }
        }
        val updatedUser = user.copy(specialties = updatedSpecialties)
        updateUser(updatedUser)
        return true
    }

    suspend fun removeSpecialtyFromUser(userId: String, specialty: String): Boolean {
        val user = getUserById(userId) ?: return false
        val updatedSpecialties = user.specialties.toMutableList().apply {
            remove(specialty)
        }
        val updatedUser = user.copy(specialties = updatedSpecialties)
        updateUser(updatedUser)
        return true
    }

    suspend fun validateUserCredentials(nikName: String, passwordHash: String? = null): UserData? {
        // В реальном приложении здесь должна быть проверка пароля
        return getUserByNikName(nikName)
    }
}