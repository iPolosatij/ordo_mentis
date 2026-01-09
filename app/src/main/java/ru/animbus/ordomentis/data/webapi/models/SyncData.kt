package ru.animbus.ordomentis.data.webapi.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SyncData(
    @SerializedName("users") val users: List<SyncAtom> = listOf(),
    @SerializedName("items") val items: List<SyncAtom> = listOf(),
    @SerializedName("units") val units: List<SyncAtom> = listOf(),
): Serializable

data class SyncAtom(
    @SerializedName("id") val id: String,
    @SerializedName("time") val time: Long,
): Serializable