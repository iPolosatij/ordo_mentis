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
        val allUsers = userDao.getAllUsers().first()
        return mapper.toDomainList(allUsers).filter { user ->
            userIds.contains(user.userId)
        }
    }

    suspend fun clearAllUsers() {
        userDao.clearAllUsers()
    }

    suspend fun searchUsers(query: String): List<UserData> {
        val allUsers = userDao.getAllUsers().first()
        return mapper.toDomainList(allUsers).filter { user ->
            user.nikName.contains(query, ignoreCase = true) ||
                    user.name?.contains(query, ignoreCase = true) == true ||
                    user.lastName?.contains(query, ignoreCase = true) == true
        }
    }

    suspend fun getUsersByUnitId(unitId: String): List<UserData> {
        val allUsers = userDao.getAllUsers().first()
        return mapper.toDomainList(allUsers).filter { user ->
            user.units?.contains(unitId) == true
        }
    }

    suspend fun validateUserCredentials(nikName: String, passwordHash: String? = null): UserData? {
        // В реальном приложении здесь должна быть проверка пароля
        return getUserByNikName(nikName)
    }
}