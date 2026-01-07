package ru.animbus.ordomentis.data.repositories

import kotlinx.coroutines.flow.Flow
import ru.animbus.ordomentis.data.models.domain.MainItemData
import ru.animbus.ordomentis.data.models.domain.UnitData
import ru.animbus.ordomentis.data.models.domain.UserData

class AppRepository(
    private val unitRepository: UnitRepository,
    private val userRepository: UserRepository,
    private val mainItemRepository: MainItemRepository
) {
    // Unit делегаты
    suspend fun insertUnit(unit: UnitData) = unitRepository.insertUnit(unit)
    suspend fun insertAllUnits(units: List<UnitData>) = unitRepository.insertAllUnits(units)
    suspend fun updateUnit(unit: UnitData) = unitRepository.updateUnit(unit)
    suspend fun deleteUnit(unit: UnitData) = unitRepository.deleteUnit(unit)
    suspend fun getUnitById(unitId: String): UnitData? = unitRepository.getUnitById(unitId)
    suspend fun getUnitsByIds(unitIds: List<String>): List<UnitData> = unitRepository.getUnitsByIds(unitIds)
    fun getAllUnits(): Flow<List<UnitData>> = unitRepository.getAllUnits()
    suspend fun getUnitsForOwner(ownerId: String): List<UnitData> = unitRepository.getUnitsForOwner(ownerId)
    suspend fun getUnitsForUser(userId: String): List<UnitData> = unitRepository.getUnitsForUser(userId)
    suspend fun clearAllUnits() = unitRepository.clearAllUnits()

    // User делегаты
    suspend fun insertUser(user: UserData) = userRepository.insertUser(user)
    suspend fun insertAllUsers(users: List<UserData>) = userRepository.insertAllUsers(users)
    suspend fun updateUser(user: UserData) = userRepository.updateUser(user)
    suspend fun deleteUser(user: UserData) = userRepository.deleteUser(user)
    suspend fun getUserById(userId: String): UserData? = userRepository.getUserById(userId)
    suspend fun getUserByNikName(nikName: String): UserData? = userRepository.getUserByNikName(nikName)
    fun getAllUsers(): Flow<List<UserData>> = userRepository.getAllUsers()
    suspend fun getUsersByIds(userIds: List<String>): List<UserData> = userRepository.getUsersByIds(userIds)
    suspend fun clearAllUsers() = userRepository.clearAllUsers()
    suspend fun searchUsers(query: String): List<UserData> = userRepository.searchUsers(query)

    // MainItem делегаты
    suspend fun insertItem(item: MainItemData) = mainItemRepository.insertItem(item)
    suspend fun insertAllItems(items: List<MainItemData>) = mainItemRepository.insertAllItems(items)
    suspend fun updateItem(item: MainItemData) = mainItemRepository.updateItem(item)
    suspend fun deleteItem(item: MainItemData) = mainItemRepository.deleteItem(item)
    suspend fun getItemById(itemId: String): MainItemData? = mainItemRepository.getItemById(itemId)
    fun getAllItems(): Flow<List<MainItemData>> = mainItemRepository.getAllItems()
    suspend fun getItemsByOwnerId(ownerId: String): List<MainItemData> = mainItemRepository.getItemsByOwnerId(ownerId)
    suspend fun getItemsByIds(itemIds: List<String>): List<MainItemData> = mainItemRepository.getItemsByIds(itemIds)
    suspend fun getItemsByUserId(userId: String): List<MainItemData> = mainItemRepository.getItemsByUserId(userId)
    suspend fun clearAllItems() = mainItemRepository.clearAllItems()
    suspend fun deleteItemsByOwnerId(ownerId: String) = mainItemRepository.deleteItemsByOwnerId(ownerId)

    // Композитные методы

    suspend fun getFullUserData(userId: String): UserData? {
        val user = getUserById(userId) ?: return null

        // Получаем полные данные юнитов пользователя
        val unitIds = user.units ?: emptyList()
        val units = getUnitsByIds(unitIds)

        // Получаем задачи пользователя
        val userItems = getItemsByUserId(userId)

        // Здесь можно создать расширенную модель UserData если нужно
        // Пока возвращаем оригинального пользователя
        return user
    }

    suspend fun getFullUnitData(unitId: String): UnitData? {
        val unit = getUnitById(unitId) ?: return null

        // Получаем полные данные владельцев
        val owners = getUsersByIds(unit.owners)

        // Получаем полные данные участников
        val internalUsers = getUsersByIds(unit.internal)
        val externalUsers = getUsersByIds(unit.external)

        // Получаем публичные и приватные задачи
        val publicItems = getItemsByIds(unit.publicItems)
        val privateItems = getItemsByIds(unit.privateItems)

        // Здесь можно создать расширенную модель UnitData если нужно
        return unit
    }

    suspend fun getFullItemData(itemId: String): MainItemData? {
        val item = getItemById(itemId) ?: return null

        // Получаем полные данные владельца
        val owner = getUserById(item.ownerId)

        // Получаем полные данные пользователей задачи
        val userIds = item.users.map { it.userId } + item.shared.map { it.userId }
        val users = getUsersByIds(userIds)

        // Если есть подзадачи, получаем их полные данные
        val fullSubItems = item.subItems?.mapNotNull { getItemById(it.itemId) }

        return if (fullSubItems != null) {
            item.copy(subItems = fullSubItems)
        } else {
            item
        }
    }

    suspend fun getItemsForUnit(unitId: String): List<MainItemData> {
        val unit = getUnitById(unitId) ?: return emptyList()

        val allItemIds = unit.publicItems + unit.privateItems
        return getItemsByIds(allItemIds)
    }

    suspend fun getUsersForUnit(unitId: String): List<UserData> {
        val unit = getUnitById(unitId) ?: return emptyList()

        val allUserIds = unit.owners + unit.internal + unit.external
        return getUsersByIds(allUserIds)
    }

    suspend fun syncDataFromServer(units: List<UnitData>, users: List<UserData>, items: List<MainItemData>) {
        // Очищаем локальные данные
        clearAllUnits()
        clearAllUsers()
        clearAllItems()

        // Сохраняем новые данные
        insertAllUnits(units)
        insertAllUsers(users)
        insertAllItems(items)
    }
}