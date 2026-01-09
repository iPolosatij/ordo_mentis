package ru.animbus.ordomentis.data.repositories.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.animbus.ordomentis.data.webapi.clients.ItemApiClient
import ru.animbus.ordomentis.data.webapi.models.ApiResponse
import ru.animbus.ordomentis.domain.models.MainItemData

class ItemApiRepository(
    private val itemApiClient: ItemApiClient
) {
    suspend fun getAllItems(): ApiResponse<List<MainItemData>> {
        return withContext(Dispatchers.IO) {
            itemApiClient.getAllItems()
        }
    }

    suspend fun getItemById(id: String): ApiResponse<MainItemData> {
        return withContext(Dispatchers.IO) {
            itemApiClient.getItemById(id)
        }
    }

    suspend fun getItemsByUserId(userId: String): ApiResponse<List<MainItemData>> {
        return withContext(Dispatchers.IO) {
            itemApiClient.getItemsByUserId(userId)
        }
    }

    suspend fun getItemsByOwnerId(ownerId: String): ApiResponse<List<MainItemData>> {
        return withContext(Dispatchers.IO) {
            itemApiClient.getItemsByOwnerId(ownerId)
        }
    }

    suspend fun getItemsByUnitId(unitId: String): ApiResponse<List<MainItemData>> {
        return withContext(Dispatchers.IO) {
            itemApiClient.getItemsByUnitId(unitId)
        }
    }

    suspend fun syncItems(items: List<MainItemData>): ApiResponse<List<MainItemData>> {
        return withContext(Dispatchers.IO) {
            itemApiClient.syncItems(items)
        }
    }
}