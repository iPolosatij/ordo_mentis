package ru.animbus.ordomentis.data.repositories.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.animbus.ordomentis.data.webapi.clients.UnitApiClient
import ru.animbus.ordomentis.data.webapi.models.ApiResponse
import ru.animbus.ordomentis.domain.models.UnitData

class UnitApiRepository(
    private val unitApiClient: UnitApiClient
) {
    suspend fun getAllUnits(): ApiResponse<List<UnitData>> {
        return withContext(Dispatchers.IO) {
            unitApiClient.getAllUnits()
        }
    }

    suspend fun getUnitById(id: String): ApiResponse<UnitData> {
        return withContext(Dispatchers.IO) {
            unitApiClient.getUnitById(id)
        }
    }

    suspend fun getUnitsByUserId(userId: String): ApiResponse<List<UnitData>> {
        return withContext(Dispatchers.IO) {
            unitApiClient.getUnitsByUserId(userId)
        }
    }

    suspend fun getUnitsByOwnerId(userId: String): ApiResponse<List<UnitData>> {
        return withContext(Dispatchers.IO) {
            unitApiClient.getUnitsByOwnerId(userId)
        }
    }

    suspend fun syncUnits(units: List<UnitData>): ApiResponse<List<UnitData>> {
        return withContext(Dispatchers.IO) {
            unitApiClient.syncUnits(units)
        }
    }
}