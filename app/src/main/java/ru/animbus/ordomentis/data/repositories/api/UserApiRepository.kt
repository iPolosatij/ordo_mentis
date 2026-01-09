package ru.animbus.ordomentis.data.repositories.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.animbus.ordomentis.data.webapi.clients.UserApiClient
import ru.animbus.ordomentis.data.webapi.models.ApiResponse
import ru.animbus.ordomentis.data.webapi.models.SyncDataResponse
import ru.animbus.ordomentis.data.webapi.models.SyncRequest
import ru.animbus.ordomentis.domain.models.UserData

class UserApiRepository(
    private val userApiClient: UserApiClient
) {
    suspend fun getSyncData(userId: String, lastSyncTime: Long = 0): ApiResponse<SyncDataResponse> {
        return withContext(Dispatchers.IO) {
            userApiClient.getSyncData(SyncRequest(userId, lastSyncTime))
        }
    }

    suspend fun getAllUsers(): ApiResponse<List<UserData>> {
        return withContext(Dispatchers.IO) {
            userApiClient.getAllUsers()
        }
    }

    suspend fun getUserById(id: String): ApiResponse<UserData> {
        return withContext(Dispatchers.IO) {
            userApiClient.getUserById(id)
        }
    }

    suspend fun getUserByNikName(nikName: String): ApiResponse<UserData> {
        return withContext(Dispatchers.IO) {
            userApiClient.getUserByNikName(nikName)
        }
    }

    suspend fun getUsersByUnitId(unitId: String): ApiResponse<List<UserData>> {
        return withContext(Dispatchers.IO) {
            userApiClient.getUsersByUnitId(unitId)
        }
    }

    suspend fun getUnitOwners(unitId: String): ApiResponse<List<UserData>> {
        return withContext(Dispatchers.IO) {
            userApiClient.getUnitOwners(unitId)
        }
    }

    suspend fun syncUsers(users: List<UserData>): ApiResponse<List<UserData>> {
        return withContext(Dispatchers.IO) {
            userApiClient.syncUsers(users)
        }
    }
}