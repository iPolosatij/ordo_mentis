package ru.animbus.ordomentis.data.webapi.models

import com.google.gson.annotations.SerializedName

data class SyncDataResponse(
    @SerializedName("users") val users: List<SyncAtom>,
    @SerializedName("items") val items: List<SyncAtom>,
    @SerializedName("units") val units: List<SyncAtom>,
)

data class SyncAtom(
    @SerializedName("id") val id: String,
    @SerializedName("time") val time: Long,
)

data class SyncRequest(
    @SerializedName("userId") val userId: String,
    @SerializedName("lastSyncTime") val lastSyncTime: Long = 0
)