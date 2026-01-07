package ru.animbus.ordomentis.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.animbus.ordomentis.data.database.dao.MainItemDao
import ru.animbus.ordomentis.data.database.mappers.MainItemMapper
import ru.animbus.ordomentis.domain.models.ItemStatus
import ru.animbus.ordomentis.domain.models.ItemType
import ru.animbus.ordomentis.domain.models.MainItemData

class MainItemRepository(
    private val mainItemDao: MainItemDao,
    private val mapper: MainItemMapper = MainItemMapper()
) {
    suspend fun insertItem(item: MainItemData) {
        mainItemDao.insertItem(mapper.toEntity(item))
    }

    suspend fun insertAllItems(items: List<MainItemData>) {
        mainItemDao.insertAllItems(mapper.toEntityList(items))
    }

    suspend fun updateItem(item: MainItemData) {
        mainItemDao.updateItem(mapper.toEntity(item))
    }

    suspend fun deleteItem(item: MainItemData) {
        mainItemDao.deleteItem(mapper.toEntity(item))
    }

    suspend fun getItemById(itemId: String): MainItemData? {
        return mainItemDao.getItemById(itemId)?.let { mapper.toDomain(it) }
    }

    fun getAllItems(): Flow<List<MainItemData>> {
        return mainItemDao.getAllItems().map { mapper.toDomainList(it) }
    }

    suspend fun getItemsByOwnerId(ownerId: String): List<MainItemData> {
        return mapper.toDomainList(mainItemDao.getItemsByOwnerId(ownerId))
    }

    suspend fun getItemsByIds(itemIds: List<String>): List<MainItemData> {
        return mapper.toDomainList(mainItemDao.getItemsByIds(itemIds))
    }

    suspend fun getItemsByUserId(userId: String): List<MainItemData> {
        val allItems = mainItemDao.getAllItems().first()
        return mapper.toDomainList(allItems).filter { item ->
            item.users.any { it.userId == userId } ||
                    item.shared.any { it.userId == userId } ||
                    item.ownerId == userId
        }
    }

    suspend fun clearAllItems() {
        mainItemDao.clearAllItems()
    }

    suspend fun deleteItemsByOwnerId(ownerId: String) {
        mainItemDao.deleteItemsByOwnerId(ownerId)
    }

    // Дополнительные методы с бизнес-логикой

    suspend fun getItemsByStatus(status: ItemStatus): List<MainItemData> {
        val allItems = mainItemDao.getAllItems().first()
        return mapper.toDomainList(allItems).filter { it.status == status }
    }

    suspend fun getItemsByType(itemType: ItemType): List<MainItemData> {
        val allItems = mainItemDao.getAllItems().first()
        return mapper.toDomainList(allItems).filter { it.itemType == itemType }
    }

    suspend fun getItemsWithDeadlineApproaching(daysThreshold: Int = 7): List<MainItemData> {
        val allItems = mainItemDao.getAllItems().first()
        return mapper.toDomainList(allItems).filter { item ->
            item.deadline != null &&
                    item.status != ItemStatus.Success &&
                    item.status != ItemStatus.Close
            // Здесь можно добавить логику проверки даты
        }
    }

    suspend fun getRootItems(): List<MainItemData> {
        val allItems = mainItemDao.getAllItems().first()
        val allItemsList = mapper.toDomainList(allItems)

        // Находим корневые элементы (те, которые не являются подзадачами других элементов)
        val subItemIds = allItemsList.flatMap { item ->
            item.subItems?.map { it.itemId } ?: emptyList()
        }.toSet()

        return allItemsList.filter { item ->
            !subItemIds.contains(item.itemId)
        }
    }

    suspend fun getSubItems(parentItemId: String): List<MainItemData> {
        val parentItem = getItemById(parentItemId)
        return parentItem?.subItems ?: emptyList()
    }

    suspend fun searchItems(query: String): List<MainItemData> {
        val allItems = mainItemDao.getAllItems().first()
        return mapper.toDomainList(allItems).filter { item ->
            item.itemName?.contains(query, ignoreCase = true) == true ||
                    item.description?.contains(query, ignoreCase = true) == true
        }
    }

    suspend fun getItemsByDateRange(startDate: String, endDate: String): List<MainItemData> {
        val allItems = mainItemDao.getAllItems().first()
        return mapper.toDomainList(allItems).filter { item ->
            item.createDate in startDate..endDate ||
                    (item.deadline != null && item.deadline in startDate..endDate)
        }
    }

    suspend fun getActiveItems(): List<MainItemData> {
        val allItems = mainItemDao.getAllItems().first()
        return mapper.toDomainList(allItems).filter { item ->
            item.status == ItemStatus.Active
        }
    }

    suspend fun getCompletedItems(): List<MainItemData> {
        val allItems = mainItemDao.getAllItems().first()
        return mapper.toDomainList(allItems).filter { item ->
            item.status == ItemStatus.Success || item.status == ItemStatus.Close
        }
    }
}