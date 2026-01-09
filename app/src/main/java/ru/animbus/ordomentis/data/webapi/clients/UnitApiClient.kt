package ru.animbus.ordomentis.data.webapi.clients

import retrofit2.Response
import ru.animbus.ordomentis.data.webapi.interfaces.UnitApi
import ru.animbus.ordomentis.data.webapi.models.ApiResponse
import ru.animbus.ordomentis.domain.models.UnitData
import java.io.IOException

class UnitApiClient(
    private val unitApi: UnitApi
) {
    suspend fun getAllUnits(): ApiResponse<List<UnitData>> {
        return safeApiCall { unitApi.getAllUnits() }
    }

    suspend fun getUnitById(id: String): ApiResponse<UnitData> {
        return safeApiCall { unitApi.getUnitById(id) }
    }

    suspend fun getUnitsByUserId(userId: String): ApiResponse<List<UnitData>> {
        return safeApiCall { unitApi.getUnitsByUserId(userId) }
    }

    suspend fun getUnitsByOwnerId(userId: String): ApiResponse<List<UnitData>> {
        return safeApiCall { unitApi.getUnitsByOwnerId(userId) }
    }

    suspend fun syncUnits(units: List<UnitData>): ApiResponse<List<UnitData>> {
        return safeApiCall { unitApi.syncUnits(units) }
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResponse<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResponse.Success(it)
                } ?: ApiResponse.Error("Пустой ответ от сервера")
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Ошибка: ${response.code()}"
                ApiResponse.Error(errorMessage, response.code())
            }
        } catch (e: IOException) {
            ApiResponse.Error("Ошибка сети: ${e.message}")
        } catch (e: Exception) {
            ApiResponse.Error("Неизвестная ошибка: ${e.message}")
        }
    }
}