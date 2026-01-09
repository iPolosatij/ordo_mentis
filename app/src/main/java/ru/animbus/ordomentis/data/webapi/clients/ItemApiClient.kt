package ru.animbus.ordomentis.data.webapi.clients

import retrofit2.Response
import ru.animbus.ordomentis.data.webapi.interfaces.ItemApi
import ru.animbus.ordomentis.data.webapi.models.ApiResponse
import ru.animbus.ordomentis.domain.models.MainItemData
import java.io.IOException

class ItemApiClient(
    private val itemApi: ItemApi
) {
    suspend fun getAllItems(): ApiResponse<List<MainItemData>> {
        return safeApiCall { itemApi.getAllItems() }
    }

    suspend fun getItemById(id: String): ApiResponse<MainItemData> {
        return safeApiCall { itemApi.getItemById(id) }
    }

    suspend fun getItemsByUserId(userId: String): ApiResponse<List<MainItemData>> {
        return safeApiCall { itemApi.getItemsByUserId(userId) }
    }

    suspend fun getItemsByOwnerId(ownerId: String): ApiResponse<List<MainItemData>> {
        return safeApiCall { itemApi.getItemsByOwnerId(ownerId) }
    }

    suspend fun getItemsByUnitId(unitId: String): ApiResponse<List<MainItemData>> {
        return safeApiCall { itemApi.getItemsByUnitId(unitId) }
    }

    suspend fun syncItems(items: List<MainItemData>): ApiResponse<List<MainItemData>> {
        return safeApiCall { itemApi.syncItems(items) }
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