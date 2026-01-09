package ru.animbus.ordomentis.data.webapi.models

import com.google.gson.annotations.SerializedName

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String, val code: Int = 0) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
}

data class ApiError(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: Int = 0,
    @SerializedName("timestamp") val timestamp: Long = 0
)