package ru.animbus.ordomentis.data.webapi.interfaces

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.animbus.ordomentis.domain.models.MainItemData

interface ItemApi {
    @GET("items")
    suspend fun getAllItems(): Response<List<MainItemData>>

    @GET("items/{id}")
    suspend fun getItemById(@Path("id") id: String): Response<MainItemData>

    @GET("items/user/{userId}")
    suspend fun getItemsByUserId(@Path("userId") userId: String): Response<List<MainItemData>>

    @GET("items/owner/{ownerId}")
    suspend fun getItemsByOwnerId(@Path("ownerId") ownerId: String): Response<List<MainItemData>>

    @GET("items/unit/{unitId}")
    suspend fun getItemsByUnitId(@Path("unitId") unitId: String): Response<List<MainItemData>>

    @POST("items")
    suspend fun createItem(@Body item: MainItemData): Response<MainItemData>

    @PUT("items/{id}")
    suspend fun updateItem(@Path("id") id: String, @Body item: MainItemData): Response<MainItemData>

    @DELETE("items/{id}")
    suspend fun deleteItem(@Path("id") id: String): Response<Unit>

    @POST("items/batch")
    suspend fun syncItems(@Body items: List<MainItemData>): Response<List<MainItemData>>
}