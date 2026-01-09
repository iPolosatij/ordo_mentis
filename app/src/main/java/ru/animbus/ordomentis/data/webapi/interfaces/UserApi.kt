package ru.animbus.ordomentis.data.webapi.interfaces

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.animbus.ordomentis.data.webapi.models.SyncData
import ru.animbus.ordomentis.domain.models.UserData

interface UserApi {
    @GET("users/sync/{id}")
    suspend fun getSyncData(@Path("id") id: String): Response<SyncData>

    @GET("users")
    suspend fun getAllUsers(): Response<List<UserData>>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: String): Response<UserData>

    @GET("users/nikname/{nikName}")
    suspend fun getUserByNikName(@Path("nikName") nikName: String): Response<UserData>

    @GET("users/unit/{unitId}")
    suspend fun getUsersByUnitId(@Path("unitId") unitId: String): Response<List<UserData>>

    @GET("users/unit/owners/{unitId}")
    suspend fun getUnitOwners(@Path("unitId") unitId: String): Response<List<UserData>>

    @POST("users")
    suspend fun createUser(@Body user: UserData): Response<UserData>

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: String, @Body user: UserData): Response<UserData>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: String): Response<Unit>

    @POST("users/batch")
    suspend fun syncUsers(@Body users: List<UserData>): Response<List<UserData>>
}