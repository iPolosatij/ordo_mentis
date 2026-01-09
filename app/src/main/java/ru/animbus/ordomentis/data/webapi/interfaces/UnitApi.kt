package ru.animbus.ordomentis.data.webapi.interfaces

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.animbus.ordomentis.domain.models.UnitData

interface UnitApi {
    @GET("units")
    suspend fun getAllUnits(): Response<List<UnitData>>

    @GET("units/{id}")
    suspend fun getUnitById(@Path("id") id: String): Response<UnitData>

    @GET("units/user/{userId}")
    suspend fun getUnitsByUserId(@Path("userId") userId: String): Response<List<UnitData>>

    @GET("units/owner/{userId}")
    suspend fun getUnitsByOwnerId(@Path("userId") userId: String): Response<List<UnitData>>

    @POST("units")
    suspend fun createUnit(@Body unit: UnitData): Response<UnitData>

    @PUT("units/{id}")
    suspend fun updateUnit(@Path("id") id: String, @Body unit: UnitData): Response<UnitData>

    @DELETE("units/{id}")
    suspend fun deleteUnit(@Path("id") id: String): Response<Unit>

    @POST("units/batch")
    suspend fun syncUnits(@Body units: List<UnitData>): Response<List<UnitData>>
}