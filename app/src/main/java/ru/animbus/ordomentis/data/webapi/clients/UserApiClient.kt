package ru.animbus.ordomentis.data.webapi.clients

import retrofit2.Response
import ru.animbus.ordomentis.data.webapi.interfaces.UserApi
import ru.animbus.ordomentis.data.webapi.models.ApiResponse
import ru.animbus.ordomentis.data.webapi.models.SyncDataResponse
import ru.animbus.ordomentis.data.webapi.models.SyncRequest
import ru.animbus.ordomentis.domain.models.UserData
import java.io.IOException

class UserApiClient(
    private val userApi: UserApi
) {
    suspend fun getSyncData(request: SyncRequest): ApiResponse<SyncDataResponse> {
        return safeApiCall { userApi.getSyncData(request) }
    }

    suspend fun getAllUsers(): ApiResponse<List<UserData>> {
        return safeApiCall { userApi.getAllUsers() }
    }

    suspend fun getUserById(id: String): ApiResponse<UserData> {
        return safeApiCall { userApi.getUserById(id) }
    }

    suspend fun getUserByNikName(nikName: String): ApiResponse<UserData> {
        return safeApiCall { userApi.getUserByNikName(nikName) }
    }

    suspend fun getUsersByUnitId(unitId: String): ApiResponse<List<UserData>> {
        return safeApiCall { userApi.getUsersByUnitId(unitId) }
    }

    suspend fun getUnitOwners(unitId: String): ApiResponse<List<UserData>> {
        return safeApiCall { userApi.getUnitOwners(unitId) }
    }

    suspend fun syncUsers(users: List<UserData>): ApiResponse<List<UserData>> {
        return safeApiCall { userApi.syncUsers(users) }
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResponse<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResponse.Success(it)
                } ?: ApiResponse.Error("Пустой ответ от сервера")
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    // Здесь можно парсить JSON ошибки если нужно
                    errorBody ?: "Ошибка: ${response.code()}"
                } catch (e: Exception) {
                    "Ошибка: ${response.code()}"
                }
                ApiResponse.Error(errorMessage, response.code())
            }
        } catch (e: IOException) {
            ApiResponse.Error("Ошибка сети: ${e.message}")
        } catch (e: Exception) {
            ApiResponse.Error("Неизвестная ошибка: ${e.message}")
        }
    }
}